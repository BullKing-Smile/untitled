package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import lombok.Data;

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