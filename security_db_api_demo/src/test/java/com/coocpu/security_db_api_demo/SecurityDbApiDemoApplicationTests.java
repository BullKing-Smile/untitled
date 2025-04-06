package com.coocpu.security_db_api_demo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coocpu.security_db_api_demo.constant.Constants;
import com.coocpu.security_db_api_demo.utils.JWTUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class SecurityDbApiDemoApplicationTests {

    @Resource
    private JWTUtil jwtUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void createToken() {
        JSONObject json = new JSONObject();
        json.set("id", 1);
        json.set("name", "tom");
        json.set("time", "2025-10-09");
        String token = jwtUtil.createToken(JSONUtil.toJsonStr(json));
        System.out.println(token);
        Assert.notNull(token, "success~~~");
    }

    @Test
    void verifyToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoie1wiaWRcIjpbMV0sXCJuYW1lXCI6W1widG9tXCJdLFwidGltZVwiOltcIjIwMjUtMTAtMDlcIl19IiwidXNlcm5hbWUiOiJ6aGFuZ3NhbiIsInBob25lIjoiMTM4MDAwMDk5OTkifQ.F98KxvuQT3bdCzY18p4cRN06zb-PkcbUarBbHlkkjSA";
        Boolean aBoolean = jwtUtil.verifyToken(token);
        System.out.println(aBoolean);
        Boolean aBoolean2 = jwtUtil.verifyToken(token+"a");
        System.out.println(aBoolean2);
        Assert.isTrue(aBoolean, "Verify Success");
    }

    @Test
    void parseToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoie1wiaWRcIjpbMV0sXCJuYW1lXCI6W1widG9tXCJdLFwidGltZVwiOltcIjIwMjUtMTAtMDlcIl19IiwidXNlcm5hbWUiOiJ6aGFuZ3NhbiIsInBob25lIjoiMTM4MDAwMDk5OTkifQ.F98KxvuQT3bdCzY18p4cRN06zb-PkcbUarBbHlkkjSA";
        String username = jwtUtil.parseToken(token);
        System.out.println(username);
        Assert.notNull(username, "Parse Success");
    }


    @Test
    void queryTokenFromRedis() {
        // save
        redisTemplate.opsForHash().put("aaa:bbb:ccc", "100", "100-eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.100");
        redisTemplate.opsForHash().put("aaa:bbb:ccc", "101", "101-eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.101");
        redisTemplate.opsForHash().put("aaa:bbb:ccc", "102", "102-eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.102");

        // get
        String o = (String) redisTemplate.opsForHash().get("aaa:bbb:ccc", "100");
        System.out.println(o);
        Assert.notNull(o, "Token query success~~~");
    }

}
