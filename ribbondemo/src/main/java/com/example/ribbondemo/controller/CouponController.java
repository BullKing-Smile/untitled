package com.example.ribbondemo.controller;

import com.example.ribbondemo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @auth Felix
 * @since 2025/3/13 14:51
 */
@RestController
@RequestMapping(path = "/")
public class CouponController /*implements InitializingBean */ {

    private CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupon/one")
    private String getNewCouponOne() {
        return couponService.getNewCouponOne();
    }

}
