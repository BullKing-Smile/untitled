package com.coocpu.security_db_api_demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * permission
 */
@Data
public class Permission implements Serializable {
    private Long id;

    private String name;

    private String code;

    private String url;

    private String type;

    private Long parentId;

    private Integer orderNo;

    private String icon;

    private Date createdAt;

    private Date updatedAt;

    private static final long serialVersionUID = 1L;
}