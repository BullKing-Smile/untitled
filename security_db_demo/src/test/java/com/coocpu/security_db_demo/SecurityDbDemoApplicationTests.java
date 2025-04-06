package com.coocpu.security_db_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

@SpringBootTest
class SecurityDbDemoApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void generatePassword() {
        String pwd = "123123";
        for (int i = 0; i < 3; i++) {
            String encode = passwordEncoder.encode(pwd);
            System.out.println(encode);
        }

        Assert.notNull(true, "Success");
    }
}
