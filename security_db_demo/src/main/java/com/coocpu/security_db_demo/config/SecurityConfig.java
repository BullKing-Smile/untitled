package com.coocpu.security_db_demo.config;

import com.coocpu.security_db_demo.filter.CaptchaFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @auth Felix
 * @since 2025/3/29 20:26
 */
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Resource
    private CaptchaFilter captchaFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(formLogin -> {
                    formLogin.loginPage("/toLogin")
                            .loginProcessingUrl("/user/login")
                            .successForwardUrl("/welcome"); // 定制登录成功后跳转的地址
                })
                // 把所有 接口都会进行登录状态的检查行为都加回来
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers("/toLogin", "/common/captcha").permitAll()
                            .anyRequest().authenticated();
                })
                // 验证码filter 添加在 用户名密码登录过滤器 之前
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
