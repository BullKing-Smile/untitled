package com.coocpu.shardingspheredemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auth Felix
 * @since 2025/3/19 21:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRecords {
    private Long id;
    private Long userId;
    private String userName;

    private Date start;
    private Date end;
    private Integer categoryId;
    private Date createdAt;
    private Date updatedAt;
}
