package com.coocpu.security_db_api_demo.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.coocpu.security_db_api_demo.constant.Constants;
import com.coocpu.security_db_api_demo.entity.Users;
import com.coocpu.security_db_api_demo.utils.LoginInfoUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;

/**
 * @auth Felix
 * @since 2025/3/31 21:35
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // generate JWT
        Users principal = (Users) authentication.getPrincipal();

        String token = JWTUtil.createToken(Map.of("user", JSONUtil.toJsonStr(principal)), Constants.secret.getBytes(StandardCharsets.UTF_8));

        // token 写入 Redis (String, Hash, List, Set, ZSet)
        // opsForValue --- String
        // opsForHash --- Hash
        // opeForList --- List
        // opsForSet --- Set
        // opsForZSet --- ZSet
        redisTemplate.opsForHash().put(Constants.redis_token_key, String.valueOf(principal.getId()), token);

        JSONObject result = new JSONObject();
        result.set("msg", "成功");
        result.set("data", LoginInfoUtils.getLoginUserInfo());
        result.set("token", token);
        String jsonStr = JSONUtil.toJsonStr(result);
        response.getWriter().write(jsonStr);
    }
}
