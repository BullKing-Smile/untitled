package com.coocpu.security_db_api_demo.filter;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.coocpu.security_db_api_demo.constant.Constants;
import com.coocpu.security_db_api_demo.entity.Users;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @auth Felix
 * @since 2025/4/1 14:01
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private  RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (StringUtils.endsWithIgnoreCase("/user/login", uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("access-token");
        if (!StringUtils.hasText(token) || !JWTUtil.verify(token, Constants.secret.getBytes(StandardCharsets.UTF_8))) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("~~Unauthorized~~~");
        } else {
            String userJson = JWTUtil.parseToken(token).getPayloads().get("user", String.class);
            Users user = JSONUtil.toBean(userJson, Users.class);
            String redisToken = (String) redisTemplate.opsForHash().get(Constants.redis_token_key, String.valueOf(user.getId()));
            if (!StringUtils.endsWithIgnoreCase(redisToken, token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("~~Unauthorized(equal false)");
            } else {
                // 验证通过
                // spring security 框架上下文放置一个 认证对象，告诉后续的filter 该请求时 已认证授权的操作
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // 链式执行下一个 filter
                filterChain.doFilter(request, response);
            }
        }
    }
}
