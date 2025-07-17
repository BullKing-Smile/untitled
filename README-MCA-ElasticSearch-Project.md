# Elastic Project 项目实战





















## 2. ELK 分布式日志系统

“ELK”是三个开源项目的首字母缩写，这三个项目分别是：**Elasticsearch、Logstash 和 Kibana**。Elasticsearch 是一个搜索和分析引擎。Logstash 是服务器端数据处理管道，能够同时从多个来源采集数据，转换数据，然后将数据发送到诸如 Elasticsearch 等“存储库”中。Kibana 则可以让用户在 Elasticsearch 中使用图形和图表对数据进行可视化。



### 2.1 介绍

#### 2.1.1 Elastic Stack的组成部分

- **Elasticsearch**：Elasticsearch（以下简称ES） 是一个分布式、RESTful 风格的搜索和数据分析引擎，能够解决不断涌现出的各种用例。 ES是 Elastic Stack 的核心，采用集中式数据存储，可以通过机器学习来发现潜在问题。ES能够执行及合并多种类型的搜索（结构化数据、非结构化数据、地理位置、指标）。支持 PB级数据的秒级检索。
- **Kibana**：Kibana 是一个免费且开放的用户界面，能够让您对 Elasticsearch 数据进行可视化，并让您在 Elastic Stack 中进行导航。您可以进行各种操作，从跟踪查询负载，到理解请求如何流经您的整个应用，都能轻松完成
- **Logstash**：Logstash 是免费且开放的服务器端数据处理管道，能够从多个来源采集数据，转换数据，然后将数据发送到合适的的“存储库”中。
- **Beats**：Beats 是一套免费且开源的轻量级数据采集器，集合了多种单一用途数据采集器。它们从成百上千或成千上万台机器和系统向 Logstash 或 Elasticsearch 发送数据。



#### 2.1.2 为什么要使用ELK

- 严格按照开发标准来说，开发人员是**不能登录生产服务器查看日志数据**
- 一个应用可能分布于多台服务器，难以查找
- 同一台服务区可能部署多个应用，**日志分散**难以管理
- **日志可能很大**，单个文件通常能达到GB级别，日志无法准确定位，日志查询不方便且速度慢
- 通常日志文件以**非结构化**存储，不支持数据可视化查询。
- **不支持日志分析**（比如慢查询日志分析、分析用户画像等）。





#### 2.1.3 使用场景

- 采集业务日志
- 采集Nginx日志
- 采集数据库日志，如MySQL
- 监控集群性能指标
- 监听网络端口
- 心跳检测





### 2.2 Logstash 简介

开源的流数据处理、转换（解析）和发送引擎，可以采集来自不同数据源的数据，并对数据进行处理后输出到多种输出源。Logstash是Elastic Stack的重要组成部分



#### 2.2.1 工作原理

Logstash的每个处理过程均已插件的形式实现，Logstash的数据处理过程主要包括： **Inputs** , **Filters** , **Outputs** 三部分，如图：

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/28/1656740975041/10e7ce6e578e4008988d3dfb3dc992a6.png#pic_center)





### 2.3 Beats 简介

Beats 是一套免费且开源的轻量级数据采集器，集合了多种单一用途数据采集器。它们从成百上千或成千上万台机器和系统向 Logstash 或 Elasticsearch 发送数据。



#### 2.3.1 Beats的基本特性

- **开源**：Beats 是一个免费且开放的平台，集合了多种单一用途数据采集器，各司其职，功能分离。社区中维护了上百个beat，社区地址：[戳我](https://cloud.fynote.com/share/[https://github.com/elastic/beats/blob/master/libbeat/docs/communitybeats.asciidoc](https://github.com/elastic/beats/blob/master/libbeat/docs/communitybeats.asciidoc))
- **轻量级**：体积小，职责单一、基于go语言开发，具有先天性能优势，不依赖于Java环境，对服务器资源占用极小。Beats 可以采集符合 Elastic Common Schema (ECS) 要求的数据，可以将数据转发至 Logstash 进行转换和解析。
- **可插拔**：Filebeat 和 Metricbeat 中包含的一些模块能够简化从关键数据源（例如云平台、容器和系统，以及网络技术）采集、解析和可视化信息的过程。只需运行一行命令，即可开始探索。
- **高性能**：**<font color=red>对CPU、内存和IO的资源占用极小</font>**。
- **可扩展**：由于Beats开源的特性，如果现有Beats不能满足开发需要，我们可以自行构建，并且完善Beats社区



#### 2.3.2 Logstash功能如此强大，为什么还要用Beats

就功能而言，Beats是弟弟，得益于Java生态优势，Logstash功能明显更加强大。但是Logstash在数据收集上的性能表现饱受诟病，Beats的诞生，其目的就是为了取代**Logstash Forwarder** 。







### 2.4 Kibana 介绍

Kibana 是一个免费且开放的可视化系统，能够让您对 Elasticsearch 数据进行可视化，并让您在 Elastic Stack 中进行导航。您可以进行各种操作，从跟踪查询负载，到理解请求如何流经您的整个应用，都能轻松完成。

**一张图片，胜过千万行日志**

![基本内容](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt47b86adba2f459aa/5fa31e03bfc5dd7188659491/screenshot-kibana-dashboard-webtraffic2-710-547x308.jpg)













### 2.5 FileBeats

[Documents](https://www.elastic.co/docs/reference/beats/heartbeat/running-on-docker)





```shell
docker pull docker.elastic.co/beats/filebeat:8.18.0
```









