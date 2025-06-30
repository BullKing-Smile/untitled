# Hippo4j

[代码地址](https://gitee.com/opengoofy/hippo4j)

痛点：
- 线程池不能随便定义， 线程数多了任务慢， 少了 资源利用不到位
- 线程池参数的评估成本太高， 测试过程麻烦， 如果出现变动， 需要重新部署
- 线程池中的线程执行任务 如果超时了无法感知， 无法快速处理这个问题
- 如果出现了流量激增， 大量任务堆积到阻塞队列， 或者触发拒绝策略， 影响业务


Hippo4j 为了解决上面问题而诞生

- 所有线程池都可以交给 Hippo4j
- Hippo4j可以**动态的修改线程池的核心参数**， 以更好的适配硬件资源，提升处理速度
- Hippo4j 还提供了**报警功能**， 快速发现问题
- 监控线程池运行时的一些数据信息




### Hippo4j 快速入门
两个角色 
- Server --- 图形化界面，监控内容
- Client --- 项目工程


### 快启动

#### Docker 版本启动


#### Hippo4j-Server 源码的方式启动
1. 下载代码 并切换到最新发布版本（当前是1.5.0）  https://gitee.com/opengoofy/hippo4j.git  
2. hippo4j-server -> hippo4j-bootstrap 在这个项目module是启动的最终项目
3. src->main->resources->application.properties 配置文件修改 数据库配置信息
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:13306/hippo4j_manager?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=password
```
4. 启动web服务


#### Hippo4j-Client 无中间件启动
[接入流程文档](https://hippo4j.github.io/docs/user_docs/getting_started/server/hippo4j-server-start/)

1. 引入依赖
```xml
 <dependency>
    <groupId>cn.hippo4j</groupId>
    <artifactId>hippo4j-spring-boot-starter</artifactId>
    <version>1.5.0</version>
</dependency>
```
2. 添加配置
！！！ Attention！！！ 
租户(Tenant) 和 项目(Item) 必须先再Hippo4j-Server中创建(Dashboard)
```yaml
spring:
  profiles:
    active: dev
  application:
    # 服务端创建的项目 id 需要与 application.name 保持一致
    name: dynamic-threadpool-example
  dynamic:
    thread-pool:
      # 服务端地址
      server-addr: http://localhost:6691
      # 用户名
      username: admin
      # 密码
      password: 123456
      # 租户 id, 对应 tenant 表
      namespace: prescription
      # 项目 id, 对应 item 表
      item-id: ${spring.application.name}
```

3. 同台线程池配置
```java
@Configuration
public class Hippo4jThreadPoolConfiguration {
    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor wsfDynamicThreadPool() {
        ThreadPoolExecutor build = ThreadPoolBuilder.builder()
                .corePoolSize(10)
                .maximumPoolSize(10)
                .keepAliveTime(10, TimeUnit.SECONDS)
                .workQueue(BlockingQueueTypeEnum.RESIZABLE_LINKED_BLOCKING_QUEUE)
                .threadFactory("wsf-thread-id")
                .threadPoolId("wsf-thread-id")
                .dynamicPool()
                .rejected(new ThreadPoolExecutor.AbortPolicy())
                .build();
        return build;
    }
}
```
Application.java 添加配置@EnableDynamicThreadPool
```java
@SpringBootApplication
@EnableDynamicThreadPool
public class Hippo4jClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hippo4jClientApplication.class, args);
    }
}

```


4. 问题修复
jdk-8.0 以上会出下如下问题， 表象为ThreadPoolManage修改后， 服务重启不生效
```text
DynamicThreadPoolPostProcessor : Failed to initialize thread pool configuration. error message: Unable to make field private final java.util.concurrent.BlockingQueue java.util.concurrent.ThreadPoolExecutor.workQueue accessible: module java.base does not "opens java.util.concurrent" to unnamed module @4550bb58
```
解决： 添加VM参数
Run → Edit Configurations → VM Options
--add-opens java.base/java.util.concurrent=ALL-UNNAMED

5. 动态修改线程池参数
Hippo4j-Server -> Dashboard -> DynamicThreadPool -> ThreadPool Instance 
修改参数后，会自动下发到各个订阅服务 完成动态修改线程池参数。

6. 动态修改Service Container线程参数（Tomcat,Undertow,Jetty...）


### 框架线程池监控
Hippo4j 支持多种框架的线程池监控功能
包括， Dubbo, Hystrix, RabbitMQ, RocketMQ 同时还支持SpringCloud中Stream组件



#### RabbitMQ 开启图形化界面
1. 启动后 进入/opt/rabbitmq/sbin 执行 ./rabbitmq-plugins enable rabbitmq_management

docker exec -it rabbitmq bash
rabbitmq-plugins enable rabbitmq_management

default username/password (guest, guest)

启动后默认登录地址： 
url: http://localhost:15672
name: guest
pwd: guest


#### 在hippo4j-client项目中 最近RabbitMQ配置信息
对于其他平台的代码集成，可以查看hippo4j-server代码中example module.

- 导入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
- 编写配置文件
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
```
- 编写配置类
```java
@Configuration
public class RabbitMQConfig {

    @Bean
    public ThreadPoolTaskExecutor rabbitThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(6);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadNamePrefix("rabbit-");
        return threadPoolTaskExecutor;
    }

    @Bean
    public AbstractRabbitListenerContainerFactory<?> defaultContainerFactory(ThreadPoolTaskExecutor rabbitThreadPool, AbstractConnectionFactory connectionFactory) {
        AbstractRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        connectionFactory.setExecutor(rabbitThreadPool);

        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
```
- 构建消费者
```java
@Component
public class RabbitListenerDemo {

    @RabbitListener(queues = "DVH-20250605-HIPPO4J", containerFactory = "defaultContainerFactory")
    public void consume(String message) {
        System.out.println(Thread.currentThread().getName()+"= 消息："+message);
    }
}
```
- 查看消费消息


#### 通知报警功能

Hippo4j 提供了各种通知功能
- 线程池配置改变了 会通知
- 报警 
  - 任务执行超时 报警
  - 线程 活跃度 报警
  - 容量报警， 工作队列中的任务达到了多少
  - 线程池 执行了拒绝策略 也会报警


钉钉机器人
https://oapi.dingtalk.com/robot/send?access_token=4e2ffe945d540e27989046ba9459dfe42b29fa5a9850400d9ed628c8a415aa4e


### Jmeter 压测
[下载地址](https://jmeter.apache.org/download_jmeter.cgi)

解压后 -> bin目录 -> 双击启动jmeter.bat


#### Linux 安装Java环境（配置环境变量）
- 上传文件jdk
- 解压缩 tar -zxf jdk-17.0.12_linux-x64_bin.tar.gz -C /usr/local
- 修改加压缩后的文件夹名称(名称太行) mv jdk-17.0.12/ jdk/
- 编辑文件 vi /etc/profile

- 添加内容
export JAVA_HOME=/usr/local/jdk
export PATH=$JAVA_HOME/bin:$PATH

- 保存退出
wq!
- 加载配置
source /etc/profile
- 启动服务
java -jar Hippo4jClientWithoutMiddleware-1.0-SNAPSHOT.jar --spring.profiles.active=test

#### 安装docker

sudo systemctl daemon-reload
sudo systemctl restart docker
