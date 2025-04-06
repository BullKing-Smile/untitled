package com.coocpu.security_db_demo.controller;

import com.coocpu.security_db_demo.service.UserService;
import com.coocpu.security_db_demo.utils.LoginInfoUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auth Felix
 * @since 2025/3/29 17:37
 */
@RequestMapping("/")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/")
    private String hi() {
        return "Welcome to Security Demo!";
    }

    @GetMapping("/toLogin")
    private String toLogin() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/user/login")
    private String userLogin(HttpServletRequest request) {
//        UserDetails userDetails = userService.loadUserByUsername(request.getParameter("username"));
//        if (userDetails.)
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
    @ResponseBody
    @PostMapping("/welcome")
    private Object welcome() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return usernamePasswordAuthenticationToken;
//        return authentication;
        return LoginInfoUtils.getLoginUserInfo();
    }
}
