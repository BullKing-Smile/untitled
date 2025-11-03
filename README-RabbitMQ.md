# RabbitMQ

RabbitMQ 是一个开源的消息代理和队列服务器，用于在分布式系统之间异步传递消息。



标准的 消息队列协议(AMQP)





## 1 基础

### 1.1 核心组件有哪些？

- **生产者(Producer)**：发送消息的应用
- **消费者(Consumer)**：接收消息的应用
- **队列(Queue)**：存储消息的缓冲区
- **交换机(Exchange)**：接收生产者消息并根据规则路由到队列
- **绑定(Binding)**：交换机和队列之间的连接规则
- **虚拟主机(Virtual Host)**：隔离的独立环境







### 1.2 交换机类型

1. **直连交换机(Direct Exchange)**：**精确匹配**路由键
2. **扇出交换机(Fanout Exchange)**：**广播到所有绑定队列**  （忽略路由键）
3. **主题交换机(Topic Exchange)**：基于**通配符模式匹配**路由键 ， 即消息可以发送到多个 队列 进行路由
4. **头交换机(Headers Exchange)**：基于消息头属性匹配





### 1.3 保证消息不丢失？

- **<font color=red>生产者确认模式(Publisher Confirm)</font>**：确保消息到达 RabbitMQ
- **事务机制**：但性能较差，不推荐生产环境使用
- **<font color=red>持久化</font>**：交换机、队列和消息都设置为持久化
- **<font color=green>消费者确认(ACK)</font>**：消费者正确处理后才确认消息







### 1.4 如何保证消息顺序？

**<font color=red>单个队列中可以保证 FIFO 顺序</font>**

需要注意的是：

1. 不要使用多消费者
2. 不要使用优先级 队列
3. 确保消息 不会重入







### 1.5 什么是死信队列(DLX)？

**<font color=red>存储未能被正常消费的消息的特殊队列</font>**

1. 消费失败 消费拒绝
2. 消息过期TTL
3. 队列达到 最大长度
   1. `x-max-length`：限制队列最大 ‌**消息数量**‌（整数） 默认不设置
   2. `x-max-length-bytes`：限制队列最大 ‌**字节容量**‌（单位为字节） // 一般小于1GB



PS: 仅设置 `x-max-length` ‌**不会自动将超限消息转为死信**‌，需额外配置 `x-dead-letter-exchange`









### 1.6 Rabbit高可用

1. 消息在 集群节点 间 复制

2. 负载均衡  HAProxy
3. 持久化





### 1.7 消息幂等性/重复消费

1. 生产者 消息唯一ID
2. 消费者 记录以消费的消息ID





### 1.8 消息堆积？

1. 增加消费者数量
2. 使用工作队列模式
3. 设置合适的TTL和死信队列
4. 监控并扩展集群







### 1.9 消息满了 怎么办？

**<font color=red>溢出行为控制 x-overflow --- 参数指定队列满时的处理策略</font>**

1. drop-head 默认策略  --- 丢弃头部消息
2. reject-publish --- 拒绝新消息
3. reject-publish-dlx --- 加入死信队列







### 1.10 如何实现 延迟消息？

**<font color=red>RabbitMQ 本身不直接支持延迟队列</font>**



1. **<font color=green>TTL + 死信队列  实现</font>**

2. **<font color=#ff49cf>rabbitmq-delayed-message-exchange 插件</font>**













































