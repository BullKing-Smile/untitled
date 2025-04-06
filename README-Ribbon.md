# Ribbon
> 负载均衡器

目前已经被废弃， 官方推荐： Spring Cloud LoadBalancer


目前主流的 负载均衡方案：
- 集中式负载均衡
- 客户端根据自己的请求情况做负载均衡， ***Ribbon就属于<font color=yellow>客户端</font>自己做负载均衡***



## 负载均衡算法
- 随机
- 轮询
- 加权轮询
- 地址Hash
- 最小连接数



RandomRule 随机策略
RoundRobinRule 线性轮询负载均衡策略
RetryRule 重试策略， 在轮询的基础上重试
WeightedResponseTimeRule 权重策略
BestAvailableRule 过滤掉失效的服务实例， 然后找出并发请求最小的服务实例来使用。
ZoneAvoidanceRule 默认规则 符合判断Server所在区域的性能和Server的可用性选择服务器 没有区域的概念， 则采用线性轮询 RoundRobinRule

## 修改配置策略的两种方式
- @Configuration
- xml

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
@Configuration
public class RibbonCofnig {
    // 方法名称 固定 iRule
    @Bean
    public IRule iRule() {
        return new NacosRule();
    }
}
```
启动类 Application 添加@RibbonClients
```java
@RibbonClients(values = {
        @RibbonClient(name = "mall-order", configuration = RibbonConfig.class),
        @RibbonClient(name = "mall-account", configuration = RibbonConfig.class)
})
public class MallApplication {
    
}
```








## Spring Cloud LoadBalancer
> Spring Cloud 官方推出的新一代客户端负载均衡器，<br>
> 用于替代已弃用的 Netflix Ribbon。<br>
> 它深度集成 Spring 生态，支持响应式编程模型，提供更灵活、轻量级的负载均衡能力<br>
> 


### 快速集成
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

### 启用负载均衡
> 通过 @LoadBalanced 注解标记 RestTemplate 或 WebClient：

```java
@Configuration
public class LoadBalancerConfig {

    @Bean
    @LoadBalanced  // 启用负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced  // 支持 WebFlux 响应式调用
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
```

### 发起服务调用

```java
@Autowired
private RestTemplate restTemplate;

public String callService() {
    return restTemplate.getForObject("http://user-service/api/info", String.class);
}
```

### 特定服务指定配置策略
```java
@Configuration
@LoadBalancerClient(name = "order-service", configuration = OrderServiceLoadBalancerConfig.class)

```


