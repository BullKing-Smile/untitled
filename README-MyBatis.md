# MyBatis
> ORM框架


- 利用反射技术， 获取mapper层接口对应的sql
- 解析方法传递的参数信息
- 替换sql中参数对应的值
- JDBC PreparedStatement准备SQL
- 执行SQL
- 


### MyBatis 启动过程
- 加载配置文件 mybatis-config.xml
- 创建SqlSessionFactory对象， 这是MyBatis的核心对象，用于创建SqlSession对象
- 工厂对象中 创建SqlSession对象， MyBatis的会话对象， 用于执行数据库操作。
- 加载映射文件
- 初始化Mapper接口(sqlSesion.getMapper(UserMapper.class)) --- 获取代理对象
- 启动完成


## MyBatis 中缓存设计
> 缓存的作用是提高数据库的访问性能。
> 将查询结果缓存到内存中， 下次有相同的查询请求时，直接从缓存中取出结果，避免再次访问数据库， 从而提高查询的响应速度。

### 一级缓存
> SqlSession级别的缓存， 默认开启。
> 同一个SqlSession执行相同的SQL, 会先从缓存中查找

关闭一级缓存
<setting name="localCacheScope" value="STATEMENT">

### 二级缓存
> 二级缓存是Mapper级别的缓存， 默认时关闭的
> 不同的SqlSession执行相同的SQL时，如果开启二级缓存， 会先从缓存中查找

- 开启二级缓存 
控制全局缓存(二级缓存) 默认时true
<setting name="cacheEnabled" value="true/>
- 找到具体的映射文件中设置cache标签

```xml
<mapper xmlns="http://mybatis.org/schema/mybatis-mapper">
    <cache/>
</mapper>
```

可以设置 自定义Cache接口
```xml
<mapper xmlns="http://mybatis.org/schema/mybatis-mapper">
    <cache type="org.springframework.data.redis.cache.RedisCache"/>
</mapper>
```

如果想关闭缓存 可以设置
userCache="false"


### 三级缓存
> 分布式的系统架构 存在缓存数据不一致的问题， 因此 将缓存放在中间件中 (eg: redis)
MyBatis 中 Cache接口， 默认时放到内存中
因此 重写Cache 接口 实现


## SqlSession 的安全问题

Connection/
DefaultSqlSession 都是 <font color=red><b>线程不安全</b></font>
因此 <font color=yellow><b>使用 ThreadLocal</b></font> 来实现多线程共享 SqlSession 来保证SqlSession对象的唯一性。



## MyBatis 中设计的设计模式
类型分为三类
- 创建型 --- (单例/工厂/抽象工厂/建造者/原型)
- 结构型 --- (适配器/装饰着/代理/组合/桥接/外观/享元)
- 行为型 --- (策略/观察者/模板方法/责任链/命令/状态/中介者)


缓存模块 -- 装饰器模式， 
日志模块 -- 适配器模式，策略模式，代理模式
反射模块 -- 工厂模式， 装饰者模式
Mapping -- 代理模式
SqlSessionFactory -- 建造者模式


## SqlSessionFactory
> 作用：使用建造者模式(SqlSessionFactoryBuilder) 创建SqlSession对象
> 单例
> 配置文件的 加载解析 保存在Configuration中

使用到的设计模式： 工厂模式，建造者模式


## SqlSession
MyBatis一个非常核心的API, 实现对数据库的操作


## MyBatis 分页原理

SQL
- MySQL --- limit
- Oracle --- row_no

MyBatis中实现分页的两种方式
- 逻辑分页 查询所有 RowBounds 分页数据返回
- 物理分页 拦截器 解析SQL AbstractParser 拼接sql 实现物理分页
com.github.pagehelper


## DefaultSqlSession 数据安全问题
> 首先 线程非安全 的对象使用， 不能声明为成员变量， 方法级别的使用 来保证安全性
> Spring提供了一个SqlSessionTemplate来实现SqlSession的相关定义，
> 这是一个动态代理对象， 然后在动态代理对象方法级别的DefaultSqlSession来实现相关的数据库操作。


## MyBatis中的延迟加载
> 单表不存在 延迟加载
> 只有 多表连接查询时 才存在延迟加载

实现方式：
- 1. 开启配置 lazyLoadingEnable 延迟加载的全局开关
- 2. 多表关联 (1:1  association, 1:n collection)



