package com.coocpu.shardingspheredemo.contoller;

import com.coocpu.shardingspheredemo.entity.LeaveRecords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auth Felix
 * @since 2025/3/19 21:15
 */
@RequestMapping("/record")
@RestController
public class LeaveRecordController {


    @GetMapping("/list")
    private List<LeaveRecords> queryRecords() {
       return null;
    }
}
