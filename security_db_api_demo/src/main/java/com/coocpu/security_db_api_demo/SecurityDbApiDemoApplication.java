package com.coocpu.security_db_api_demo;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication
public class SecurityDbApiDemoApplication implements CommandLineRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SecurityDbApiDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // redis 的 key 采用string序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // redis 的 hashKey 采用string序列化
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // redis 的 hashValue 采用string序列化
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
    }
}
