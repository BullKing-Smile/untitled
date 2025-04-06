# Dubbo

> Apache Dubbo 是一款高性能、轻量级的 Java <font color=yellow>***RPC***</font>（远程过程调用）框架，
> 专为分布式系统设计，由阿里巴巴开源并贡献给 Apache 基金会，现为顶级项目。
> 它提供了服务治理、负载均衡、服务容错等核心功能，广泛应用于微服务架构中。

## Dubbo 核心功能
- 服务通信与协议支持
- 服务治理
- 扩展性与可观测性



### 服务通信与协议支持
- 多协议支持：默认使用基于 TCP 的 Dubbo 协议（高性能二进制协议），也支持 HTTP/HTTPS、REST、gRPC、Thrift 等。
- 高性能传输：通过 NIO 异步通信和序列化优化（Hessian2、Kryo、Protobuf 等），降低网络开销。


### 服务治理
- 服务注册与发现：集成 ZooKeeper、Nacos、Consul 等注册中心，动态管理服务地址。
- 负载均衡：支持随机、轮询、最少活跃调用、一致性哈希等多种策略。
- 容错机制：失败自动切换（Failover）、快速失败（Failfast）、并行调用（Forking）等容错模式。
- 服务限流与降级：通过配置实现流量控制和服务熔断。

### 扩展性与可观测性
- SPI 扩展机制：基于 JDK SPI 增强，支持模块化扩展（协议、注册中心、序列化等）。
- 监控中心：集成 Prometheus、Grafana 等，提供调用链路追踪、QPS 统计、服务健康检查。




## Dubbo 核心架构
Dubbo 采用分层设计，各层可独立扩展：

1. 服务接口层（API）：定义服务接口（如 UserService）。
2. 配置层（Config）：解析 XML、注解或 API 方式的配置。
3. 代理层（Proxy）：生成服务消费者和提供者的代理对象。
4. 注册层（Registry）：与注册中心交互，管理服务地址。
5. 路由层（Cluster）：负载均衡、集群容错、路由规则。
6. 监控层（Monitor）：统计调用次数和耗时。
7. 协议层（Protocol）：定义 RPC 协议（如 Dubbo、HTTP）。
8. 传输层（Transport）：网络传输（Netty、Mina）。
9. 序列化层（Serialize）：对象与二进制数据的转换。






## Dubbo Dubbo 核心组件

| 角色	       | 作用                                         |
|:----------|:-------------------------------------------|
| Provider	 | 服务提供者，暴露服务接口实现。                            |
| Consumer	 | 服务消费者，调用远程服务。                              |
| Registry	 | 注册中心（如 ZooKeeper、Nacos），负责服务地址注册与发现。       |
| Monitor	  | 监控中心，收集服务调用统计信息。                           |
| Container | 服务运行容器（如 Spring、Tomcat），管理 Provider 生命周期。  |



### 负载均衡算法
- Random --- 加权随机
- RoundRobin --- 加权轮询
- LeastActive --- 最小活跃数
- ConsistentHash --- 一致性哈希   使相同参数请求总是发到同一提供者


| 策略名称	                  | 说明                                           | 	适用场景 |
|:-----------------------|:---------------------------------------------|-------|
| Random（加权随机）           | 	根据权重随机分配请求，默认策略。	大多数场景，请求处理时间差异不大时。         |
| RoundRobin（加权轮询）	      | 按权重轮询分配请求。	请求处理时间均匀，需要顺序分配的场景。               |
| LeastActive（最小活跃数）	    | 优先选择当前活跃调用数最少的 Provider。	处理时间差异大，需动态感知负载情况。  |
| ConsistentHash（一致性哈希）  | 	相同参数的请求总是路由到同一 Provider。	需要会话保持或缓存命中的场景。    |



## 代码示例

### Dubbo 服务定义与调用
```java
// 服务接口定义（Provider 和 Consumer 共享）
public interface UserService {
    User getUserById(Long id);
}

// 服务提供者实现
@Service // Dubbo 的 @Service 注解
public class UserServiceImpl implements UserService {
    public User getUserById(Long id) {
        return new User(id, "Alice");
    }
}

// 服务消费者调用
@Reference // Dubbo 的 @Reference 注解
private UserService userService;

public void test() {
    User user = userService.getUserById(1L);
}
```




## Dubbo 3.0 新特性
- 注册模型的改变
- 新一代RPC协议



### 注册模型的改变
2.x 版本基于 <font color=orange>***接口***</font> 粒度的服务发现机制，
3.x 引入了全新的<font color=yellow>***基于应用粒度的服务发现机制***</font>

新模式具有两大优势
1. <font color=yellow>***性能和稳定性 提升， 大幅度提高系统资源利用率； 单机内存消耗降低50%； 注册中心集群的存储和推送压力降低90%； 集群规模步入 百万实例级别***</font>
2. 大同与其他异构微服务 体系的地址互发现障碍。


### 新一代RPC协议 --- Triple
Triple 基于 HTTP/2 上构建的RPC协议， 完全兼容了gRPC

优点
- 更容易适配到网关/Mesh框架
- 多语言友好
- 流式通信支持









