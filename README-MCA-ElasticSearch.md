# Elastic Stack

- ElasticSearch -- 基于Json的分布式**搜索**和**分析**引擎
- Logstash -- 动态数据收集管道， 生态丰富
- Kibana -- 提供数据可视化界面
- Beats -- 轻量级的数据采集器

## 核心知识


### 安装与启动

Docker 的方式
1. 下载镜像
> docker pull elasticsearch:8.18.0<br>

2. Docker Compose 配置
```dockercompose
services:
  elasticsearch-8.18.0:
    image: elasticsearch:8.18.0  # 请使用合适的版本
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - ELASTIC_PASSWORD=password  # 设置Elasticsearch超级用户的密码
      - xpack.security.enabled=true       # 启用安全模块
    ports:
      - "9200:9200"                       # 映射9200端口
    volumes:
      - esdata:/usr/share/elasticsearch/data

volumes:
  esdata:
    driver: local
```
3. 启动
> docker-componse up -d


!!! Attention !!!!
线上环境 单个物理机 禁止启动多个ElasticSearch实例 


- 查看所有卷
docker volume ls

```console
DRIVER    VOLUME NAME
local     43ab339740415c46264a20fd2dabdcf988d01fac798375f069a336bfbe9d9b7f
local     es-mac_esdata
local     redis_cluster_redis-master-data
local     redis_cluster_redis-slave1-data
local     redis_cluster_redis-slave2-data
```
- 查看卷详情（包括存储路径）
docker volume inspect esdata

```console
[
    {
        "CreatedAt": "2025-06-18T13:33:31Z",
        "Driver": "local",
        "Labels": {
            "com.docker.compose.project": "es-mac",
            "com.docker.compose.version": "2.28.1",
            "com.docker.compose.volume": "esdata"
        },
        "Mountpoint": "/var/lib/docker/volumes/es-mac_esdata/_data",
        "Name": "es-mac_esdata",
        "Options": null,
        "Scope": "local"
    }
]
```

- windows 则启动WSL执行以下命令
find -name es-mac_esdata
- Linux 待验证。。。

[//]: # (TODO)






























## 高手进阶



## 运维调优


## 项目实战

