package com.example.ribbondemo.feign;

import com.example.ribbondemo.fallback.CouponServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auth Felix
 * @since 2025/3/14 2:17
 */
@Component
@FeignClient(value = "coupon-server", fallback = CouponServiceFallback.class)
public interface ICouponService {

    @GetMapping("/coupon/new")
    String getCoupon();
    @GetMapping("/coupon/one")
    String getCouponOne();
}
