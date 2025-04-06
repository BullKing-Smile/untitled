package com.coocpu.springdemo.proxy;

import com.coocpu.springdemo.service.IUserService;
import com.coocpu.springdemo.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @auth Felix
 * @since 2025/3/15 22:58
 */
public class ProxyUtil {

    public static IUserService getProxyInstance(UserService userService) {
        IUserService o = (IUserService) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{IUserService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equalsIgnoreCase("login")
                                || method.getName().equalsIgnoreCase("getUserInfo")) {
                            long startTime = System.currentTimeMillis();

                            System.out.println("---- start " + method.getName() + " ----\n start = " + startTime);
                            Object invoke = method.invoke(userService, args);
                            long endTime = System.currentTimeMillis();

                            System.out.println("---- end fun ----\n end = " + endTime);
                            System.out.println("cost time = " + (endTime - startTime));
                            return invoke;
                        } else {
                            Object rx = method.invoke(method, args);
                            return rx;
                        }
                    }
                });
        return o;
    }

}