## MyBatis中 插件的原理
目的：
方便开发人员 对mybatis功能的增强

MyBatis基于动态代理机制 的四大核心组件：
- Executor
- ParameterHandler
- ResultSetHandler
- StatementHandler
对这四个 相关方法实现增强

- 1. 检查SQL
- 2. 对执行SQL 的 参数 做处理
- 3. 对 结果 做装饰处理
- 4. 对查询SQL 的分表做处理

常用的使用场景：
- 分页插件
- 性能监控
- SQL日志记录
- 数据权限控制
- 多租户数据隔离
- SQL执行时间统计

定义拦截器
```java
@Intercepts({
  @Signature(
    type = Executor.class, // 拦截的组件类型
    method = "query",      // 拦截的方法名
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class} // 方法参数类型
  )
})
public class MyPlugin implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    // 前置逻辑（如记录 SQL 开始时间）
    long start = System.currentTimeMillis();

    // 执行原方法（即被拦截的方法）
    Object result = invocation.proceed();

    // 后置逻辑（如记录 SQL 执行耗时）
    System.out.println("SQL 耗时：" + (System.currentTimeMillis() - start) + "ms");
    return result;
  }

  @Override
  public Object plugin(Object target) {
    // 创建代理对象（固定写法）
    return Plugin.wrap(target, this);
  }
}
```
注册插件 (mybatis-config.xml)
```xml
<plugins>
  <plugin interceptor="com.example.MyPlugin"/>
</plugins>
```


## Mapper接口 的使用有哪些要求
- Mapper映射文件 namespace 是 Mapper接口对应的全类路径的名称
- mapper接口中方法名 与mapper映射文件中 id一致
- 参数类型 parameterType 相同
- 出参 与 resultType类型相同
- 接口名称 与 Mapper映射文件 名称相同


## MyBatis中 获取自增主键
```xml
<insert id="saveUser" useGeneratedKeys="true" keyProperty="id">
</insert>
```

## 不同Mapper种的id是否可以相同
可以
因为 namespace 对应的是唯一的


## MyBatis 架构设计
简单的 单一的ORM框架
- 接口层 --- 面向开发者 提供相关API
> - SqlSession

- 核心层 --- MyBatis的核心功能实现： 增删改查操作
> - 配置解析
> - 参数映射
> - SQL解析
> - SQL执行
> - 结果集映射
> - 插件

- 基础模块 --- 由很多相互之间 没有关联的模块组成， 作用是支持核心层完成核心功能
> - 反射模块
> - 日志模块
> - 缓存模块
> - 类型转换模块
> - 数据源模块
> - 事务模块
> - 资源加载
> - 。。。



## JDBC 开发的不足
- 数据库连接 需要自己管理 建立/关闭 繁琐和资源浪费 --- MyBatis种 数据库连接池来解决
- SQL硬编码在Java种 --- MyBatis 代码分离， 提供了映射文件Mapper.xml
- 参数传递 麻烦
- 结果集解析 麻烦
- MyBatis 事务处理的 相对便捷


## MyBatis异步编程步骤
- 创建SqlSessionFactory
- 通过创建的SqlSessionFactory对象来获取 SqlSession对象
- 通过SqlSession对象执行数据库操作
- 调用SqlSession 的 Commit方法来显示提交事务
- 调用 SqlSession 的close 关闭会话

## 实体中的属性名 和 表中字段名不一致
- sql 中 使用别名 as
- 结果集 ResultMap(Mapper.xml中) 定义对应关系 --- 常用操作


## MyBatis 中的执行器Executor

Executor 的类型有三类
- SIMPLE 默认类型 SimpleExecutor 每次都是新的PreparedStatement对象， 频繁操作时 效率不高
- BATCH BatchExecutor 批处理执行器,将多个SQL语句批量执行, 执行大量插入、更新或删除操作时非常有用
- REUSE ReuseExecutor 复用缓存的PreparedStatement

设置执行器类型的两种方式
- SqlSessionFactory openSession() 方法来指定ExecutorType
- 全局配置文件中的setting来配置默认执行器


## 如何实现多个传参
- #{name} --- @Param注解指定 参数名称  === 推荐做法
- #{0,1,2,3} --- 指定下表的方式 === 不建议
- Map传值 ， #{key}, 根据key取值 --- 参数灵活 === 推荐
- 对象传递  #{fieldName}, parameterType指定对象类型 === 可读性强，推荐 


