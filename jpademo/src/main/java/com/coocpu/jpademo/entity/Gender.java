package com.coocpu.jpademo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auth Felix
 * @since 2025/3/19 14:02
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gender")
@Data
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private Integer status;
}
