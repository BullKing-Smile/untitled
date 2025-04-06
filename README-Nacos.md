# Nacos
> 构建原生应用的动态 服务发现/服务配置/服务管理的 平台

按照连接内流程启动服务
> https://nacos.io/docs/next/quickstart/quick-start-docker/

## Basic knowledge

## Feature
- 服务发现和服务健康检测
- 动态配置服务
- 动态DNS服务
- 服务机器元数据管理


## Nacos 注册中心
> 存储再mysql

### 核心功能
- 服务注册 --- Nacos Client通过REST请求向Nacos Server注册自己的服务
- 服务心跳 --- Client维护一个心跳(default 5s)通知Nacos Server保活
- 服务同步 --- Nacos Server集群之间会同步服务实例，保证信息一致性
- 服务发现 --- Client调用其他服务是，会发送REST获取服务清单，并缓存，启动一个定时任务拉去更新服务状态
- 服务健康检查 --- Server 会开启一个定时任务 检查注册服务实例的健康状(15s->Unhealthy, 30s Removed)

https://sca.aliyun.com/docs

## Nacos 注册表结构
- 


### 配置
// 临时实例配置， default true 是临时实例，服务下线则被剔除， false 则是永久实例。
spring.cloud.nacos.discovery.ephemeral: true or false



### 集群模式



### 负载均衡器 Ribbon 默认引用的组件


## 配置中心
Nacos提供用于存储配置和其他元数据的key-value格式数据存储， 为分布式系统中的外部化配置提供服务端和客户端支持。

命名方式
com.project.service.itemname

配置后缀默认时properties

### Bean中 动态感知配置更新
> @RefreshScope


注册表结构
Map<namespace, Map<group::serviceName, Service>>
namespace 用来区分环境 eg dev, test, pro
group 用来做微服务分组的 原有的自服务 继续拆分的场景




## Nacos + OpenFeign 服务之间通信 (类似RPC)
- 服务提供者， 启动后注册服务(服务中心) --> Nacos维护一个注册表Map<String, List<URL>> 存放服务对应的服务器列表
- 消费者启动后 先去注册中心拉去服务列表 缓存在本地 --> 监听机制 来维护更新
- 消费者发起 第三方服务的网络请求调用 --> 携带(1.接口名 2.方法名 3. 方法参数类型列表 4.方法参数值列表)
- 消费者收到请求结果 --> 处理结果

- 更新机制 消费者更新本地注册表

- 重试机制 保证结果正确处理和接受

看着像是RPC通信， 实际上 底层依旧是HTTP协议的通信方式
OpenFeign虽然提供了类似RPC的接口声明方式，但底层使用的是HTTP协议，因此更准确地说，它属于基于HTTP的客户端工具，而不是传统的RPC框架



常见的RPC框架由gRPC, Dubbo等


### OpenFeign
HTTP请求构建
```java
@FeignClient(name = "user-service", path = "/api/user")
public interface UserFeignClient {
    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Long id);
}
```
> - 代理类解析注解中的HTTP方法 GET, POST
> - 路径\/api\\/user/{id}）
> - 参数绑定 <font color=yellow> @PathVariable </font> 等信息，生成完整的HTTP请求。


### OpenFeign 默认继承了Ribbon 实现负载均衡
负载均衡策略：
- 轮询
- 随机
- 加权

### QPS(Query Per Second) 每秒查询率
聚焦 请求级处理能力
OpenFeign QPS 达到10K以上可以考虑RPC

### TPS(Transactions Per Second)每秒事务执行数
聚焦 事务级处理能力

示例：
用户访问商品详情页（1次HTTP请求 = 1 QPS）。
用户完成支付（包含下单、扣款、生成订单 = 1 TPS）。