## MyBatis中的日志
- MyBatis中的日志模块， 使用了适配器模式
- 如果我们需要适配MyBatis没有提供日志框架， 那么对应的需要添加相关的适配类
- 在全局配置文件中设置日志的实现
- MyBatis的日志框架中提供了一个jdbc 这个包，里边实现了JDBC相关操作的日志记录


## MyBatis中记录SQL日志的原理
日志的实现 本质上是其代理对象来实现的
- Connection --- 代理对象:ConnectionLogger
- PreparedStatement -- 代理对象:PreparedStatementLogger
- ResultSet --- 代理对象:ResultSetLogger

本质都是通过代理对象实现的。 代理对象中完成相关日志的操作。然后调用目标对象完成数据库的操作。


## MyBatis中数据源模块的设计
mybatis -> datasource

MyBatis中单独设计了DataSource数据源模块 

UnpooledDataSource 非数据库连接池的实现
PooledDataSource 数据库连接池的实现


## 事务模块的设计

事务管理有两个选择
- JDBC 在MyBatis中自己处理事务的管理
- Managed: MyBatis中没有处理任何事务逻辑，交给spring容器来管理事务。


## Mapper接口的设计


## Reflector 反射模块
> 针对反射封装简化的模块， MyBatis是一个ORM框架<br>
> 主要用于将Java对象和数据库中的数据进行映射<br>
> 所以在对象属性与数据库字段的映射过程中，反射操作是不可避免的。<br>
> <font color=yellow>***Reflector模块可能就是用来优化反射操作的，避免每次映射都直接使用Java原生的反射API，从而提高性能***</font>。

工作原理：
> 首次访问java类时， Reflector***模块缓存这些反射信息***，避免重复解析类的元数据，减少反射调用的开销。

设计目的：
- 性能优化
> 通过预解析并缓存类的元数据（如方法、属性），减少重复解析的开销(java原生反射getMethod,getField)
- 统一元数据的管理
> 规范化类的属性名与方法名的映射逻辑（如处理isXxx()布尔方法、字段名驼峰转换等）<br>
- 简化复杂操作
> 为上层模块（如MetaObject、ResultMap）提供简洁的反射操作接口，隐藏反射的复杂性。<br>

缓存的内容
- 属性名 与 对应方法的映射 --- username 对应 getUsername, setUsername
- 字段名的大些小不敏感 username userName 统一映射到 userName

使用场景
- ResultMap --- 结果集中ResultMap的结果映射
- 参数绑定 --- ParameterMapping (调用属性对应的 getter 方法)
- 动态SQL处理 --- #{user.name} 快速访问嵌套属性

关联 JVM调优
> 反射会创建大量的类加载器DelegatingClassLoader <br>
> 这会消耗较大的元空间metaspace(内存中的一块区域)<br>
> 同时 存在内存碎片化，元空间利用率不高， 触发FGC

策略
- 适当调整metaspace的空间大小
- 优化不合理的反射调用（mybatis中的reflector模块 缓存反射信息）


## 类型转换 接口的设计
Java中的类型 与 数据库中字段类型的 映射关系

类型处理器 TypeHandlers
> TypeHandler接口，负责处理Java类型和JDBC类型之间的转换.<br>
> MyBatis 在设置预处理语句(PreparedStatement)中的参数或从结果集中 取出一个值时，都会用类型处理器获取到的值已核实的方式转换成Java类型。

两个方向的转换
- Java → JDBC：将 Java 类型转换为 JDBC 类型（通过 PreparedStatement 设置参数）。
- JDBC → Java：将 JDBC 类型转换为 Java 类型（从 ResultSet 提取结果）

TypeHandlers
- StringTypeHandler
- IntegerTypeHandler
- DateTypeHandler
- BigDecimalTypeHandler
- BooleanTypeHandler
- EnumTypeHandler
- ArrayTypeHandler

以上无法满足时， 也可以自定义， 实现TypeHandler接口即可


## MyBatis 与 Spring 整合

整合主要是通过 mybatis-spring 模块实现的，
核心目的时： 将 mybatis的核心组件 SqlSessionFactory, SqlSession, Mapper接口交给Spring容器管理， 实现<font color=orange>***依赖注入DI***</font>,声明时事务管理等。

