package com.example.ribbondemo;

import com.example.ribbondemo.config.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "round-balancer",configuration = LoadBalancerConfig.class)
})
public class RibbondemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbondemoApplication.class, args);
    }

}
