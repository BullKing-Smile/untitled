package com.coocpu.springdemo.service;

import com.coocpu.springdemo.spring.Component;
import com.coocpu.springdemo.spring.Scope;

import java.util.concurrent.TimeUnit;

/**
 * @auth Felix
 * @since 2025/3/15 15:10
 */
@Scope("profo")
@Component("userService")
public class UserService implements IUserService {
    public String getUserName() {
        return "username is " + System.currentTimeMillis();
    }

    @Override
    public void login() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--- 开始登陆 ---");
    }

    @Override
    public String getUserInfo() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "user name is abc";
    }
}
