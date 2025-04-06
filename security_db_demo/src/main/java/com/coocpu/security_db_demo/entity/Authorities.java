package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * authorities
 */
@Data
public class Authorities implements Serializable {
    private String username;

    private String authority;

    private static final long serialVersionUID = 1L;
}