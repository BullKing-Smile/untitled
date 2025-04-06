package com.example.orderserver.config;

import com.example.orderserver.interceptor.CustomInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @auth Felix
 * @since 2025/3/14 14:58
 */

@Configuration
public class AppConfig {
//
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate1(RestTemplateBuilder builder) {
//        RestTemplate restTemplate = builder.build();
//        restTemplate.getInterceptors().add(new CustomInterceptor());
//        System.out.println("实例了，restTemplate = "+restTemplate);
//        return restTemplate;
//    }

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CustomInterceptor());
        System.out.println("实例了，restTemplate = "+restTemplate);
        return restTemplate;
    }
}