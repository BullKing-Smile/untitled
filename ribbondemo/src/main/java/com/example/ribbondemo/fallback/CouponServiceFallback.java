package com.example.ribbondemo.fallback;

import com.example.ribbondemo.feign.ICouponService;
import org.springframework.stereotype.Component;

/**
 * @auth Felix
 * @since 2025/3/17 19:37
 */
@Component
public class CouponServiceFallback implements ICouponService {

    // 本质是在原方法 上加一个try catch, 超过重试次数后依旧调用失败， 则回调该方法
    // 服务降级调用的方法
    @Override
    public String getCoupon() {
        return "获取失败，这是一个降级模拟返回coupon";
    }

    @Override
    public String getCouponOne() {
        return "获取 Coupon/one failed";
    }
}
