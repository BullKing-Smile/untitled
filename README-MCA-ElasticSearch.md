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

#### 健康状态

Green --- 表示健康抓过你太
Yellow --- 分片Primary均为Active正常， 副本Replica至少有一个不可用, 数据仍然可以保证完整性
Red --- 至少有一个Primary不可用

说明：Primary可读可写， Replica 只读

检查健康值状态
GET _cat/health
GET _cat/health?v

#### 配置TOKEN

1. 生成token
   进入ES container, 执行:

> bin/elasticsearch-service-tokens create elastic/kibana kibana-token<br/>
> 得到的结果： SERVICE_TOKEN elastic/kibana/kibana-token =
> AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpOUGFad0FPb1F5YUFKbHQyM0NlVlZ3

- 生成elasticsearch证书
  openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout elasticsearch.key -out elasticsearch.crt -subj "
  /CN=elasticsearch"
- 生成kibana证书
  openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout kibana.key -out kibana.crt -subj "/CN=kibana"
  openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout kibana.key -out kibana.crt -subj "/CN=kibana" -addext "
  subjectAltName=DNS:localhost"
  openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout kibana.key -out kibana.crt -subj "/CN=kibana" -addext "
  subjectAltName=DNS:localhost,IP:127.0.0.1"
  openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout kibana.key -out kibana.crt -subj "/CN=kibana" -addext "
  subjectAltName=DNS:localhost,IP:127.0.0.1" -config /d/Program Files/Git/etc/ssl/openssl.cnf

- 授予文件权限
  chmod 600 *.key
- 启动elasticsearch 并获取token
  docker-compose up -d elasticsearch
    - 进入容器生成Token
      docker exec -it elasticsearch bash
      bin/elasticsearch-service-tokens create elastic/kibana kibana-token
    - 输出示例：AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpLVk1hLWJZck...
      exit


- 生成的token
  SERVICE_TOKEN elastic/kibana/kibana-token = AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpxSm9kYjBKYlRsMmxBLXJvb3Qza0FR
  SERVICE_TOKEN elastic/kibana/kibana-token = AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpYcVhUUV93Q1F5dU5lNDVWUk1BeVJn


- 将elastic生成的token 配置到 kibana

```yaml
services:
  elasticsearch:
    image: elasticsearch:8.18.0
    container_name: elasticsearch
    environment:
      - node.name=elasticsearch
      - cluster.name=es-docker-cluster
      - discovery.type=single-node
      - xpack.security.enabled=true
      - xpack.security.authc.api_key.enabled=true
      - xpack.security.authc.success_cache.enabled=true
      - ELASTIC_PASSWORD=password  # 仅首次启动使用，后续通过API修改
    volumes:
      - es_data:/usr/share/elasticsearch/data
      - ./certs:/usr/share/elasticsearch/config/certs  # 挂载SSL证书
    ports:
      - "9200:9200"
    networks:
      - elastic

  kibana:
    image: kibana:8.18.0
    container_name: kibana
    depends_on:
      - elasticsearch
    environment:
      - ELASTICSEARCH_HOSTS=http://elasticsearch:9200
      - ELASTICSEARCH_SERVICEACCOUNTTOKEN=AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpYcVhUUV93Q1F5dU5lNDVWUk1BeVJn  # 替换为elastic生成的Token
    #      - SERVER_SSL_ENABLED=true
    #      - SERVER_SSL_CERTIFICATE=/usr/share/kibana/config/certs/kibana.csr
    #      - SERVER_SSL_KEY=/usr/share/kibana/config/certs/kibana.key
    volumes:
      - ./certs:/usr/share/kibana/config/certs  # 挂载SSL证书
    ports:
      - "5601:5601"
    networks:
      - elastic

volumes:
  es_data:

networks:
  elastic:
    driver: bridge
```

- 启动kibana(单独启动)

> docker -compose up -d kibana

#### 节点Node

每个节点都是 一个Elasticsearch实例

#### 节点角色

**master**: 候选节点， 主节点宕机后 可以选择为主节点的 节点
**data**: 数据节点
data_content: 数据内容节点
data_hot: 热节点
data_warm: 索引不再定期更新， 但仍可查询
data_code: 冷节点 只读索引
longest: 预处理节点， 作用类似于Logstash中的Filter 用到比较少
ml: 机器学习节点
remote_cluster_client: 候选客户端节点
transform: 转换节点
voting_only: 仅投票节点

#### 分片

- 一个索引包含一个或者多个分片， 副本可以在索引创建之后修改数量，但是主分片的数量一旦确定不可修改， 只能创建索引。
- 每个分片 都是一个Lucene实例，是完整的创建索引和处理请求的能力
- ES会自动在nodes上 做分片均衡
- 一个doc不可能同时存在于多一个主分片中， 一个主分片多个副本时 可以同时存在于多个副本中
- 每个主分片和其副本 不能同时存在于同一个节点上。

#### 集群配置

1. docker-compose.yml 配置

```yaml

```

2. 证书配置
   2.1 生成CA私钥
   openssl genrsa -out certs/ca/ca.key 4096

2.2 生成CA根证书（有效期10年）
MSYS_NO_PATHCONV=1 openssl req -new -x509 -days 3650 -key certs/ca/ca.key -out certs/ca/ca.crt -subj "/CN=Elasticsearch
CA"
生成节点证书 es00
MSYS_NO_PATHCONV=1 openssl req -newkey rsa:4096 -nodes -keyout certs/nodes/es00.key -out certs/nodes/es00.csr -subj "
/CN=es00" -addext "subjectAltName=DNS:es00,DNS:localhost,IP:127.0.0.1"
生成节点证书 es01
MSYS_NO_PATHCONV=1 openssl req -newkey rsa:4096 -nodes -keyout certs/nodes/es01.key -out certs/nodes/es01.csr -subj "
/CN=es01" -addext "subjectAltName=DNS:es01,DNS:localhost,IP:127.0.0.1"
生成节点证书 es02
MSYS_NO_PATHCONV=1 openssl req -newkey rsa:4096 -nodes -keyout certs/nodes/es02.key -out certs/nodes/es02.csr -subj "
/CN=es02" -addext "subjectAltName=DNS:es02,DNS:localhost,IP:127.0.0.1"

2.3 签发证书节点

printf "subjectAltName=DNS:es00,DNS:localhost,IP:127.0.0.1" > extfile.cnf
MSYS_NO_PATHCONV=1 openssl x509 -req -days 730 -in certs/nodes/es00.csr -CA certs/ca/ca.crt -CAkey certs/ca/ca.key
-CAcreateserial -out certs/nodes/es00.crt -extfile extfile.cnf
rm extfile.cnf

printf "subjectAltName=DNS:es00,DNS:localhost,IP:127.0.0.1" > extfile.cnf
MSYS_NO_PATHCONV=1 openssl x509 -req -days 730 -in certs/nodes/es01.csr -CA certs/ca/ca.crt -CAkey certs/ca/ca.key
-CAcreateserial -out certs/nodes/es01.crt -extfile extfile.cnf
rm extfile.cnf

printf "subjectAltName=DNS:es00,DNS:localhost,IP:127.0.0.1" > extfile.cnf
MSYS_NO_PATHCONV=1 openssl x509 -req -days 730 -in certs/nodes/es02.csr -CA certs/ca/ca.crt -CAkey certs/ca/ca.key
-CAcreateserial -out certs/nodes/es02.crt -extfile extfile.cnf
rm extfile.cnf

2.4 验证证书 书链

MSYS_NO_PATHCONV=1 openssl verify -CAfile certs/ca/ca.crt certs/nodes/es00.crt

2.5 授予文件操作权限
chmod 600 *.key
chmod 644 *.c*

3. 生成Token
   docker exec -it container_name/id bash
   bin/elasticsearch-service-tokens create elastic/kibana kibana-token

eg:
SERVICE_TOKEN elastic/kibana/kibana-token = AAEAAWVsYXN0aWMva2liYW5hL2tpYmFuYS10b2tlbjpXOW92N2pFQ1J3Q2tZTkZLYV90d0VR

