package com.example.orderserver.service;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @auth Felix
 * @since 2025/3/14 2:29
 */
@RefreshScope
//@NacosConfigurationProperties(dataId = "order-server-dev.yaml",groupId = "DEFAULT_GROUP", autoRefreshed = true)
@Component
//@ConfigurationProperties
public class ConfigService {

    @Value(value = "${user.name}")
    private String username;

    @Value(value = "${user.age}")
    private String age;

    @Value("${password}")
    private String password;

    public String config() {
        System.out.println("username=" + username);
        System.out.println("password=" + password);
        System.out.println("age=" + age);
        return "username=" + username + "\n;age=" + age + "\npassword=" + password;
    }
}
