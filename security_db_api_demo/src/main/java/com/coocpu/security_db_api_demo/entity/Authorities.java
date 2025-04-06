package com.coocpu.security_db_api_demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * authorities
 */
@Data
public class Authorities implements Serializable {
    private String username;

    private String authority;

    private static final long serialVersionUID = 1L;
}