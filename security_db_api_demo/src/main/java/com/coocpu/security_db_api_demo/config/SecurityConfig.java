package com.coocpu.security_db_api_demo.config;

import com.coocpu.security_db_api_demo.filter.CaptchaFilter;
import com.coocpu.security_db_api_demo.filter.TokenFilter;
import com.coocpu.security_db_api_demo.handler.MyAccessDeniedHandler;
import com.coocpu.security_db_api_demo.handler.MyAuthenticationFailureHandler;
import com.coocpu.security_db_api_demo.handler.MyAuthenticationSuccessHandler;
import com.coocpu.security_db_api_demo.handler.MyLogoutSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @auth Felix
 * @since 2025/3/29 20:26
 */
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Resource
    private CaptchaFilter captchaFilter;
    @Resource
    private TokenFilter tokenFilter;

    @Resource
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private MyLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private MyAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        // 跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*")); // 允许任何来源
        corsConfiguration.setAllowedMethods(List.of("*")); // 允许任何 方法 Get/Post/Put/Head/Delete
        corsConfiguration.setAllowedHeaders(List.of("*")); // 允许任何 请求头参数

        // 注册跨域配置
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(formLogin -> {
                    formLogin.loginProcessingUrl("/user/login") // 登录的账号密码往哪个 地址提交
                            .successHandler(authenticationSuccessHandler) // 登录成功处理器
                            .failureHandler(authenticationFailureHandler); // 登录失处理器
                })
                .logout(logout->{
                    logout.logoutUrl("/user/logout")
                            .logoutSuccessHandler(logoutSuccessHandler);
                })
                // 把所有 接口都会进行登录状态的检查行为都加回来
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers("/common/captcha").permitAll()
                            // 任何请求都是认证(登录)后才能访问， 但是spring security提供的地址除外
                            .anyRequest().authenticated();
                })
                // 禁用Session/Cookie机制 (前后端分离的项目 使用jwt共享状态)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    // 设置无状态策略
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 验证码filter 添加在 用户名密码登录过滤器 之前
//                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenFilter, LogoutFilter.class)
                .csrf(AbstractHttpConfigurer::disable) // 禁用跨站请求伪造
                .cors(cors -> {
                    //允许前端跨域访问
                    cors.configurationSource(corsConfigurationSource());
                })
                .exceptionHandling(exceptionHandling->{
                    exceptionHandling.accessDeniedHandler(accessDeniedHandler);
                })
                .build();
    }
}
