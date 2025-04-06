package com.coocpu.security_db_demo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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