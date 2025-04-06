package com.coocpu.security_db_api_demo.listener;

import com.coocpu.security_db_api_demo.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @auth Felix
 * @since 2025/4/1 15:41
 */
@Component
public class ApplicationContentListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("spring boot context closing~~~");
        // 删除 所有用户登录的token
        redisTemplate.delete(Constants.redis_token_key);
    }
}
