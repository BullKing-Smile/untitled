package com.coocpu.springdemo;

import com.coocpu.springdemo.service.UserService;

/**
 * @auth Felix
 * @since 2025/3/15 15:08
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
        UserService userService1 = (UserService) myApplicationContext.getBean("userService");
        UserService userService2 = (UserService) myApplicationContext.getBean("userService");
        UserService userService3 = (UserService) myApplicationContext.getBean("userService");
        System.out.println(userService1);
        System.out.println(userService2);
        System.out.println(userService3);
    }
}
