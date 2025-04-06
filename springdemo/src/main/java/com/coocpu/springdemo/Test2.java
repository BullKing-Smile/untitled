package com.coocpu.springdemo;

import com.coocpu.springdemo.proxy.ProxyUtil;
import com.coocpu.springdemo.service.IUserService;
import com.coocpu.springdemo.service.UserService;

/**
 * @auth Felix
 * @since 2025/3/15 15:08
 */
public class Test2 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        UserService userService = new UserService();
        IUserService proxyInstance = ProxyUtil.getProxyInstance(userService);
        proxyInstance.login();
        proxyInstance.getUserInfo();
    }
}
