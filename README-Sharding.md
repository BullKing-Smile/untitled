# Sharding
[Document](https://shardingsphere.apache.org/document/current/cn/features/)
sharding-jdbc 用来做分库分表


Range 分表 相对合理  --- 存在热点数据的问题， 但是可以忽略
table_1 : id= 1 ~ 1000w
talbe_2 : id = 1000w ~ 2000w
...





TiDB 分布式数据库存储



ShardingSphere-JDBC 客户端分库分表


ShardingSphere-Proxy 服务端分库分表
数据库代理端



## 三种经典分片算法
- Standard
- Complex
- Hint


## 自定义分片算法
实现ComplexKeysShardingAlgorithm接口









