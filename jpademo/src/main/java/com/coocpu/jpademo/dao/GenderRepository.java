package com.coocpu.jpademo.dao;

import com.coocpu.jpademo.entity.Gender;
import com.coocpu.jpademo.entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @auth Felix
 * @since 2025/3/19 14:14
 */
public interface GenderRepository extends CrudRepository<Gender, Long> {
    Gender save(Gender gender);
}