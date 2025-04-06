package com.example.orderserver.feign;

import com.example.orderserver.fallback.CouponServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auth Felix
 * @since 2025/3/14 2:17
 */
@Component
@FeignClient(value = "coupon-server", path = "/coupon", fallback = CouponServiceFallback.class)
public interface ICouponService {

    @GetMapping("/new")
    String getCoupon();
    @GetMapping("/one")
    String getCouponOne();
}
