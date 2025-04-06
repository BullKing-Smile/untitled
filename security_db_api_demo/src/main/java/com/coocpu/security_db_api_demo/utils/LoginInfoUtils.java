package com.coocpu.security_db_api_demo.utils;

import com.coocpu.security_db_api_demo.entity.Users;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @auth Felix
 * @since 2025/3/30 22:24
 */
public class LoginInfoUtils {
    public static Users getLoginUserInfo() {
        return  (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
