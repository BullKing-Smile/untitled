package com.coocpu.security_db_api_demo.controller;

import com.coocpu.security_db_api_demo.service.UserService;
import com.coocpu.security_db_api_demo.utils.LoginInfoUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @auth Felix
 * @since 2025/3/29 17:37
 */
@RequestMapping("/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    private String hi() {
        return "Welcome to Security Demo!";
    }


    @GetMapping("/user/login")
    private String userLogin(HttpServletRequest request) {
        return "Login Success, Welcome to SpringSecurity Demo!!!";
    }

    /**
     * 用下面三个对象/类/接口 接受都可以获得 登录信息
     * java.security.Principal
     * org.springframework.security.core.Authentication
     * org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     * OR
     * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     */
    @PostMapping("/welcome")
    private Object welcome() {
        return LoginInfoUtils.getLoginUserInfo();
    }
}
