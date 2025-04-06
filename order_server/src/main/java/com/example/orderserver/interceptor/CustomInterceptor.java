package com.example.orderserver.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

public class CustomInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        // 请求前逻辑（如添加 Header）
        System.out.println("请求 URI: " + request.getURI());
        request.getHeaders().add("Authorization", "Bearer token");

        // 执行请求
        ClientHttpResponse response = execution.execute(request, body);

        // 响应后逻辑（如记录状态码）
        System.out.println("响应状态码: " + response.getStatusCode());
        return response;
    }
}