4. 配置KibanaToken
   生成私钥   
   openssl genrsa -out certs/kibana/kibana.key 4096
   创建CSR
   MSYS_NO_PATHCONV=1 openssl req -new -key certs/kibana.key -out certs/kibana.csr -subj "/CN=kibana" -addext "
   subjectAltName=DNS:es00,DNS:localhost,IP:127.0.0.1"

签发证书
printf "subjectAltName=DNS:kibana,DNS:localhost,IP:127.0.0.1" > extfile.cnf
openssl x509 -req -days 365 -in certs/kibana.csr -CA certs/ca.crt -CAkey certs/ca.key -CAcreateserial -out
certs/kibana.crt -extfile extfile.cnf
rm extfile.cnf

6. 生成kibana 证书
   MSYS_NO_PATHCONV=1 openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout certs/kibana.key -out certs/kibana.crt
   -subj "/CN=kibana" -addext "subjectAltName=DNS:localhost,IP:127.0.0.1"

5. 启动Kibana
   docker-compose up -d kibana

#### REST full

创建索引 PUT /index?pretty
查询索引 GET _cat/indices or GET _cat/indices?v
查询索引的信息 GET index/_search --- 查看索引的详细信息
DELETE /index?pretty --- 删除索引
DELETE index/_doc/1 --- 删除doc
GET /index/_doc/1
PUT /index/_doc/1
{"price":99} --- 全量修改

POST /index/_doc/1/_update --- 局部更新
{
"doc": {
"price": 99
}
}

### Mapping 介绍

#### 概念和用法

GET index/_mapping --- 查看 所有mapping
GET index/_mapping/field/[name] --- 产看指定title的mapping 信息

##### Field Data Type, 字段数据类型

- 数字类型
    - long 64位 有符号整形  [-2^63 到 2^63 - 1]
    - integer 32位。。。
    - short 16位 。。。
    - byte
    - double
    - float
    - half_float
    - scaled_float
    - unsigned_long: 无符号64位整数

- 基本数据类型
    - binary Base64字符串 二进制值
    - **boolean 布尔类型**
    - **alias** 字段别名
- keywords
    - **keyword** 没必要进行分词的字段 用这个类型； eg: id, age, 姓名
    - constant_keyword
    - wildcard
- date
    - **date**： JSON 中没有日期数据类型
        - 包含格式化日期的字符串
        - 时间戳
- **对象数据类型**
    - object: 非基本数据类型之外， 默认的json对象为object类型
    - flattened
    - nested --- 嵌套类型
    - join --- 父子级 类型
- 空间数据类型
    - geo_point: 纬度经度
    - geo_shape 复杂的形状
    - point
    - shape
- 文档排名类型
    - **<font color=orange>text 文本类型， 最重要的类型， 全文检索所依赖的类型</font>**
    - annotated-text --- 包含特殊文本标记， 用于标识命名实体
    - **completion** 用于自动补全， 即 搜索推荐
    - search_as_you_type:   类似文本的字段
    - token_count: 文本中的标记计数

#### ES的数据类型

#### 常见的映射参数

#### 两种映射类型

- 自动映射器 Dynamic field mapping

- 显示映射器 Explicit field mapping (生产环境建议使用显示映射)

``` http request json
PUT test_student
{
    "mappings": {
        "properties": {
          "title": {
            "type":"text"
           },
          "name": {
              "type": "text",
              "fields": {
                "name2": {
                  "type": "keyword",
                  "ignore_above": 256
                }
              }
            },
            "age":{
                "type": "byte"
            }
        }
    }
}
```

#### 映射模板
先 按照自动映射的方式 插入一条doc， 查看mappings,在此基础上 修改后 再 显示添加mappings



#### 映射参数类型

- index --- 当前字段是否 创建倒排索引， 默认true， 如果false 则该字段不能通过索引被搜索到。 es性能优化上的一个小点。
- analyzer --- 分词器类型 （针对源数据）
  - character filter
  - tokenizer
  - token filters
- boost ---  评分权重  默认1
- coerce --- 是否允许强制类型转换
- copy_to --- 是否允许将多个字段的值 复制到 组字段中， 然后将其作为 单个字段进行查询
- doc_vlaues --- 是否可以排序/聚合， 默认true, 为了提升 排序和聚合效率
- dynamic --- 是否可以动态添加新字段， 默认true
- strict
- eager_global_oridinals
- enable --- 是否可以创建倒排索引

- fielddata -- 
- fields
- format --- 格式化
- ignore_above --- 超过长度将被忽略 对keyword类型数据， text 类型则不受此限制
- ignore_malformed 忽略类型错误
- meta --- 元数据
- norms --- 是否进行评分， filter和聚合字段上 应该禁止， es性能优化上的一个小点。
- search_analyzer --- 默认跟analyzer保持一致  （针对搜索值）
- similarity
- store --- 设置字段是否进查询
- term_vector --- 运维参数



### <font color=red>Text 和 Keyword 类型</font>

1. Text

- 适用于全文检索 如match查询
-  文本字段会被分词
- 默认  创建倒排索引
- **自动映射器 会为Text类型创建keyword字段**

2. Keyword

   Keyword类型 适用于 不分词的字段， 如姓名，id, 数字等， 如果数字类型 不用于范围查找， 用keyword的性能要高于数值类型。

   - Keyword 不会对文本分词，会保留字段的原有属性，包括大小写等。

   - Keyword 仅仅是字段类型，而不会对搜索词产生任何影响

   - Keyword 一般用于需要精确查找的字段，或者聚合排序字段

   - Keyword 通常和 Term 搜索一起用（会在 DSL 中提到）

   - Keyword 字段的 ignore_above 参数代表其截断长度，默认 256，如果超出长度，字段值会被忽略，而不是截断。



#### 映射模板

之前讲过的映射类型或者字段参数，都是为确定的某个字段而声明的，如果希望对符合某类要求的特定字段制定映射，就需要用到映射模板：Dynamic templates。

映射模板有时候也被称作：自动映射模板、动态模板等



##### 基本语法

```codes
"dynamic_templates": [ { "my_template_name": { ... match conditions ... "mapping": { ... } } }, ... ]
```



### 搜索和查询

#### Query DSL(Domain Specific Language)

_source 元数据

GET index/_search

{"_source": "false", --- 禁用元数据， 查询结果则不返回元数据

"query:{"match_all":{}}"}



#### 常用过滤规则

- "_source": "false",
- "_source": "obj.*",
- "_source": [ "obj1.*", "obj2.*" ],
- "_source": {
  "includes": [ "obj1.*", "obj2.*" ], --- 需要返回的字段
  "excludes": [ "*.description" ] --- 不需要包含在 返回结果中
  }

### Query String

- #### 查询所有：

  GET /product/_search

- #### 带参数：

  GET /product/_search?q=name:xiaomi

- #### 分页：

  GET /product/_search?from=0&size=2&sort=price:asc

- #### 精准匹配 exact value

  GET /product/_search?q=date:2021-06-01

- #### _all搜索 <font color=red>相当于在所有有索引的字段中检索</font>

  GET /product/_search?q=2021-06-01

  ```
  DELETE product
  # 验证_all搜索
  PUT product
  {
    "mappings": {
      "properties": {
        "desc": {
          "type": "text", 
          "index": false
        }
      }
    }
  }
  # 先初始化数据
  POST /product/_update/5
  {
    "doc": {
      "desc": "erji zhong de kendeji 2021-06-01"
    }
  }
  ```

### 全文检索-Fulltext query

```
GET index/_search
{
  "query": {
    ***
  }
}
```

- #### match：匹配包含某个term的子句

- #### match_all：匹配所有结果的子句

- #### multi_match：多字段条件

- #### match_phrase：短语查询， 分词包含，顺序匹配