整合的核心目的:
- 依赖注入 spring来管理mybatis组件
- 事务管理
- 简化配置
- 资源生命周期管理

实现步骤
- 1. 添加依赖
```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>2.0.7</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>5.3.20</version>
</dependency>
```

SpringBoot 下自动配置
mybatis-spring-boot-starter
MybatisAutoConfiguration --- 自动创建 SqlSessionFactory 和 SqlSessionTemplate
MapperScannerAutoConfiguration --- 自动扫描 Mapper 接口

- 2. 属性配置
```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.model
  configuration:
    map-underscore-to-camel-case: true
```


## MyBatis trim标签

- /<trim> 标签的核心作用
去除多余前缀/后缀：
自动删除 SQL 片段开头或结尾的指定字符（如 AND、OR、,）。

添加自定义前缀/后缀：
在 SQL 片段前后插入固定内容（如 WHERE、SET、括号等）。

替代 <where> 和 <set>：
通过灵活配置，实现与 <where>、<set> 相同的功能，并支持更复杂的场景。


传统做法
```xml
<select id="selectUser" resultType="User">
  SELECT * FROM users
  <where>
    <if test="name != null">AND name = #{name}</if>
    <if test="age != null">AND age = #{age}</if>
  </where>
</select>
<!-- 场景二-->
<update id="updateUser">
UPDATE users
<set>
    <if test="name != null">name = #{name},</if>
    <if test="age != null">age = #{age},</if>
</set>
WHERE id = #{id}
</update>
```
等效trim写法
```xml
<select id="selectUser" resultType="User">
  SELECT * FROM users
  <trim prefix="WHERE" prefixOverrides="AND | OR">
    <if test="name != null">AND name = #{name}</if>
    <if test="age != null">AND age = #{age}</if>
  </trim>
</select>
<!-- 场景二 -->
<update id="updateUser">
UPDATE users
<trim prefix="SET" suffixOverrides=",">
    <if test="name != null">name = #{name},</if>
    <if test="age != null">age = #{age},</if>
</trim>
WHERE id = #{id}
</update>
<!-- 场景三 IN查询 -->
<select id="selectUsersByIds" resultType="User">
SELECT * FROM users
<trim prefix="WHERE" prefixOverrides="AND | OR">
    <if test="ids != null and !ids.isEmpty()">
        AND id IN
        <foreach collection="ids" item="id" open="(" separator="," close=")">
            #{id}
        </foreach>
    </if>
    <if test="name != null">AND name = #{name}</if>
</trim>
</select>

<!-- 场景四 多条件查询-->
<select id="selectUserByCondition" resultType="User">
SELECT * FROM users
<trim prefix="WHERE" prefixOverrides="AND | OR">
    <if test="role != null">role = #{role}</if>
    <if test="status != null or name != null">
        AND (
        <trim prefixOverrides="AND | OR">
            <if test="status != null">status = #{status}</if>
            <if test="name != null">OR name LIKE #{name}</if>
        </trim>
        )
    </if>
</trim>
</select>
```

<font color=orange>***解释：***</font>
- prefix="WHERE"：在条件前添加 WHERE/SET
- prefixOverrides="AND | OR"：删除条件开头的 AND 或 OR/','



## MyBatis bind 标签 
- 场景一: 模糊查询
```xml
<select id="searchUsers" resultType="User">
  <bind name="pattern" value="'%' + name + '%'"/>
  SELECT * FROM users
  WHERE username LIKE #{pattern}
</select>
```

- 场景二: 动态排序字段
```xml
<select id="getUsers" resultType="User">
  <bind name="orderColumn" value="@com.example.util.SafeSort@validate(orderBy)"/>
  SELECT * FROM users
  ORDER BY ${orderColumn} #{orderDirection}
</select>
```




## 相关面试题

### #{} 与 ${} 的区别
相同点
- 都表示参数值， 写在Mapper.xml中

#{} 占位符， 在预编译时(PreparedStatement) 被替换成 ?
> 先预编译， 再替换指， 不存在sql注入风险
> select * from user where id = #{id}
> select * from user where id = ?

${} 占位符，时单纯的把占位符 替换成参数值， 再进行后续处理。
> 先替换值， 再预编译， 可能影响语义
> 存在sql侵入风险
