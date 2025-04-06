package com.example.orderserver.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @auth Felix
 * @since 2025/3/23 17:30
 */
@Slf4j
@Configuration
public class NacosRefreshEvent {

//    @Bean
//    public PropertySourcesPlaceholderConfigurer configurer() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        configurer.setIgnoreUnresolvablePlaceholders(true);  // 忽略未解析的占位符
//        return configurer;
//    }

    @EventListener
    public void handleRefreshEvent(RefreshEvent event) {
        log.info("配置已更新，当前配置: {}", event.toString());
    }
}
