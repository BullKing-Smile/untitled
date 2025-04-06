package com.coocpu.security_db_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth Felix
 * @since 2025/3/29 17:37
 */
@RequestMapping("/api/course")
@RestController
public class CourseOpController {

    @PreAuthorize(value = "hasAuthority('course:create')")
    @GetMapping("/create")
    public Object createCourse() {
        return "createCourse";
    }

    @PreAuthorize(value = "hasAuthority('course:delete')")
    @GetMapping("/delete")
    public Object deleteCourse() {
        return "deleteCourse";
    }

    @PreAuthorize(value = "hasAnyAuthority('course:update')")
    @GetMapping("/update")
    public Object updateCourse() {
        return "updateCourse";
    }

    @PreAuthorize(value = "hasAnyAuthority('course:read')")
    @GetMapping("/read")
    public Object readCourse() {
        return "readCourse";
    }

}
