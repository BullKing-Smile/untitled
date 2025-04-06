package com.coocpu.security_db_api_demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * user_role
 */
@Data
public class UserRole implements Serializable {
    private Long id;

    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}