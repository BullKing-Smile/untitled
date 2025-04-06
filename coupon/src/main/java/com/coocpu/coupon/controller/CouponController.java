package com.coocpu.coupon.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth Felix
 * @since 2025/3/14 1:36
 */
//@RefreshScope
@RestController
@RequestMapping(path = "/coupon")
public class CouponController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/new")
    public String getCoupon() {
        return "New coupon "+ System.currentTimeMillis();
    }

    @GetMapping("/one")
    public String getCouponOne() {
        return "New coupon "+ System.currentTimeMillis()+"-" + port;
    }
}
