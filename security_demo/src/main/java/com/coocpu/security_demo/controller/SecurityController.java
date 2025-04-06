package com.coocpu.security_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth Felix
 * @since 2025/3/29 15:49
 */
@RequestMapping("/")
@RestController
public class SecurityController {

    @GetMapping("/login")
    private String login() {
        return "login success";
    }

    @GetMapping("/hello")
    private String hello() {
        return "Hello security...";
    }
}

