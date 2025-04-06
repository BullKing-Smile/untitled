package com.example.orderserver.controller;

import com.example.orderserver.service.ConfigService;
import com.example.orderserver.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
//@RefreshScope
@RestController
@RequestMapping(path = "/")
public class OrderController /*implements InitializingBean */ {

    @Resource
    private ConfigService configService;

    private final OrderService orderService;

    //    @Autowired
    private final ApplicationContext applicationContext; // 直接注入上下文

    //    @Autowired
    private final RestTemplate restTemplate;
//    @Autowired
//    private RestTemplate restTemplate1;

    @Autowired
    public OrderController(RestTemplate restTemplate, ApplicationContext applicationContext, OrderService orderService) {
        this.restTemplate = restTemplate;
        this.applicationContext = applicationContext;
        this.orderService = orderService;
//        this.configService = configService;
        System.out.println("构造函数，restTemplate = " + restTemplate + "; this=" + this);
    }


    //    @SentinelResource(value = "add", fallback = "fallbackHandler")
    @GetMapping("/add")
    private String addOrder() {
        System.out.println("实例(add) context=" + applicationContext + "; this=" + this);

//        Object rt = applicationContext.getBean("restTemplate");
        System.out.println("实例 context=" + applicationContext + ";restTemplate=" + restTemplate);
//        System.out.println("实例 restTemplate=" + restTemplate + ";restTemplate1=" + restTemplate1);

        String forObject = null;
//        if (restTemplate != null) {
            forObject = restTemplate.getForObject("http://127.0.0.1:8082/coupon/new", String.class);

//        } else {
//            forObject = restTemplate1.getForObject("http://127.0.0.1:8082/coupon/new", String.class);
//
//        }
        return forObject + "||" + System.currentTimeMillis();
//        return orderService.addOrder();
    }

    //    @SentinelResource(value = "getNewCoupon", fallback = "fallbackHandler")
    @GetMapping("/coupon")
    private String getNewCoupon() {
        return orderService.getNewCoupon();
    }

    @GetMapping("/coupon/one")
    private String getNewCouponOne() {
        return orderService.getNewCouponOne();
    }

    @RefreshScope
    //    @SentinelResource(value = "config", fallback = "fallbackHandler")
    @GetMapping("/config")
    private String config() {
        return configService.config();
    }

    public String fallbackHandler() {
        return "Fallback response!---!";
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("实例(order controller) context="+applicationContext+"; this="+this);
//    }
}