```json
{
    "query": {
        "multi_match": {
            "query": "phone xiaomi",
            "fields":["name", "desc"] # 这两个字段中包含上面的内容即 符合被筛选， or 的关系
        }
    }
}
```

- #### 

```json
GET _analyze
{
  "analyzer": "standard",
  "text": "Ali hello"
}
```



### 6 精准查询-Term query

- #### term：匹配和搜索词项完全相等的结果 <font color=orange>不对搜索词进行分词</font>

  - term和match_phrase区别:

    match_phrase 会将检索关键词分词, match_phrase的分词结果必须在被检索字段的分词中都包含，而且顺序必须相同，而且默认必须都是连续的

    term搜索不会将搜索词分词

  - term和keyword区别

    term是对于搜索词不分词,

    keyword是字段类型,是对于source data中的字段值不分词

- #### terms：匹配和搜索词项列表中任意项匹配的结果

- #### range：范围查找

### 7 过滤器-Filter

```console
GET _search
{
  "query": {
    "constant_score": {
      "filter": {
        "term": {
          "status": "active"
        }
      }
    }
  }
}
```

- filter：query和filter的主要区别在： filter是结果导向的而query是过程导向。query倾向于“当前文档和查询的语句的相关度”而filter倾向于“当前文档和查询的条件是不是相符”。即在查询过程中，query是要对查询的每个结果计算相关性得分的，而filter不会。另外filter有相应的缓存机制，可以提高查询效率。

####  7.1 term和match区别

首先两者都是检索的方式，都是针对“搜索词”而言的，与源数据无关

- term搜索不会将搜索词分词
- match对搜索词分词

#### 7.2 term与keyword的区别

term 是搜索关键字

keyword是数据类型， keyword 的元数据是正常进行分词的， keyword作为附属字段，不进行分词存储， 因此可以做完全匹配查询

如图： **name字段正常按照分词 存储，附属字段keyword1 则不进行分词**

```json
{
	"name": {
      "type": "text", # 表示name的数据类型是text
      "fields": {
        "keyword1": { # 附属字段 keyword1
          "type": "keyword", # 附属字段的类型是 keyword
          "ignore_above": 256 # 超过 256的内容将不进行分词匹配
        }
      }
    }
}
```



### 8 组合查询-Bool query

**bool**：可以组合多个查询条件，bool查询也是采用more_matches_is_better的机制，因此满足must和should子句的文档将会合并起来计算分值

- **must**：必须满足子句（查询）必须出现在匹配的文档中，并将有助于得分。
- **filter**：过滤器 不计算相关度分数，cache☆子句（查询）必须出现在匹配的文档中。但是不像 must查询的分数将被忽略。Filter子句在[filter上下文](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-filter-context.html)中执行，这意味着计分被忽略，并且子句被考虑用于缓存。
- **should**：可能满足 or子句（查询）应出现在匹配的文档中。
- **must_not**：必须不满足 不计算相关度分数 not子句（查询）不得出现在匹配的文档中。子句在[过滤器上下文](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-filter-context.html)中执行，这意味着计分被忽略，并且子句被视为用于缓存。由于忽略计分，0因此将返回所有文档的分数。

**minimum_should_match**：参数指定should返回的文档必须匹配的子句的数量或百分比。如果bool查询包含至少一个 should 子句，而没有 must 或 filter 子句，则默认值为 1。否则，默认值为0



### 9 Filter过滤器

```json
GET test_student/_search
{
  "query": {
    "constant_score": {
      "filter": {
        "term": {
          "age": 18
        }
      },
      "boost": 1.2
    }
  }
}

# filter
GET test_student/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "term": {
            "name": "ali"
          }
        }
      ]
    }
  }
}
# must_not 都不满足
GET test_student/_search
{
  "query": {
    "bool": {
      "must_not": [
        {
          "match": {
            "name": "Ali"
          }
        },
        {
          "range": {
            "age": {
              "gte": 10,
              "lte": 30
            }
          }
        }
      ]
    }
  }
}

```



### 10 bool Qery 组合查询

```jso
# should 满足条件中的部分即可， 类似 or 条件
GET test_student/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "match": {
            "name": "Wu"
          }
        },
        {
          "range": {
            "age": {
              "gte": 10,
              "lte": 20
            }
          }
        }
      ]
    }
  }
}

# 组合条件查询， must+filter组合条件查询，filter先过滤掉一些数据发，避免全量评分， 因此性能更高， 并且filter具有缓存机制，相同的filter条件查询性能更高
GET test_student/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "name": "Ali"
          }
        }
      ],
      "filter": [
        {
          "range": {
            "age": {
              "gte": 19
            }
          }
        }
      ]
    }
  }
}
```

#### 9.1 should 查询

- 当should与其他类型组合查询时， should+mush, should+filter， 则should中 满足0或0以上条件即可 （minimum_should_match=0）
- 当should条件单独使用时，则必须满足其中一个条件
- minimum_should_match=1, 即使是 组合查询should中也必须满足一个条件，数字可以任意修改



### 分词器

- Nomination 规范化  不通用/无用字符 规范化为 通用词汇的过程
- character filter
- tokenizer
- token filter

#### 常见分词器

- standard analyzer: 默认分词器， 中文支持不理想， 会逐字拆分
- pattern tokenizer: 以正则匹配 分隔符， 把文本拆分成若干词项
- simple pattern tokenizer: 以正则匹配词项, 速度比pattern tokenizer快
- whitespace analyzer：以空白符分隔 Tim_cookie

#### 自定义分词器： custom analyzer

- char_filter：内置或自定义字符过滤器 。

- token filter：内置或自定义token filter 。

- tokenizer：内置或自定义分词器。



#### 中文分词器

https://github.com/infinilabs/analysis-ik/archive/refs/heads/8.17.zip

安装的两种方式

