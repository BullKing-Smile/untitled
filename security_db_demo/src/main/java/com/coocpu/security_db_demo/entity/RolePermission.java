package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import lombok.Data;

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