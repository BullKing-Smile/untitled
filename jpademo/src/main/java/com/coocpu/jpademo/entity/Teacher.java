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
@Table(name = "teacher")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "username")
    private String username;
//    private Integer gender;
    private Integer grade;
    private Integer status;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "gender")
    private Gender genderInfo;
}
