package com.coocpu.jpademo.dao;

import com.coocpu.jpademo.entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @auth Felix
 * @since 2025/3/19 14:14
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher save(Teacher person);

//    @Transactional
//    @Modifying
//    int saveAll(List<Teacher> teachers);

    Optional<Teacher> findById(long id);

//    @Query(nativeQuery = true)
//    List<Teacher> findAllTeachers();

    @Query("from Teacher where username = ?1")
    Teacher findTeacherByUsername(String username);

    List<Teacher> findTeacherByUsernameLikeIgnoreCase(String username);

    @Query("from Teacher where username = :username")
    Teacher findTeacherByUsername2(@Param("username") String username);


    @Transactional
    @Modifying // 通知springdatajpa, 这是一个修改操作
    @Query("update Teacher t set t.username = :username where t.id =:id")
    int updateTeacher(@Param("username") String username, @Param("id") Long id);
}