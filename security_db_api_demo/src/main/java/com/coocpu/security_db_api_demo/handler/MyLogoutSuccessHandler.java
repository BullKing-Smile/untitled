package com.coocpu.security_db_api_demo.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coocpu.security_db_api_demo.constant.Constants;
import com.coocpu.security_db_api_demo.entity.Users;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @auth Felix
 * @since 2025/3/31 23:13
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 清除token
        Users user = (Users) authentication.getPrincipal();
        redisTemplate.opsForHash().delete(Constants.redis_token_key, String.valueOf(user.getId()));

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        JSONObject result = new JSONObject();
        result.set("msg", "退出登录成功");
        String jsonStr = JSONUtil.toJsonStr(result);
        response.getWriter().write(jsonStr);
    }
}
