package com.coocpu.security_db_api_demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @auth Felix
 * @since 2025/3/30 17:16
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (!StringUtils.endsWithIgnoreCase("/user/login", uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String captcha = request.getParameter("captcha");
        String sessionCode = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.hasText(captcha)) {
            response.sendRedirect("/");
        } else if (!StringUtils.endsWithIgnoreCase(sessionCode, captcha)) {
            response.sendRedirect("/");
        } else {
            // 验证通过， 放行， 执行下一个filter
            filterChain.doFilter(request, response);
        }
    }
}
