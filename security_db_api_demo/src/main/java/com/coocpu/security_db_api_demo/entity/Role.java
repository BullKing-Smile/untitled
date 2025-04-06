package com.coocpu.security_db_api_demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * role
 */
@Data
public class Role implements Serializable {
    private Long id;

    private String name;

    private static final long serialVersionUID = 1L;
}