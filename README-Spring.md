# Spring

## 核心特性
- 控制反转（IoC） Inversion of Control --- <font color=orange>***用于解耦组件之间的依赖关系***</font>
> 核心机制：通过IoC容器（如ApplicationContext）管理对象的生命周期和依赖关系。
> 实现方式：
> - 构造函数注入：强制依赖项不可变。
> - Setter注入：可选依赖项的灵活配置。

总结：
- <font color=orange>***IoC 的核心思想***</font>：将对象的控制权交给容器，实现组件间的解耦。
- <font color=orange>***Spring 的实现***</font>：通过依赖注入（DI）和 IoC 容器管理 Bean 的生命周期。
- <font color=orange>***适用场景***</font>：任何需要灵活管理依赖关系的系统，尤其是大型企业级应用。

- 依赖注入（DI）

- 面向切面编程（AOP）
> 通过切面（Aspect）统一处理横切关注点（如日志、事务、安全）。
> - 使用<font color=orange><b>动态代理(JDK/CGLIB)</b></font>实现方法拦截。

- 声明式事务管理
> 实现方式：通过@Transactional注解配置事务（传播行为、隔离级别）。
> 优势：避免手动管理Connection和commit/rollback，代码简洁。

- Spring MVC
> Web应用开发框架。
> Spring Boot的出现简化了配置，提供了自动配置和起步依赖.



Bean 对象
普通对象

多数情况下 bean对象 就等于 普通对象

UserService.class-->无参构造方法-->普通对象-->依赖注入-->初始化(afterPropertiesSet)-->初始化后(AOP)-->代理对象-->放入上下文的Map中


单例bean 与 单例对象 并不是一回事

单例bean的单例对象 只对于 name而言有效




## 动态代理
> Spring主要用动态代理来实现AOP
>


### 动态代理的应用场景
- 声明式事务管理（@Transactional）
- 安全控制（如 Spring Security）
- 日志记录
- 性能监控
- 缓存处理

```java
package com.coocpu.springdemo.proxy;

import com.coocpu.springdemo.service.IUserService;
import com.coocpu.springdemo.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @auth Felix
 * @since 2025/3/15 22:58
 */
public class ProxyUtil {

    public static IUserService getProxyInstance(UserService userService) {
        IUserService o = (IUserService) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{IUserService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equalsIgnoreCase("login")
                                || method.getName().equalsIgnoreCase("getUserInfo")) {
                            long startTime = System.currentTimeMillis();

                            System.out.println("---- start " + method.getName() + " ----\n start = " + startTime);
                            Object invoke = method.invoke(userService, args);
                            long endTime = System.currentTimeMillis();

                            System.out.println("---- end fun ----\n end = " + endTime);
                            System.out.println("cost time = " + (endTime - startTime));
                            return invoke;
                        } else {
                            Object rx = method.invoke(method, args);
                            return rx;
                        }
                    }
                });
        return o;
    }

}
```

### Spring AOP 的底层机制
Spring AOP 基于<font color=yelow>***动态代理***</font>实现，核心步骤如下：
> 底层原理是 动态代理
> 反射 实现的
> 
- 根据目标对象的类型（是否实现接口）选择代理方式。
- 生成代理对象，拦截方法调用。 代理机制 将 关注点代码织入到 业务代码中 产生的新对象 叫做代理对象。
- 通过 Advice（通知） 和 Pointcut（切点） 定义增强逻辑。

JDK 动态代理：为实现类接口的目标对象生成代理对象 HandlerInvoker
CGLIB 动态代理：为没有实现接口的目标对象生成对象 增强类实现MethodInterceptor
SpringBoot2.xxx 开始， 默认选择CGLIB动态代理机制


Aspect --- 切面

Advice 通知类型
- @Before --- 前置通知
- @After --- 后置通知
- @Round --- 环绕通知
- @AfterReturning --- 返回后通知
- @AfterThrowing --- 抛出异常后通知

切面 --- Aspect 定义的类 切面
通知 --- advice Aspect中定义的 
连接点 --- joinPoint 业务代码中的每一个方法
切点 --- pointCut -- 关注点代码切入的连接点 叫做 切点
织入 --- weaving

AOP的应用
- spring的声明式 事务管理
- 记录日志
- 性能统计
- 权限控制

## Spring Web 启动过程

- 1. 启动Spring 容器 ， 扫描并创建各类bean
- 2. 加载tomcat 并注入DispatchServlet --> Tomcat监听指定端口 会将所有的请求 交给DispatchServlet处理
- 3. DispatchServlet 会从spring 容器中找到指定的bean 处理请求地址匹配的业务逻辑

// Spring 容器
AnnotationConfigWebApplicationContext;

// 负责扫描 被代理的类 -- > 返回给spring 容器 进行 实例代理对象
AutoConfigurationImportSelector 
fun public string[] selectorImports(AnnotationMetadata)


## SpringMVC 五大组件
- DispatcherServlet --- 前端/中央处理器
- HandlerMapping --- 处理映射器
- Controller --- 控制器
- ModelAndView --- 试图数据模型
- ViewResolver --- 视图解析器

### DispatcherServlet - 用于接收和响应所有请求


### HandlerMapping
Map<url, Handler> 存储路径和controller/function 信息 集合






