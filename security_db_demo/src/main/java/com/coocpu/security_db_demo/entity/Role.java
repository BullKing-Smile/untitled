package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * role
 */
@Data
public class Role implements Serializable {
    private Long id;

    private String name;

    private static final long serialVersionUID = 1L;
}