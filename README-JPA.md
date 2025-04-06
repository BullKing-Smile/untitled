# JPA
> Java Persistence API,  Java EE（现 Jakarta EE）中用于对象关系映射（ORM） 的标准规范<br>
> 旨在简化 Java 应用与关系型数据库的交互.<br>
> <font color=orange>***基于JDBC的高级ORM规范***</font><br>
> Hibernate 实现了JPA接口的ORM框架。
> JPA 不太适合 复杂的查询。 当然利用各种插件也可以实现。 复杂的查询 需要考虑技术方案选型。

## 核心概念
- ORM(对象关系映射) <font color=yellow>***Object Relation Mapping***</font>
> Java 类（实体类）与数据库表关联，类的属性映射到表的字段，实现对象与数据的自动转换。

- 注解驱动
> - @Entity
> - @Table
> - @Column
> - @Id
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name")
    private String username;
    
    // Getter & Setter
}
```

- EntityManager JPA 核心接口
> 负责管理实体对象的生命周期（增删改查）和持久化上下文（缓存）

- JPQL（Java Persistence Query Language）
> 一种面向对象的查询语言，类似 SQL，但操作的是实体类而非数据库表

- 事务管理


## JPA 主要优势
- 标准化接口
- 开发效率高
- 跨数据库兼容 --- 数据库方言库Dialect
- 高级特性
> - 一级/二级缓存
> - 关联关系映射 @OneToMany、@ManyToOne
> - 乐观锁 @Version
> - 审计 @CreatedDate、@LastModifiedDate

SpringBoot 集成
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```


## JPA 的使用

### JPA对象的几种状态
- 临时状态(瞬时状态) --- new-提交前
- 持久状态 ---  数据库 提交后
- 游离状态 --- 删除(游离状态)已经提交 的对象
- 删除状态 --- 执行了remove, 事务提交前

```java 
tc.begin;
em.find(Teacher)
em.remove(teacher) -- 该 sql 将不会被执行
em.persis(teacher)
tc.commit --- 最终只有一条select 语句被执行
```

### 一级缓存的使用
```java 
tc.begin;
em.find(Teacher.class, 1L)
em.find(Teacher.class, 1L) 
tc.commit --- 最终只有一条select 语句被执行 此处使用了一级缓存
```




## JPA spring-boot 使用

### JPSQL 原生SQL
```java
@Query("from Teacher where username = ?1")
Teacher findTeacherByUsername(String username);
```

or

```java
    @Query("from Teacher where username = :username")
    Teacher findTeacherByUsername2(@Param("username") String username);
```

### 参数设置方式
- 索引 --- ?数字
- 具名 --- :参数名 结合 @Param

### 执行原生SQL
@Query(native=true, value="sql") --- native 默认时false

### 修改操作
> Insert/Update/Delete 都需要 @Modifying
```java
    @Transactional // 必须要加上事务的支持 通常放在业务逻辑层
    @Modifying // 通知springdatajpa, 这是一个修改操作 
    @Query("update Teacher t set t.username = :username where t.id =: id")
    int updateTeacher(@Param("username") String username, @Param("id") Long id)

```


### 规定的方法名
[Reference API](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
[Query Key Words](https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html)

- find...By/ read..By/ get..By/query...By/search...By/stream...By --- 查询
- exists...By -- 存在
- count...By -- 计数
- delete...By, remove...By
- ...First\<number>.../...Top<number>
- ...Distinct...


### Query by Example
```java
Teacher teacher1 = new Teacher();
        teacher1.setUsername("teacher-001");
        teacher1.setGender(1);
        teacher1.setGrade(4);
        ExampleMatcher matcher = ExampleMatcher.matching()
                        .withIgnoreCase("username")// 设置忽略大小写
                                .withIgnorePaths("gender");// 设置忽略属性
        Example<Teacher> teacherExample = Example.of(teacher1, matcher);

```



### 动态查询 Querydsl
[Querydls PredicateExecutor interface](https://docs.spring.io/spring-data/jpa/reference/repositories/core-extensions.html#core.extensions.querydsl)

### Querydsl
```java
       QTeacher teacher = QTeacher.teacher;
        BooleanExpression eq = teacher.id.eq(1L);

        eq.and()
                .gt()
                .lt()
                .in()
```

### @OneToOne Cascade 设置关联操作 OneToOne
- All 所有持久化操作
- PERSIST 只有插入 才会执行关联操作
- MERGE  只有修改 才会执行关联操作
- REMOVE  只有删除 才会执行关联操作

### @OneToOne FetchType
- LAZY 懒加载
- EAGER 立即加载 default

### @OneToOne orphanRemoval 关联移除
- true 关联数据值为null或者修改了其他的关联数据 则删除被取消关联的数据
- false

### @OneToOne optional  限制关联对象能否为null
- true 可以为null --- default
- false 不允许为null

### @OneToOne 双向关联 mappedBy
> 将外键约束执行另一方维护
> 值 等于 另一方的属性名称
### 一对一 1:1
@OneToOne
```java
@OneToOne(mappedBy="teacher", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
@JoinColumn(name = "gender_id")
private Gender gender;

// 单项关联
@OneToOne
@JoinColumn(name = "gender_id")
private Gender gender;
```


### 一对多 1:2



### 乐观锁 @Version
> 防止并发修改
> private @Version Long version;




### 审计 Audit
编写AuditorAware
@Configuration
public class UserAuditorAware implements AuditorAware<String>{}

添加启动 审计功能
@EnableJpaAuditing




## 相关面试题
