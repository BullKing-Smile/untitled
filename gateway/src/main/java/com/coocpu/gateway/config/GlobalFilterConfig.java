package com.coocpu.gateway.config;

import com.alibaba.nacos.common.utils.ConcurrentHashSet;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * @auth Felix
 * @since 2025/3/23 21:36
 */
@Component
public class GlobalFilterConfig implements GlobalFilter, Ordered {

    private final String HEADER_NAME = "Access-Token";
    private final Set<String> NOTFILTERPATH = new ConcurrentHashSet<>();
    {
        NOTFILTERPATH.add("/coupon-server/coupon/one");
        NOTFILTERPATH.add("/coupon-server/coupon/new");
        NOTFILTERPATH.add("/order-server/coupon/one");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("=====global filter =====");
        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();

        String url = request.getURI().getPath();
        System.out.println("url="+url);
        // 判断是否是白名单请求， 以及一些内置不需要验证的请求
        // 如果请求当中token不为空， 也会验证token合法性 这样能保证
        // Token 中的用户信息被业务接口正常访问到了， 而如果当token为空的时候 白名单接口
        // 也可以被王光直接转发， 无登录验证。 当然被转发的接口， 也无法获得身份验证
//        if (this.shouldNotDoFilter(url)) {
//            return chain.filter(exchange);
//        }
        if (null != url) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(HEADER_NAME);

        // 为空 或者 缓存中没有 该token
        if (!StringUtils.hasLength(token)) {
//            return chain.filter(exchange);
            return unAuthorize(exchange);
        }
        // 把新的exchange 放回到过滤器
        ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, token).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    private boolean shouldNotDoFilter(String url) {
//        if (url.startsWith("/user/login")) {
//            return true;
//        }
        if (NOTFILTERPATH.contains(url)) {
            return true;
        }
        return false;
    }

    private Mono<Void> unAuthorize(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorMessage = "{\"msg\":\"authorize failed, login pls\"}";
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes())));
    }

    // 值越小优先级越高
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
