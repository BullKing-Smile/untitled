package com.example.orderserver.service;

import com.example.orderserver.feign.ICouponService;
import org.springframework.stereotype.Service;

/**
 * @auth Felix
 * @since 2025/3/14 2:29
 */
@Service
public class OrderService {
    private final ICouponService couponService;


    public OrderService(ICouponService couponService) {
        this.couponService = couponService;
    }

    public String addOrder() {
        return "Add order success " + System.currentTimeMillis();
    }

    public String getNewCoupon() {
        return couponService.getCoupon() + "||" + System.currentTimeMillis();
//        return  "||" + System.currentTimeMillis();
    }

    public String getNewCouponOne() {
        return couponService.getCouponOne();
//        return  "||" + System.currentTimeMillis();
    }
//
//    @PostConstruct
//    public void init() {
//        System.out.println("OrderService has been initialized and is managed by Spring.");
//    }
}
