package com.example.orderserver;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"com.example.orderserver"})
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderServerApplication.class, args);
        String username = applicationContext.getEnvironment().getProperty("user.name");
        String password = applicationContext.getEnvironment().getProperty("password");
        String age = applicationContext.getEnvironment().getProperty("user.age");
        System.out.println("username="+username);
        System.out.println("password="+password);
        System.out.println("age="+age);
        System.out.println("实例 context="+applicationContext);
    }

}