- 手动下载 [Analysis-ik](https://release.infinilabs.com/) 并解压放置到 es_root/plugins/ik下

- 执行命令 

  - For Elasticsearch:

  ```
  bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/8.18.0
  ```

  - For OpenSearch:

  ```
  bin/opensearch-plugin install https://get.infini.cloud/opensearch/analysis-ik/2.12.0
  ```

使用示例

```json
GET /_analyze
{
  "analyzer":"ik_max_word",
  "text": ["中华人民共和国国歌"]
}
```

##### ik文件描述

- 主词库：main.dic
  - 英文停用词：stopword.dic，不会建立在倒排索引中
  - 特殊词库：
    - quantifier.dic：特殊词库：计量单位等
    - suffix.dic：特殊词库：行政单位
    - surname.dic：特殊词库：百家姓
    - preposition：特殊词库：语气词
  - 自定义词库：网络词汇、流行词、自造词等

#### ik提供的两种analyzer:

1. ik_max_word会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合，适合 Term Query；
2. ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”，适合 Phrase 查询。



#### 热更新

- 远程词库文件， 修改IKAnalyzer.cfg.xml配置文件 配置remote_ext_** , 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<entry key="ext_dict">custom/mydict.dic;custom/single_word_low_freq.dic</entry>
	<entry key="ext_stopwords">custom/ext_stopword.dic</entry>
	<entry key="remote_ext_dict">location</entry>
	<entry key="remote_ext_stopwords">http://xxx.com/xxx.dic</entry>
</properties>
```

接口Http Header 添加两个参数 **Last-Modified** 和 **ETag** 都是**字符串类**型

1. 优点：上手简单
2. 缺点：
   1. 词库的管理不方便，要操作直接操作磁盘文件，检索页很麻烦
   2. 文件的读写没有专门的优化性能不好
   3. 多一层接口调用和网络传输

- ik访问数据库 （**<font color=orange>生产环境更推荐的做法</font>**）

1. MySQL驱动版本兼容性
   1. https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-versions.html
   2. https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-versions.html
2. 驱动下载地址
   1. https://mvnrepository.com/artifact/mysql/mysql-connector-java



### 聚合查询 

[官方文档](https://www.elastic.co/docs/explore-analyze/query-filter/aggregations)

- 分桶聚合 Bucket agregations
- 指标聚合 Metrics agregations  avg/max/min/sum/ cardinality/count/stats/
- 管道聚合 Pipeline agregations --- 对聚合结果的二次聚合



#### 语法

```json
GET product/_search
{
 "aggs": {
  "<aggs_name>": {
   "<agg_type>": {
​    "field": "<field_name>"
   }
  }
 }
}
```

分桶聚合示例

```json
GET product/_search
{
    "size": 0, # 表示不返回source数据 也就是hits结果为空
    "aggs": { # 表示这是一个聚合查询
      "aggs_type": { # 自定义 名称， 作为返回结果的名称， 数据解析用
        "terms": { # 完全匹配的方式 分组
            "field": "type.keyword", # 对type字段进行分组
            "size": 100 # 分组结果 显示的数量， 默认返回10个， 实际开发中需要注意
    		"order": { # 结果排序
              "_count": "asc" # 对返回数量做升序 排列， 也可以对key 做排序
            }
        }
      }
    }
}
```



指标聚合示例

```json
GET product/_search
{
    "size": 0,
    "aggs": {
      "price_max": {
        "max": { # 表示 最大值 指标聚合
            "field": "price"
        }
      },
      "price_min": {
        "min": { # 表示 最小值 指标聚合
          "field": "price"
        }
      },
      "price_avg": {
        "avg": { # 表示 平均值 指标聚合
          "field": "price"
        }
      },
      "price_count": { 
        "cardinality": { # 对 字段去重统计， 如果是text字段， 则应该使用name.keyword
          "field": "price"
        }
      },
      "all_avg_price": { # 计算 全部数据的平均价格， 
      "global": {}, # 加这个参数， 即使 是再上一个 聚合查询下面的，页同样使用全部数据， 这一点需要注意， 只是把他的数据 与聚合结果 一起返回
      "aggs": {
        "all_avg": {
          "avg": {
            "field": "price"
          }
        }
      }
    }
    }
}
```





管道聚合示例

```json
GET product/_search
{
  "size": 0, 
  "aggs": {
    "type_bucket": { # 第一个聚合 -- 指标聚合结果
      "terms": {
        "field": "type.keyword"
      },
      "aggs": {
        "price_bucket": {
          "avg": {
            "field": "price"
          }
        }
      }
    },
    "min_bucket_name":{ # 根据上一个 聚合结果 开始的 管道聚合, 此处的名字 作为名称
      "min_bucket": { # 查询最小桶
        "buckets_path": "type_bucket>price_bucket" # 使用上一个聚合type_bucket
      }
    }
  }
}
```



嵌套聚合示例

```json
GET product/_search
{
    "size": 0,
    "aggs": {
      "agg_type": {
        "terms": {
            "field": "type.keyword"
        },
        "aggs": { # 二级聚合， 再type类型分组聚合的基础上 最lv进行 再次聚合查询
          "agg_lv": {
            "terms": {
                "field": "lv.keyword"
            }
          },
          "price_list": { # 同上
            "stats": { # stats 会列出 指定字段的所有值(count/max/min/avg/sum)
                "field": "price"
            }
          }
        }
      }
    }
}
```



##### 基于聚合的查询 - （post_filter） 实际上是 对聚合结果的简单过滤

```json
GET product/_search
{
  "aggs": {
    "tags_bucket": {
      "terms": {
        "field": "tags.keyword"
      }
    }
  },
  "post_filter": {
    "term": {
      "tags.keyword": "性价比"
    }
  }
}
```



####  聚合排序

```json
GET product/_search?size=0
{
    "aggs": {
      "agg_type": {
        "terms": {
            "field": "type.keyword",
            "order": { # 对聚合字段进行排序
              "_key": "asc",
              "_count": "asc"
            }
        }
      }
    }
}
#多级排序
GET product/_search?size=0
{
  "aggs": {
    "first_sort": {
      "terms": {
        "field": "type.keyword",
        "order": {
          "_count": "desc"
        }
      },
      "aggs": {
        "second_sort": {
          "terms": {
            "field": "lv.keyword",
            "order": {
              "_count": "asc"
            }
          }
        }
      }
    }
  }
}
```



#### 经典统计方法 

##### histogram 直方图 或者 柱状图

```json
GET product/_search?size=0
{
  "aggs": {
    "price_histogram": {
      "histogram": {
        "field": "price", # 聚合的字段
        "interval": 1000, # 分组的间隔
        "keyed": true, # 是否 将数值作为key, 默认false
        "min_doc_count": 0, # 最小值， 小于这个值的分组信息将不被 返回
        "missing": 1999 # field字段 数据缺省时的 替代值
      }
    }
  }
}
#date-histogram
#ms s m h d
GET product/_search?size=0
{
  "aggs": {
    "my_date_histogram": {
      "date_histogram": { # 对日期参数进行聚合分组统计
        "field": "createtime", # 分组的字段
        "calendar_interval": "month", # 统计的间隔
        "min_doc_count": 0, # 显示的最小值
        "format": "yyyy-MM",  # 显示的时间格式
        "extended_bounds": { # 拓展的边界， 如果min_cod_count=0或不设置， 则边界范围内都返回，没有数据据则统计为0
          "min": "2020-01",
          "max": "2020-12"
        },
        "order": {
          "_count": "desc"
        }
      }
    }
  }
}
GET product/_search?size=0
{
  "aggs": {
    "my_auto_histogram": {
      "auto_date_histogram": {
        "field": "createtime",
        "format": "yyyy-MM-dd",
        "buckets": 180 # 均分为多少个桶
      }
    }
  }
}
#cumulative_sum 累加聚合
GET product/_search?size=0
{
  "aggs": {
    "my_date_histogram": {
      "date_histogram": {
        "field": "createtime",
        "calendar_interval": "month",
        "min_doc_count": 0,
        "format": "yyyy-MM", 
        "extended_bounds": {
          "min": "2020-01",
          "max": "2020-12"
        }
      },
      "aggs": {
        "sum_agg": { # 统计各个时间段 的销售总和
          "sum": {
            "field": "price"
          }
        },
        "my_cumulative_sum":{
          "cumulative_sum": { # 累加统计
            "buckets_path": "sum_agg" # 指向某个路径
          }
        }
      }
    }
  }
}

## percentile 百分位统计 或者 饼状图
## https://www.elastic.co/guide/en/elasticsearch/reference/8.18/search-aggregations-metrics-percentile-aggregation.html

GET product/_search?size=0
{
  "aggs": {
    "price_percentiles": {
      "percentiles": {
        "field": "price",
        "percents": [
          1, # 1% 以内的最大值
          5,
          25,
          50,
          75,
          95,
          99 # 99% 以内的最大值， 适用场景：99%的请求都在1s以内，即表示服务运行良好
        ]
      }
    }
  }
}

#percentile_ranks
#TDigest 计算精度不高， 但是效率非常高
GET product/_search?size=0
{
  "aggs": {
    "price_percentiles": {
      "percentile_ranks": {
        "field": "price",
        "values": [
          1000, # 1000 以内的占比 结果eg: 25 表示 25%的比例
          2000,
          3000,
          4000,
          5000,
          6000 # 6000 以内的占比
        ]
      }
    }
  }
}
```





### Scripting 脚本查询

#### 概念

Scripting是Elasticsearch支持的一种专门用于**复杂场景**下支持自定义编程的强大的脚本功能，ES支持多种脚本语言，如painless，其语法类似于Java,也有注释、关键字、类型、变量、函数等，其就要相对于其他脚本高出几倍的性能，并且安全可靠，可以用于内联和存储脚本。

#### 特点

1. **语法简单**，学习成本低
2. 灵活度高，可编程能力强
3. **性能相较于其他脚本语言很高**
4. 安全性好
5. 独立语言，虽然易学但仍需单独学习
6. **相较于DSL性能低**
7. 不适用于复杂的业务场景

ES脚本 使用示例

```json
##语法：ctx._source.<field-name>
POST product/_update/2
{
  "script": {
    "source": "ctx._source.price-=1" # ctx 表示当前上下文，_source表示修改文档资源， price 时doc的属性
  }
}

#简写
POST product/_update/2
{
  "script": "ctx._source.price-=1" # ES 引擎内执行价格-1， 防止了并发问题
}


# Scripting的CRUD
POST _reindex # 复制index 复制索引
{
  "source": {
    "index": "product"
  },
  "dest": {
    "index": "product2"
  }
}


# 举个例子：小米10出了新款 新增了tag 叫做“无线充电”
POST product/_update/6
{
  "script": {
    "lang": "painless",
    "source": "ctx._source.tags.add('无线充电')"
  }
}
#delete 删除数据
POST product/_update/10
{
  "script": {
    "lang": "painless",
    "source": "ctx.op='delete'"
  }
}

#upsert update + insert  修改和新增脚本 有数据则执行修改脚本，否则执行新增脚本
DELETE product/_doc/15
GET product/_doc/15
POST product/_update/15
{
  "script": { # id=15的数据存在，则执行更新脚本
    "lang": "painless",
    "source": "ctx._source.price += 100"
  },
  "upsert": { # i大5的数据不存在 则执行insert脚本
    "name" : "小米手机10",
    "desc" : "充电贼快掉电更快，超级无敌望远镜，高刷电竞屏",
    "price" : 1999
  }
}


#GET查询 painless expression
GET product/_search
{
  "script_fields": {
    "my_price": {
      "script": {
        "lang": "expression",
        "source": "doc['price'].value*0.9" # value 有没有都可以
      }
    }
  }
}
GET product/_search
{
  "script_fields": {
    "my_price": {
      "script": {
        "lang": "painless",
        "source": "doc['price'].value*0.9" # value 必须有
      }
    }
  }
}


#参数化
POST product/_update/6
{
  "script": {
    "lang": "painless",
    "source": "ctx._source.tags.add(params.tag_name)",
    "params": {
      "tag_name":"无线秒充" # es 编译时，会先将字符串放入缓冲区， 提高写入性能
    }
  }
}

GET product/_search
{
  "script_fields": {
    "my_price": {
      "script": {
        "lang": "painless",
        "source": "doc['price'].value* params.num",
        "params": {
          "num": 9 
        }
      }
    }
  }
}

#案例： 打8折
GET product2/_search
{
  "script_fields": {
    "price": {
      "script": {
        "lang": "painless",
        "source": "doc['price'].value"
      }
    },
    "discount_price": {
      "script": {
        "lang": "painless",
        "source": "[doc['price'].value* params.discount_8,doc['price'].value* params.discount_7,doc['price'].value* params.discount_6,doc['price'].value* params.discount_5]",
        "params": {
          "discount_8": 0.8,
          "discount_7": 0.7,
          "discount_6": 0.6,
          "discount_5": 0.5
        }
      }
    }
  }
}
```



#### Scripting函数式编程

```json
#Scripting的函数式编程
POST product/_update/1
{
  "script": {
    "lang": "painless",
    "source": "ctx._source.tags.add(params.tag_name)",
    "params": {
      "tag_name":"无线秒充"
    }
  }
}

POST product/_update/1
{
  "script": {
    "lang": "painless",
    "source": """
      ctx._source.tags.add(params.tag_name);
      ctx._source.price-=100;
    """,
    "params": {
      "tag_name":"无线秒充1"
    }
  }
}


#正则like %小米% /[\s\S]*小米[\s\S]*/
POST product/_update/3
{
  "script": {
    "lang": "painless",
    "source": """
      if(ctx._source.name ==~ /[\s\S]*小米[\s\S]*/) { # ==~ 符号表示 匹配正则表达式； 斜杠"/"通常用作正则表达式的分隔符
        ctx._source.name+="***|"
      }else{
        ctx.op="noop" # noop 表示不做任何操作
      }
    """
  }
}



#统计所有价格小于1000的商品的tag的数量 不考虑重复的情况
GET product/_mapping
GET product/_search?size=0
{
  "query": {
    "constant_score": {
      "filter": {
        "range": {
          "price": {
            "lte": 1000
          }
        }
      }
    }
  },
  "aggs": {
    "tag_agg": {
      "sum": {
        "script": { # 等同于 "source": "doc['tags.keyword'].length" 这样的写法效果相同
          "lang": "painless",
          "source": """
            int total = 0;
            for(int i = 0; i <doc['tags.keyword'].length; i++){
              total++;
            }
            return total;
          """
        }
      }
    }
  }
}



POST test_index_1/_doc/3
{
  "ajbh": "12345",
  "ajmc": "立案案件",
  "lasj": "2020/05/21 13:25:23",
  "jsbax_sjjh2_xz_ryjbxx_cleaning": [
    {
      "XM": "张三3",
      "NL": "30",
      "SF": "男"
    },
    {
      "XM": "李四3",
      "NL": "31",
      "SF": "男"
    },
    {
      "XM": "王五3",
      "NL": "30",
      "SF": "女"
    },
    {
      "XM": "赵六3",
      "NL": "23",
      "SF": "男"
    }
  ]
}
#统计男性嫌疑人的数量
GET test_index_1/_search?size=0
{
    "aggs": {
      "sum_male": {
        "sum": {
            "script": {
                "lang": "painless",
                "source": """
                    int total = 0;
                    for (int i = 0; i < params['_source']['jsbax_sjjh2_xz_ryjbxx_cleaning'].length;i++) {
                        if (params['_source']['jsbax_sjjh2_xz_ryjbxx_cleaning'][i]['SF']=='男') {
                            total +=1;
                        }
                    }
                    return total;
                    """
                }
        }
      }
    }
}

```



### 索引的批量操作

语法

```json
GET /_mget
```



```json
# 批量查询 等同于 Select * from table where id in (...);
GET product/_mget
{
  "docs": [
    {
      "_id": 2
    },
    {
      "_id": 3
    }
  ]
}
GET product/_mget
{
  "docs": [
    {
      "_id": 2,
      "_source":["name","price"] # 选择哪些字段返回
    },
    {
      "_id": 3,
      "_source":{
        "include": ["name","lv","price","type"], # 选择哪些字段
        "exclude": ["createtime"] # 排除哪些字段
      }
    }
  ]
}
GET product/_mget {
{
  "ids": [
    2,
    3,
    4
  ]
}
```



#### 文档的操作类型

- create 不存在则创建， 存在则报错
- delete 删除文档
- update 全量替换，或者部分更新
- index 索引（动词）

```json
# Create
PUT test_index_2/_doc/2
{
  "name": "wangwu",
  "age": 18
}
 
PUT test_index_2/_create/3
{
  "name": "wangwu",
  "age": 18
}

# Delete 实际上是懒删除
DELETE test_index_2/_doc/2

# Update
POST /test_index_2/_update/2 # 局部更新/局部替换 方式
{
  "doc": {
    "birth": "2020-18-28"
  }
}

POST /test_index_2/_doc/2 # 全量更新/全量替换
{
  "doc": {
    "birth": "2020-18-28"
  }
}
PUT test_index_2/_doc/2 # 全量更新/全量替换
{
  "birth": "2020-18-18"
}
PUT test_index_2/_doc/2?op_type=index # 全量更新/全量替换 同上
{
  "birth": "2020-18-19"
}


# Index 可以是创建 也可以是 全量替换
#创建     PUT test_index/_create/3
#全量替换  PUT test_index/_doc/3

PUT test_index_2/_doc/2?op_type=index # 有数据则 全量替换， 没有则创建
{
  "birth": "2020-18-19"
}


# 任何操作都可以添加参数 ?filter_path=item.*.error
```



#### 索引的批量操作: _bulk

```json
# 语法
# POST /_bulk

# 接下来三行是固定的顺序， 并且第二行json于第三行json的内容不能换行。
# POST /<index>/_bulk
#{"action": {"metadata"}}
#{"data"}


#Create
POST /test_index_2/_bulk
{"create": {"_id": 12}}{"name":"Lucy", "age": 11}

#Delete  批量删除，多条data json即表示批量操作
POST /test_index_2/_bulk
{"delete": {"_id": 12}}
{"delete": {"_id": 12}}

#Update 局部更新
POST /test_index_2/_bulk
{"update": {"_id": 12}}{"doc":{"desc":"Lucy is a girl"}}

```





###  模糊查询和智能搜索

#### 前缀搜索 Prefix  性能比较差，一般不推荐

```json
PUT my_index
{
  "mappings": {
      "properties": {
        "temperature": {
          "type": "long"
        },
        "title": {
          "analyzer":"ik_max_word", # 指定中文分词器
          "type": "text",
          "index_prefixes": { # 会当都为 该字段的 前2个字符和前3个字符单独创建分词索引， 以时间换空间， 一般不这么干
              "min_chars":2,
              "max_chars":3
          },
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        }
      }
    }
}

# 前缀搜索
GET my_index/_search
{
  "query": {
    "prefix": {
      "title": {
        "value": "城管"
      }
    }
  }
}
```



#### 通配符：wildcard

概念：通配符运算符是匹配一个或多个字符的占位符。例如，*通配符运算符匹配零个或多个字符。您可以将通配符运算符与其他字符结合使用以创建通配符模式。

##### 注意：

- 通配符匹配的也是term，而不是field

语法：

```json
GET <index>/_search 
{ 
    "query": { 
        "wildcard": { 
            "<field>": { 
                "value": "<word_with_wildcard>" 
            }
        }
    }
}
```



```json
GET my_index/_search
{
  "query": {
    "wildcard": {
      "title.keyword": {
        "value": "*城管*"
      }
    }
  }
}
```



#### 正则： regexp

##### 概念：regexp查询的性能可以根据提供的正则表达式而有所不同。为了提高性能，应避免使用通配符模式，如.*或 .*?+未经前缀或后缀

##### 语法：

```json
GET <index>/_search
{
  "query": {
    "regexp": {
      "<field>": {
        "value": "<regex>",
        "flags": "ALL",
      }
    }
  }
}


GET my_index/_search
{
  "query": {
    "regexp": {
      "title.keyword": "[\\s\\S]*城管[\\s\\S]*"
    }
  }
}
```



#### 模糊查询 Fuzzy - 智能纠错

混淆字符 (**b**ox → fox) 缺少字符 (**b**lack → lack)

多出字符 (sic → sic**k**) 颠倒次序 (a**c**t → **c**at)

##### 语法

```json
GET <index>/_search
{
  "query": {
    "fuzzy": { # 对搜索输入的内容 不分词， match 则对输入的内容分词处理
      "<field>": {
        "value": "<keyword>"
      }
    }
  }
}
```

##### 参数：

- value：（必须，关键词）

- fuzziness：编辑距离，（0，1，2）并非越大越好，召回率高但结果不准确

  1. 两段文本之间的Damerau-Levenshtein距离是使一个字符串与另一个字符串匹配所需的插入、删除、替换和调换的数量
  2. 距离公式：Levenshtein是lucene的，es改进版：Damerau-Levenshtein，

  axe=>aex Levenshtein=2 Damerau-Levenshtein=1

- transpositions：（可选，布尔值）指示编辑是否包括两个相邻字符的变位（ab→ba）。默认为true。

```json
GET my_index/_search
{
  "query": { 
    "fuzzy": {
      "title": {
        "value": "城管是人",
        "fuzziness": "2"
      }
    }
  }
}
GET my_index/_search
{
  "query": { 
    "fuzzy": {
      "title": "城管好人"
    }
  }
}
```





####  短语前缀：match_phrase_prefix (性能不是很高)

##### match_phrase：

- match_phrase会分词
- 被检索字段必须包含match_phrase中的所有词项并且顺序必须是相同的
- 被检索字段包含的match_phrase中的词项之间不能有其他词项

##### 概念：

```
match_phrase_prefix与match_phrase相同,但是它多了一个特性,就是它允许在文本的最后一个词项(term)上的前缀匹配,如果 是一个单词,比如a,它会匹配文档字段所有以a开头的文档,如果是一个短语,比如 "this is ma" ,他会先在倒排索引中做以ma做前缀搜索,然后在匹配到的doc中做match_phrase查询,(网上有的说是先match_phrase,然后再进行前缀搜索, 是不对的)
```

##### 参数

- analyzer 指定何种分析器来对该短语进行分词处理
- max_expansions 限制匹配的最大词项
- boost 用于设置该查询的权重
- slop 允许短语间的词项(term)间隔：slop 参数告诉 match_phrase 查询词条相隔多远时仍然能将文档视为匹配 什么是相隔多远？ 意思是说为了让查询和文档匹配你需要移动词条多少次？



#### 前/中/后缀搜索的优化方案



### N-gram和edge ngram --- 一种分词器（切词器） 按照字符切分

##### tokenizer

```json
GET _analyze
{
  "tokenizer": "ngram",
  "text": "reba always loves me"
}
```

##### token filter --- 词项过滤器

```json
GET _analyze
{
  "tokenizer": "ik_max_word",
  "filter": [ "ngram" ],
  "text": "reba always loves me"
}
```

##### min_gram：创建索引所拆分字符的最小阈值

##### max_gram：创建索引所拆分字符的最大阈值

##### ngram：从每一个字符开始,按照步长,进行分词,适合<font color=green>前缀中缀</font>检索

##### edge_ngram：从第一个字符开始,按照步长,进行分词,适合<font color=pink>前缀</font>匹配场景(默认min_gram=1,max_gram=1)

```json
# 显示定义 索引映射器
PUT my_index
{
  "settings": {
    "analysis": { # 自定义过滤器
      "filter": {
        "2_3_edge_ngram": {
          "type": "edge_ngram",
          "min_gram": 2,
          "max_gram": 3
        }
      },
      "analyzer": { # 自定义分词器
        "my_edge_ngram": {
          "type":"custom",
          "tokenizer": "standard",
          "filter": [ "2_3_edge_ngram" ]
        }
      }
    }
  },
  "mappings": {
    "properties": { # 定义索引 字段
      "text": {
        "type": "text",
        "analyzer":"my_edge_ngram", # 指定所使用的分词器
        "search_analyzer": "standard"
      }
    }
  }
}

# 批量插入数据
POST /my_index/_bulk
{ "index": { "_id": "1"} } { "text": "my english" } { "index": { "_id": "2"} } { "text": "my english is good" } { "index": { "_id": "3"} } { "text": "my chinese is good" } { "index": { "_id": "4"} } { "text": "my japanese is nice" } { "index": { "_id": "5"} } { "text": "my disk is full" }


# 查询
GET /my_index/_search
{
  "query": {
    "match_phrase": {
      "text": "my eng is goo"
    }
  }
}

```



### 12 搜索推荐 Suggester

搜索一般都会要求具有“搜索推荐”或者叫“搜索补全”的功能，即在用户输入搜索的过程中，进行自动补全或者纠错。以此来提高搜索文档的匹配精准度，进而提升用户的搜索体验，这就是Suggest。

##### 分类

- Term Suggester
- Phrase Suggester
- **Completion Suggester**
- Context Suggester



#### 12.1 term suggester

只基于tokenizer之后的单个term去匹配建议词，并不会考虑多个term之间的关系

```json
POST <index>/_search
{
    "suggest": {
        "<suggest_name>": {
            "text": "<search_content>",
            "term": {
                "suggest_mode": "<suggest_mode>",
                "field": "<field_name>"
            }
        }
    }
}

#示例
# 添加数据
POST _bulk
{ "index" : { "_index" : "news","_id":1 } } { "title": "baoqiang bought a new hat with the same color of this font, which is very beautiful baoqiangba baoqiangda baoqiangdada baoqian baoqia"} { "index" : { "_index" : "news","_id":2 } } { "title": "baoqiangge gave birth to two children, one is upstairs, one is downstairs baoqiangba baoqiangda baoqiangdada baoqian baoqia"} { "index" : { "_index" : "news","_id":3} } { "title": "baoqiangge 's money was rolled away baoqiangba baoqiangda baoqiangdada baoqian baoqia"} { "index" : { "_index" : "news","_id":4} } { "title": "baoqiangda baoqiangda baoqiangda baoqiangda baoqiangda baoqian baoqia"}

# 推荐搜索
POST /news/_search
{
  "suggest": {
    "my-suggestion": {
      "text": "baoqing baoqiang",
      "term": {
        "suggest_mode":"always",
        "field": "title",
        "min_doc_freq": 3
      }
    }
  }
}

GET /news/_search
{ 
  "suggest": {
    "my-suggestion": {
      "text": "baoqing baoqiang",
      "term": {
        "suggest_mode": "popular",
        "field": "title",
        "max_edits":2,
        "max_term_freq":1
      }
    }
  }
}

```

#### Options：

- **text**：用户搜索的文本

- **field**：要从哪个字段选取推荐数据

- **analyzer**：使用哪种分词器

- **size**：每个建议返回的最大结果数

- sort

  ：如何按照提示词项排序，参数值只可以是以下两个枚举：

  - **score**：分数>词频>词项本身
  - **frequency**：词频>分数>词项本身

- suggest_mode

  ：搜索推荐的推荐模式，参数值亦是枚举：

  - missing：默认值，仅为不在索引中的词项生成建议词
  - popular：仅返回与搜索词文档词频或文档词频更高的建议词
  - always：根据 建议文本中的词项 推荐 任何匹配的建议词

- **max_edits**：可以具有最大偏移距离候选建议以便被认为是建议。只能是1到2之间的值。任何其他值都将导致引发错误的请求错误。默认为2

- **prefix_length**：前缀匹配的时候，必须满足的最少字符

- **min_word_length**：最少包含的单词数量

- **min_doc_freq**：最少的文档频率

- **max_term_freq**：最大的词频



#### 12.2 Phrase suggest

```json
# Phrase suggester
POST test/_search
{
  "suggest": {
    "text": "Luceen and elasticsearhc",
    "simple_phrase": {
      "phrase": {
        "field": "title.trigram",
        "max_errors": 2, # 纠正的词数
        "gram_size": 1,
        "confidence":0, # 可信度 默认1 ， 0 表示相关性 不限制
        "direct_generator": [
          {
            "field": "title.trigram",
            "suggest_mode": "always"
          }
        ],
        "highlight": { # 高亮显示
          "pre_tag": "<em>",
          "post_tag": "</em>"
        }
      }
    }
  }
}
```



#### 12.3 Completion Suggester

自动补全，自动完成，支持三种查询【前缀查询（prefix）模糊查询（fuzzy）正则表达式查询（regex)】 ，主要针对的应用场景就是"Auto Completion"。 此场景下用户每输入一个字符的时候，就需要即时发送一次查询请求到后端查找匹配项，在用户输入速度较高的情况下对后端响应速度要求比较苛刻。因此实现上它和前面两个Suggester采用了不同的数据结构，索引并非通过倒排来完成，而是将analyze过的数据编码成FST和索引一起存放。对于一个open状态的索引，FST会被ES整个装载到内存里的，进行前缀查找速度极快。但是FST只能用于前缀查找，这也是Completion Suggester的局限所在。

- completion：es的一种特有类型，**<font color=orange>专门为suggest提供，基于内存，性能很高</font>**。
- prefix query：基于前缀查询的搜索提示，是最常用的一种搜索推荐查询。
  - prefix：客户端搜索词
  - field：建议词字段
  - size：需要返回的建议词数量（默认5）
  - skip_duplicates：是否过滤掉重复建议，默认false
- fuzzy query
  - fuzziness：允许的偏移量，默认auto
  - transpositions：如果设置为true，则换位计为一次更改而不是两次更改，默认为true。
  - min_length：返回模糊建议之前的最小输入长度，默认 3
  - prefix_length：输入的最小长度（不检查模糊替代项）默认为 1
  - unicode_aware：如果为true，则所有度量（如模糊编辑距离，换位和长度）均以Unicode代码点而不是以字节为单位。这比原始字节略慢，因此默认情况下将其设置为false。
- regex query：可以用正则表示前缀，不建议使用



**<font color=orange>内存代价太大，原话是：性能高是通过大量的内存换来的</font>**

**<font color=orange>只能前缀搜索,假如用户输入的不是前缀 召回率可能很低</font>**

```json

#complate suggester
GET suggest_carinfo/_search?size=100
DELETE suggest_carinfo

# 显示创建映射
PUT suggest_carinfo
{
  "mappings": {
    "properties": {
        "title": {
          "type": "text",
          "analyzer": "ik_max_word", # 指定中文分词器
          "fields": {
            "suggest": {
              "type": "completion", # 指定推荐类型 为completion
              "analyzer": "ik_max_word"
            }
          }
        },
        "content": {
          "type": "text",
          "analyzer": "ik_max_word"
        }
      }
  }
}


# 批量添加数据
POST _bulk
{"index":{"_index":"suggest_carinfo","_id":1}} {"title":"宝马X5 两万公里准新车","content":"这里是宝马X5图文描述"}{"index":{"_index":"suggest_carinfo","_id":2}}{"title":"宝马5系","content":"这里是奥迪A6图文描述"}{"index":{"_index":"suggest_carinfo","_id":3}}{"title":"宝马3系","content":"这里是奔驰图文描述"}{"index":{"_index":"suggest_carinfo","_id":4}}{"title":"奥迪Q5 两万公里准新车","content":"这里是宝马X5图文描述"}{"index":{"_index":"suggest_carinfo","_id":5}}{"title":"奥迪A6 无敌车况","content":"这里是奥迪A6图文描述"}{"index":{"_index":"suggest_carinfo","_id":6}}{"title":"奥迪双钻","content":"这里是奔驰图文描述"}{"index":{"_index":"suggest_carinfo","_id":7}}{"title":"奔驰AMG 两万公里准新车","content":"这里是宝马X5图文描述"}{"index":{"_index":"suggest_carinfo","_id":8}}{"title":"奔驰大G 无敌车况","content":"这里是奥迪A6图文描述"}{"index":{"_index":"suggest_carinfo","_id":9}}{"title":"奔驰C260","content":"这里是奔驰图文描述"}{"index":{"_index":"suggest_carinfo","_id":10}}{"title":"nir奔驰C260","content":"这里是奔驰图文描述"}


GET suggest_carinfo/_search?pretty
{
  "suggest": {
    "car_suggest": {
      "prefix": "奥迪",
      "completion": {
        "field": "title.suggest"
      }
    }
  }
}

#1：内存代价太大，原话是：性能高是通过大量的内存换来的
#2：只能前缀搜索,假如用户输入的不是前缀 召回率可能很低

POST suggest_carinfo/_search
{
  "suggest": {
    "car_suggest": {
      "prefix": "宝马5系",
      "completion": {
        "field": "title.suggest",
        "skip_duplicates":true,
        "fuzzy": {
          "fuzziness": 2
        }
      }
    }
  }
}
```



#### 12.4 Context Suggester

完成建议者会考虑索引中的所有文档，但是通常来说，我们在进行智能推荐的时候最好通过某些条件过滤，并且有可能会针对某些特性提升权重。

- contexts：上下文对象，可以定义多个
  - name：`context`的名字，用于区分同一个索引中不同的 `context`对象。需要在查询的时候指定当前name
  - type：`context`对象的类型，目前支持两种：category和geo，分别用于对suggest item分类和指定地理位置。
  - boost：权重值，用于提升排名
- path：如果没有path，相当于在PUT数据的时候需要指定context.name字段，如果在Mapping中指定了path，在PUT数据的时候就不需要了，因为 Mapping是一次性的，而PUT数据是频繁操作，这样就简化了代码。这段解释有木有很牛逼，网上搜到的都是官方文档的翻译，觉悟雷同。



```json
# context suggester
# 定义一个名为 place_type 的类别上下文，其中类别必须与建议一起发送。
# 定义一个名为 location 的地理上下文，类别必须与建议一起发送
GET place/_search
GET place/_mapping
PUT place
{
  "mappings": {
    "properties": {
      "suggest": {
        "type": "completion",
        "contexts": [
          {
            "name": "place_type",
            "type": "category"
          },
          {
            "name": "location",
            "type": "geo",
            "precision": 4
          }
        ]
      }
    }
  }
}

PUT place/_doc/1
{
  "suggest": {
    "input": [ "timmy's", "starbucks", "dunkin donuts" ],
    "contexts": {
      "place_type": [ "cafe", "food" ]                    
    }
  }
}
PUT place/_doc/2
{
  "suggest": {
    "input": [ "monkey", "timmy's", "Lamborghini" ],
    "contexts": {
      "place_type": [ "money"]                    
    }
  }
}


GET place/_search
POST place/_search?pretty
{
  "suggest": {
    "place_suggestion": {
      "prefix": "sta",
      "completion": {
        "field": "suggest",
        "size": 10,
        "contexts": {
          "place_type": [ "cafe", "restaurants" ]
        }
      }
    }
  }
}
# 某些类别的建议可以比其他类别提升得更高。以下按类别过滤建议，并额外提升与某些类别相关的建议
GET place/_search
POST place/_search?pretty
{
  "suggest": {
    "place_suggestion": {
      "prefix": "tim",
      "completion": {
        "field": "suggest",
        "contexts": {
          "place_type": [                             
            { "context": "cafe" },
            { "context": "money", "boost": 2 }
          ]
        }
      }
    }
  }
}
```







### 13 数据建模

#### 13.1 嵌套类型查询： Nested

nested属于object类型的一种，是Elasticsearch中用于复杂类型对象数组的索引操作。**Elasticsearch没有内部对象的概念**，因此，ES在存储复杂类型的时候会把对象的复杂层次结果扁平化为一个键值对列表。

**使用nested为复杂类型创建mapping：**

```json
PUT <index_name>
{
  "mappings": {
    "properties": {
      "<nested_field_name>": {
        "type": "nested" # 给字段指定数据类型
      }
    }
  }
}

# 显式定义映射
PUT order
{
  "mappings": {
    "properties": {
      "goods_list": { # 使用nested为复杂类型创建mapping
        "type": "nested", # 数据是列表， 因此给goods_list 设置类型为nested
        "properties": {
          "name": {
            "type": "text",
            "analyzer": "ik_max_word",
            "fields": {
              "keyword": {
                "type": "keyword",
                "ignore_above": 256
              }
            }
          }
        }
      }
    }
  }
}
# 插入数据
PUT /order/_doc/1
{
  "order_name": "小米10 Pro订单",
  "desc": "shouji zhong de zhandouji",
  "goods_count": 3,
  "total_price": 12699,
  "goods_list": [
    {
      "name": "小米10 PRO MAX 5G",
      "price": 4999
    },
    {
      "name": "钢化膜",
      "price": 19
    },
    {
      "name": "手机壳",
      "price": 199
    }
  ]
}
# 插入数据
PUT /order/_doc/2
{
  "order_name": "扫地机器人订单",
  "desc": "shouji zhong de zhandouji",
  "goods_count": 2,
  "total_price": 12699,
  "goods_list": [
    {
      "name": "小米扫地机器热儿",
      "price": 1999
    },
    {
      "name": "洗碗机",
      "price": 4999
    }
  ]
}

# 搜索数据
GET /order/_search
{
  "query": {
    "nested": { # 指定 nested
      "path": "goods_list",
      "query": {
        "bool": {
          "must": [
            {
              "match": {
                "goods_list.name": "小米10"
              }
            },
            {
              "match": {
                "goods_list.price": 4999
              }
            }
          ]
        }
      }
    }
  }
}
GET /order/_search
{
  "query": {
    "nested": {
      "path": "goods_list",
      "query": {
        "bool": {
          "must": [
            {
              "match": {
                "goods_list.name": "洗碗机"
              }
            },
            {
              "match": {
                "goods_list.price": "1999"
              }
            }
          ]
        }
      }
    }
  }
}
```



#### 13.2 父子级关系：Join

连接数据类型是一个特殊字段，它在同一索引的文档中创建父/子关系。关系部分在文档中定义了一组可能的关系，每个关系是一个父名和一个子名。父/子关系可以定义如下

```json
PUT <index_name>
{
  "mappings": {
    "properties": {
      "<join_field_name>": { 
        "type": "join",
        "relations": {
          "<parent_name>": "<child_name>" 
        }
      }
    }
  }
}
```

**使用场景**

`join`类型不能像关系数据库中的表链接那样去用，不论是 `has_child`或者是 `has_parent`查询都会对索引的**<font color=red>查询性能有严重的负面影响</font>**。并且会触发[global ordinals](https://www.elastic.co/guide/en/elasticsearch/reference/7.12/eager-global-ordinals.html#_what_are_global_ordinals)

```
join`**唯一**合适应用场景是：当索引数据包含一对多的关系，并且其中一个实体的数量远远超过另一个的时候。比如：`老师`有 `一万个学生
```

**注意**

- 在索引父子级关系数据的时候必须传入routing参数，即指定把数据存入哪个分片，因为父文档和子文档必须在同一个分片上，因此，在获取、删除或更新子文档时需要提供相同的路由值。
- 每个索引只允许有一个 `join`类型的字段映射
- 一个元素可以有多个子元素但只有一个父元素
- 可以向现有连接字段添加新关系
- 也可以向现有元素添加子元素，但前提是该元素已经是父元素



#### 13.3 数据建模





**数据建模的意义总结如下，但其实不仅限于以下几点：**

- 开发：简化开发流程，从而提高效率
- 产品：提升数据的存储效率，提升查询性能
- 管理：前期准备充分，降低后期出现问题的可能性
- 成本：综合各个因素，降低整体的运营和管理成本



### 14 ES客户端 Elasticsearch Clients

- 与语言无关
- 与ES服务端连接的工具

#### 14.1 Java Client

https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.18/getting-started-java.html



1. Installation in a Maven Project

```xml
<project>
  <dependencies>

    <dependency>
      <groupId>co.elastic.clients</groupId>
      <artifactId>elasticsearch-java</artifactId>
      <version>9.0.1</version>
    </dependency>

  </dependencies>
</project>
```



2. Connecting

```jav
// URL and API key
String serverUrl = "https://localhost:9200";
String apiKey = "VnVhQ2ZHY0JDZGJrU...";

ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
    .host(serverUrl)
    .apiKey(apiKey)
);

// Use the client...

// Close the client, also closing the underlying transport object and network connections.
esClient.close();
```



3. Operations

``` java
# Creating an index
esClient.indices().create(c -> c
    .index("products")
);

# Indexing documents
Product product = new Product("bk-1", "City bike", 123.0);

IndexResponse response = esClient.index(i -> i
    .index("products")
    .id(product.getSku())
    .document(product)
);

logger.info("Indexed with version " + response.version());

#Geting documents
GetResponse<Product> response = esClient.get(g -> g
    .index("products")
    .id("bk-1"),
    Product.class
);

if (response.found()) {
    Product product = response.source();
    logger.info("Product name " + product.getName());
} else {
    logger.info ("Product not found");
}


# Searching documents
    String searchText = "bike";

SearchResponse<Product> response = esClient.search(s -> s
        .index("products")
        .query(q -> q
            .match(t -> t
                .field("name")
                .query(searchText)
            )
        ),
    Product.class
);

```







#### 14.2 嗅探器 sniff

在旧版 Transport Client (7.x 之前)中确实有 Sniffer 功能，但随着 Transport Client 的弃用和移除，这一功能也随之消失。新的 REST 客户端设计更倾向于显式配置而非自动发现。







## 高手进阶

## 运维调优

## 项目实战

