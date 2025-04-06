package com.coocpu.security_db_api_demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * role_permission
 */
@Data
public class RolePermission implements Serializable {
    private Long id;

    private Long roleId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;
}