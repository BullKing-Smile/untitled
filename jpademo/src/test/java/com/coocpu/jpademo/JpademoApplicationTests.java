package com.coocpu.jpademo;

import com.coocpu.jpademo.dao.GenderRepository;
import com.coocpu.jpademo.dao.TeacherRepository;
import com.coocpu.jpademo.entity.Gender;
import com.coocpu.jpademo.entity.Teacher;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@SpringBootTest
class JpademoApplicationTests {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    GenderRepository genderRepository;

    @Test
    void saveTeacher() {
        Teacher teacher1 = new Teacher();
        teacher1.setUsername("teacher-001");
//        teacher1.setGender(1);
        teacher1.setGrade(4);
        teacher1.setStatus(1);
        Teacher save = teacherRepository.save(teacher1);
        Assert.notNull(save, "Save success!!!");

    }

//    @Test
//    @Transactional
//    void saveTeacherWithTransaction() {
//        List<Teacher> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Teacher teacher1 = new Teacher();
//            teacher1.setUsername("teacher-0"+(i+1));
//            teacher1.setGender(new Random().nextInt(2));
//            teacher1.setGrade(new Random().nextInt(10));
//            teacher1.setStatus(new Random().nextInt(2));
//list.add(teacher1);
//        }
//        int count = teacherRepository.saveAll(list);
//        System.out.println("save count = "+ count);
//        Assert.isTrue(count == list.size(), "Save success!!!");
//
//    }

    @Test
    void queryTeacher() {
        Optional<Teacher> teacherOptional = teacherRepository.findById(1L);
        Teacher teacher = teacherOptional.get();
        log.info(teacher.toString());
        Assert.notNull(teacher, "Query success!!!");

    }

    @Test
    void queryTeacherWithCondition() {
        Teacher teacher = teacherRepository.findTeacherByUsername("teacher-001");
        log.info(teacher.toString());
        Assert.notNull(teacher, "Query success!!!");

    }

    @Test
    void queryTeacherWithCondition2() {
        Teacher teacher = teacherRepository.findTeacherByUsername2("teacher-001");
        log.info(teacher.toString());
        Assert.notNull(teacher, "Query success!!!");

    }


    @Test
    void queryTeacherWithConditionLike() {
        List<Teacher> teacher = teacherRepository.findTeacherByUsernameLikeIgnoreCase("teacher%");
        teacher.forEach(System.out::println);
        Assert.notNull(teacher, "Query success!!!");

    }

    @Test
    void updateTeacher() {
        int count = teacherRepository.updateTeacher("teacher-999", 1L);
        System.out.println("update count = " + count);
        Assert.isTrue(count > 0, "Query success!!!");

    }


    @Test
    void ignoreUppercase() {
        Teacher teacher1 = new Teacher();
        teacher1.setUsername("teacher-001");
//        teacher1.setGender(1);
        teacher1.setGrade(4);
        ExampleMatcher matcher = ExampleMatcher.matching()
                        .withIgnoreCase("username")// 设置忽略大小写
                                .withIgnorePaths("gender");// 设置忽略属性
        Example<Teacher> teacherExample = Example.of(teacher1, matcher);
//        teacherRepository.findAll(teacherExample);

//        Assert.isTrue(count > 0, "Query success!!!");

    }

//    @Test
//    public void testQuerydsl() {
//        QTeacher teacher = QTeacher.teacher;
//        BooleanExpression eq = teacher.id.eq(1L);
//
//        eq.and()
//                .gt()
//                .lt()
//                .in()
//
//    }

    @Test
    public void saveGenderInfo() {
        Gender female = new Gender();
        female.setName("female");
        female.setStatus(1);
        genderRepository.save(female);

        Gender male = new Gender();
        male.setName("male");
        male.setStatus(1);
        genderRepository.save(male);

        Gender unknown = new Gender();
        unknown.setName("unknown");
        unknown.setStatus(1);
        genderRepository.save(unknown);
        Assert.isTrue(true, "Gender save success~~~");
    }


}
