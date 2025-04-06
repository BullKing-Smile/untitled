package com.coocpu.security_db_api_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth Felix
 * @since 2025/3/29 17:37
 */
@RequestMapping("/api/user")
@RestController
public class UserOpController {

    @GetMapping("/info")
    public Object userInfo() {
        return "userInfo";
    }

    @PreAuthorize(value = "hasRole('admin')")
    @GetMapping("/create")
    public Object createUser() {
        return "createUser";
    }

    @PreAuthorize(value = "hasRole('admin')")
    @GetMapping("/delete")
    public Object deleteUser() {
        return "deleteUser";
    }

    @PreAuthorize(value = "hasAnyRole('admin','reviewer')")
    @GetMapping("/update")
    public Object updateUser() {
        return "updateUser";
    }

    @PreAuthorize(value = "hasAnyRole('admin','user','reviewer','teacher')")
    @GetMapping("/read")
    public Object readUser() {
        return "readUser";
    }

}
