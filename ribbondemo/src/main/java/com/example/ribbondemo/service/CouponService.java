package com.example.ribbondemo.service;

import com.example.ribbondemo.feign.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auth Felix
 * @since 2025/3/14 2:29
 */
@Service
public class CouponService {
    private final ICouponService couponService;

    @Autowired
    public CouponService(ICouponService couponService) {
        this.couponService = couponService;
    }


    public String getNewCoupon() {
        return couponService.getCoupon() + "||" + System.currentTimeMillis();
    }

    @Transactional
    public String getNewCouponOne() {
        return couponService.getCouponOne();
    }
}
