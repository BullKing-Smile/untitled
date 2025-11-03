# 面试突击，题目汇总







## 1. Java 基础

### 1.1 Object 对象有哪些方法？

- getClass() --- 获取运行时Class对象， 属于反射的内容，获取运行时的一些数据。
- hashCode() --- 返回对象的hash值，为了支持hash tables(哈希表), java中的hash表就是HashMap

- equals() --- 判断两个对象是否相等
- clone()  --- 创建对象副本， 延伸到 深拷贝 和浅拷贝， 默认浅拷贝。
- toString() --- 字符串形式  表示当前对象信息
- notify() --- **<font color=green>当某个线程持有当前对象的锁时，执行对象锁的notify, 唤醒之前基于wait挂起的一个线程</font>**
- notifyAll() --- 同上， 唤醒之前所有基于wait挂起的线程。
- wait() --- **<font color=red>当某个线程持有该对象的锁时， 可以执行锁wait, 将持有锁的线程挂起等待</font>**
- finalize() --- **<font color=orange>当触发垃圾回收时，无法基于可达性分析定位到，就会被垃圾回收器回收掉，回收之前，如果对象重写了finalize，就会触发该方法，可以执行一些清理工作，当时不保证一定执行,因为他用的时<font color=blue>守护线程</font></font>**





#### 1.1.1 **经典的<font color=#a000ff>多项式哈希算法</font>**

```code
// String类的hashCode实现
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;
        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];  // 核心计算
        }
        hash = h;
    }
    return h;
}
```

- 为什么是 31？

  - **31是一个适中的<font color=orange>质数</font>**

  - **<font color=red>使用质数可以减少哈希冲突（特别是与取模运算配合时）</font>**

  - **`31 * i` <font color=green>可以被JVM优化为位运算</font>**

    - ```
      31 * i == (i << 5) - i  // 现代JVM会自动做此优化
      ```



### 1.2 创建对象的方式

- new

- 反射 --- Construct.newInstance()   **spring 底层就是使用反射技术 实现对象实例的**

- 反序列化 --- **通过字节流构建对象的机制**(ois.readObject())

  - 流反序列化
  - JSON字符串反序列化

- clone(), 克隆

- 工厂模式构建对象

  



### 1.3 为什么要有clone?

快速复制一个对象，简化创建/赋值代码



**深拷贝和浅拷贝的区别**

- **浅拷贝**：**仅复制对象的<font color=#24af98>值类型属性，引用类型属性，直接复制引用，即共享引用类型</font>**

- **深拷贝**：**<font color=#f43f98>复制值类型属性，也复制引用类型属性</font>**



### 1.4 构造器是否可以被重写

可以被重载

重写，按照方法的特点 @Overrides, 而构造函数不存在这个注解

同一个类中 不能被重写，子类继承后可以定义自己的构造方法





### 1.5 跳出循环？如何在内部循环跳出外部循环？

- continue;

- break;

- 跳出外层循环：
  - 定义**<font color=red>标签</font>** label;  **break,continue的时候 指定标签**
  - 定义临时变量

```codes
outter: for(...) {
	for (...) {
		...
		break outter; # 跳出外部循环
	}
}
```





### 1.6 什么是泛型，怎么使用？

**Java中的泛型 就是一种 <font color=green>规避类型错误的而一种安全机制</font>**



声明一个集合并且指定泛型的类型后， 存储的数据类型就会有限制了， 存储不允许的类型， 编译期时，就会直接报错

- 类型的安全
- 减少强制类型转换 操作



**<font color=red>类型擦除</font>**问题:

泛型只发生在编写Java代码时； 编译时 自动转换为Object类型。



### 1.7 Java类加载顺序（类加载过程，双亲委派）

- **类加载过程**
  - 加载： **class字节码文件 <font color=green>加载到JVM中的方法区</font>，然后在内存中体现这个class对象**
  - 验证：验证加载的class文件是否被篡改
  - **准备：为类中的属性变量分配 内存空间， 并设置默认值**
  - 解析：将常量值 内的符号引用 转换为 直接引用
    - 符号引用 com.xxx.xxx/A.findAll() void
    - 直接引用 直接指向内存的具体位置
  - 初始化：**<font color=red>对静态变量 赋值</font>， 执行静态代码块， 初始化好父类。**
  - 类加载完毕， 可以进行new对象，或者直接使用静态方法
- **双亲委派**
  - Java中三种默认类加载器
    - BootstrapClassLoader --- **引导类加载器**负责加载jdk//jre/lib/rt.jar
    - ExtensionClassLoader ---  jdk/jre/lib/ext目录下所有的jar (jdk17 **PlatformClassLoader**)
    - ApplicationClassLoader --- 应用类加载器 classpath目录下的各种class文件
    - 自定义类加载器 --- 重写方法，指定要加载的位置
  - **<font color=red>双亲委派过程(父类委托)</font>**
    - 先调度ApplicationClassLoader,  如果没加载过 向上问
    - 问到ExtensionClassLoader, 如果没加载过 向上问
    - 问到BootstrapClassLoader, **如果没加载过 <font color=green>尝试加载</font>**, **如果rt.jar没有这个class文件可以加载，则<font color=red>往下分配</font>**
    - 分配到ExtensionClassLoader， 尝试加载(ext目录)， 没有则往下分配
    - 分配到ApplicationClassLoader， **尝试加载(classpath目录) ，如果没找到，也没加载到，<font color=orange>ClassNotFoundException 异常</font>**
  - **<font color=blue>双亲委派解决的问题</font>**
    - 防止class重复加载
    - 防止你破坏jdk结构

### 1.8 两个List交集元素

- Java API 本身提供了原始api来取交集， List.retainAll(), 内部使用双指针+contains() 实现。
- Hash表的方式，key=值， value=1 , 遍历第二个集合， 如果hash表中能拿到1，则放入交集。





### 1.9 面向对象编程OOP，讨论Java封装/继承/多态的实现及其应用场景

面向对象的思想 底层还是面向过程的， 只不过把一些复杂的操作 进行封装， 方便操作。



**封装**：隐藏（**私有化**）核心内容，提供公共方法提供给外部使用，**保证数据安全**， eg: setter() getter() 方法

**继承**：公共的属性和方法，向上抽象出公共的类。

**多态**：**同一个动作的不同实现**， <font color=red>父类接口指向子类的实现</font>。



### 1.10 数组和链表的区别

核心区别

- **查询效率** 
  - 数组直接**<font color=blue>通过下标(索引) 访问元素</font>， 时间复杂度O(1) 效率高**
  - 链表 只能顺序访问， 从头开始查找， **时间按复杂度O(n)**
- **增删效率**
  - 数组：插入/删除操作， 要移动元素， 效率低
  - 链表：插入/删除操作， 只需要修改相邻两个节点， 效率高
- **内存使用**
  - 数组：内存空间连续 而且需要制定长度， 扩容使用**<font color=red>复制</font>**的方式
  - 链表：内存空间不连续, 不需要提前申请，

**使用场景**：

- 快速随机**访问**元素， 元素个数确定 的场景 --- **<font color=green>使用数组</font>**

- 频繁**增删**数据 --- **<font color=orange>使用链表</font>**
- 线程池--阻塞队列-- 优先使用LinkedBlockingQueue, **链表更好**



###  1.11 Java集合框架的主要接口（List, Set, Map）的特点， 举例说明使用场景

- List
  - 特点： List是有序集合（存取 有序）， **<font color=red>元素可以重复</font>**, 并且可以存 null元素
  - 适用场景：
    - 需要维护插入的顺序， 实现一个队列
    - 需要遍历整个元素

- Set

  - 特点： **<font color=green>不允许元素重复</font>**, 也不保证元素的顺序，**能存储Null,但是只能有一个**

  - 适用场景：
    - **<font color=orange>保证元素唯一性</font>**， 比如手机号，用户id
    - **<font color=blue>需要做去重操作</font>**，Set是一个很好的工具
  - 拓展：**<font color=red>Set的本质是 基于HashMap的key来实现的。基于Hash表做的去重</font>**

- Map
  - **key-value结构，双列集合**。JDK1.8之后优化了 红黑树 提高HashMap的查询效率



### 1.12 HashSet的底层实现

**底层是基于HashMap的key来实现去重的**， map的value 就是一个final 空属性的 object对象

LinkedHashSet 如何保证元素有序， 对HashMap里的Node又包装了一层， 搞了个before, after 来记录存取的顺序

### 1.13 JDK8的新特性

- **interface新特性**
  - default 关键字， 可以规避接口中必须要重写的这点， 不必须实现的方法 用**<font color=red>default修饰 给予默认的实现</font>**
  - static关键字， 接口中 直接定义static的方法， interface.fun() 直接使用。
- **<font color=red>时间类 LocalDate/LocalDateTime</font>**
  - 支持时区 ZonedDateTime
  - **<font color=green>线程安全</font>**, 一旦创建  不可变， 适合并发场景
  - 更丰富的api
  - 需要指定序列化json的格式

```codes
@JsonSerialize(using = LocalDateTimeSerializer.class)
private LocalDateTime birthday;

# 跟 旧的Date比较
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss"
private Date date;
```



- lambda
  - 语法糖， 使用更方便， eg: 匿名内部类
- Stream
  - 链式调用， 更丰富的中间操作 map,filter,sort

### 1.14 Stream流的坑

**最大的坑 <font color=red>parallelStream 并行流</font>**

默认所有的parallel都会使用ForkJoinThreadPool线程池， 线程数是固定的， 如果没有设置这个合理的线程池数， 性能不会提升 反而会降低

**如果大量的IO密集型的操作 使用parallel并行流处理， 结果线程数没有达到IO密集的情况， 前几个任务 处理正常， 后几个任务 则需要等待前面任务执行完毕 才有线程可用，反而会让 多个请求变成了一个串行 处理的套路 **



解决办法：设置合理的线程池参数



List --> Map时 有重复数据 则会报错， 

解决办法：先 **去重**



### 1.15 JDK1.8的数据结构的优化

HashMap



**JDK1.8 加入了<font color=red>红黑树</font>  提升查询效率**。

**<font color=green>数组>=64 AND 链表>8 时， 开始使用红黑树</font>**



**链表转红黑树的阈值为什么是8?**

答案：基于统计学和工程实践的平衡考虑

- **<font color=red>泊松分布</font>统计基础**
  - 链表长度达到8的概率约为 **0.00000006** (千万分之六)
  - 链表长度达到7的概率约为 **0.0000006** (百万分之六)
- **时间复杂度平衡**
  - **链表 <font color=green>时间复杂度 O(n)</font>**
  - **红黑树 <font color=orange>时间复杂度 O(log n) </font>**
  - **在n=8时，<font color=blue>红黑树的性能优势开始明显超过链表</font>**
  - **不希望过早转换，因为<font color=red>红黑树占用更多内存</font>**





### 1.16 HashMap的put流程

- key是null，hash值固定就是0
- 不为null， **会将key.hashCode的结果，进行高低位的亦或运算，得到一个结果，就是hash值。**
- 基于 **数组长度 - 1 再跟key的hash值做&运算** ，得到要存储的数组索引位置。

- HashMap的长度必须是2的n次方？
  - 如果长度不是2的n次方，会导致在计算索引位置时，hash冲突变多，导致HashMap查询变慢。



### 1.17 项目中设计模式

单例，工厂，代理  



#### 1.17.1 **责任链模式**

- **工作原理**

1. **客户端创建一系列处理者对象，并将它们连接成一条链**
2. 请求被发送到链中的第一个处理者
3. 每个处理者决定是否处理该请求，以及是否将请求传递给链中的下一个处理者
4. 请求沿着链传递，直到被某个处理者处理或者到达链的末端

```
// 抽象处理者
abstract class Handler {
    protected Handler successor;
    
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
    
    public abstract void handleRequest(Request request);
}

// 具体处理者A
class ConcreteHandlerA extends Handler {
    public void handleRequest(Request request) {
        if (request.getType() == RequestType.TYPE_A) {
            // 处理请求
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

// 具体处理者B
class ConcreteHandlerB extends Handler {
    public void handleRequest(Request request) {
        if (request.getType() == RequestType.TYPE_B) {
            // 处理请求
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

// 客户端代码
public class Client {
    public static void main(String[] args) {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();
        
        handlerA.setSuccessor(handlerB);
        
        Request request = new Request(RequestType.TYPE_B);
        handlerA.handleRequest(request);
    }
}
```



- **应用场景**：

  - Java Servlet 中的**过滤器链** (FilterChain)

  - Spring Security 中的**安全过滤器链**

  - 日志框架中的多级日志处理器



#### 1.17.2 策略模式

- **主要组件**：
  1. **Strategy (策略接口)**: 定义所有支持的**算法**或行为的公共**<font color=orange>接口</font>**。
  2. **ConcreteStrategy (具体策略)**: **<font color=red>实现策略接口</font>**的具体算法或行为。
  3. **Context (上下文)**: **<font color=green>持有一个策略对象的引用</font>**，并通过策略接口与具体策略交互。

- **工作原理**

  1. 客户端创建一个具体策略对象

  2. 客户端创建一个上下文对象，并将具体策略对象传入

  3. 客户端通过上下文对象调用策略方法

  4. 上下文对象将调用委托给当前持有的策略对象

```
// 策略接口
interface PaymentStrategy {
    void pay(int amount);
}

// 具体策略 - 信用卡支付
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
    }
}

// 具体策略 - 支付宝支付
class AlipayPayment implements PaymentStrategy {
    private String email;
    
    public AlipayPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Alipay: " + email);
    }
}

// 上下文
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}

// 客户端代码
public class Client {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        
        // 使用信用卡支付
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(100);
        
        // 切换为支付宝支付
        cart.setPaymentStrategy(new AlipayPayment("user@example.com"));
        cart.checkout(200);
    }
}
```



- **应用场景**：

  - **支付系统**: 支持多种支付方式（信用卡、支付宝、微信支付等）

  - **排序算法**: 根据数据特点选择不同的排序算法（快速排序、归并排序等）

  - **压缩算法**: 选择不同的压缩策略（ZIP、RAR、7z等）

  - **导航系统**: 提供不同的路线策略（最快路线、最短路线、避开收费路线等）





### 1.18 ThreadLocal



- ThreadLocal本身**不存储数据**，他只是一个key

- 真正存储数据的，是线程对象**Thread当中的一个Map**。

- Map的底层是一个Entry数组，每一个Entry都可以存储key和value

- 其中的key，就是ThreadLocal

- **<font color=red>key的内存泄漏问题已被解决</font>，因为ThreadLocal内部对Key的引用是<font color=blue>弱引用</font>**

- **<font color=green>value的内存泄漏</font>，需要咱们在使用完毕后，<font color=orange>主动的remove</font>**


ThreadLocalMap 类 --- ThreadLocal的内部静态类，实际存储结构 自定义的哈希表，仅用于维护线程本地变量
- key为弱引用的ThreadLocal实例
- value为强引用的实际存储值

### 1.19 ThreadLocal如何实现主子线程之间的数据同步



- 共享变量来实现父子线程之间的数据共享
- InheritableThreadLocal





### 1.20 接口抽象类区别

- 接口**属性**都是public static final修饰的常量，抽象类就没啥限制了
- JDK1.8之后，interface增强了，其实方法的维度和抽象类区别不大，**抽象类可以声明<font color=red>抽象方法</font>，<font color=green>非抽象方法</font>**，静态方法都可以，但是interface增强后也可以，默认是抽象方法，default修饰可以写方法体，static修饰可以提供静态方法。但是interface的访问修饰符都是public

- **接口不能写静态代码块**，抽象类可以。
- 都不实例化
- **抽象类单继承**，限制大。 接口多线程，更舒服。





### 1.21 RESTful API的设计原则，并说明如何在Java中实现这些原则

**RESTful API他本质就是一个架构风格，他不是标准。可以不去遵循，也可以就遵循一部分，当然，也可以全部遵循**

- 请求的报文中携带好必要的信息
- 前端后端分离，后端不做什么请求转发，重定向这种操作
- 基于路径本身传参
- 请求方式代表不同的操作Get/Post/Put/Delete
- 不同的状态码标识不同的事情







### 1.22 Java后端架构设计的原则

架构设计原则

- **单一职责**：一个类，就专门一干一个事
- **里氏替换**：子类可以拓展父类功能，但是别影响其他功能的正确性
- **开闭原则**：功能可以加，但是不能改。
- **依赖倒置：<font color=green>面向接口编程</font>**



推荐回答：(更接地气)

- **可维护性: 后期功能迭代，不可避免，不要因为一个小的改动，导致大面积的修改代码，做到高内聚低耦合！！**
- **可扩展性**： 责任链模式 就是一种很好的可扩展实现
- **安全性**：敏感数据 加密， （AES，RSA，MD5, .... HTTPS）
- **性能**：CND，DNS优化，缓存的设计，分库分表，合理的中间件，多线程
- **容错兜底**：熔断，降级，做好兜底。 限流， MQ做削峰；规避单点故障；数据的冗余备份
- **监控**：
- **弹性伸缩**：





### 1.23 Java中的异常处理机制

- try-catch:  捕获异常的
- throw：抛出异常的
- throws：**声明方法抛出异常**
- SpringMVC 有异常处理器：全局的异常处理



* **checked**：**<font color=green>检查时异常</font>**就是在编译时期，就存在的，当咱们做一些操作时，比如IO操作，文件可能不存在，提前向上抛出IOException。

- **unchecked**：**<font color=red>运行时异常</font>**，就是程序运行后，在执行代码时，可能会出现的异常，比如NPE，索引越界等等。。



**自定义异常这，记住，一定是继承<font color=orange>RuntimeException</font>**,如果抛出的不是RuntimeException， Spring声明式事务会失效







### 1.24 Java中基本数据类型的种类及其特点, 如何选择合适的数据类型来优化内存使用

byte，short，int，long，float，double，boolean，char



eg： 年龄，一般byte够用，或者short



- **数据库层面**：一次I/O 一页数据， 占比字节更小的列， 可以存储更多的数据，**更多的数据就可以存放在MySQL的Buffer Pool中提升查询性能**。



- **主键索引类型**，还是要选择**<font color=red>long类型**，一般咱们会选择**分布式ID的雪花算法**等方式生成ID，64位的界限更长



- **金额** 贴近业务一般采用**BigDecimal**，或者是采用**Long**类型



- **不是所有操作都是内存占用小，就效率高**。比如CPU





### 1.25 变量修饰符 volatile/transient

- **<font color=red>volatile --- 共享资源的线程可见性 和 有序性</font>**
  工作内存 缓存区 共享副本的方式 JVM控制副本数据 与 主内存数据的同步， 保证了 资源的及时 可见

  - **<font color=#daaf09>可见性 --- 多线程 共享资源的可见性 </font>**
  - **<font color=#afda09>有序性 --- 防止指令重排， 懒汉式 单例模式中，双重检查锁 中的 代码块可能存在指令重排</font>**

- **<font color=green>transient --- 关键字 transient 不会被序列化</font>**

  ```java
  public class User implements Serializable {
      private String username;
      private transient String password;  // 不会被序列化
      
      // 其他代码...
  }
  ```







### 1.26 **final、finally、finalize区别**

- **final**：用于声明常量、不可重写的方法和不可继承的类。

- **finally**：用于异常处理，确保某些代码总是会执行。

- **finalize**：用于对象被垃圾回收之前的清理操作，但由于不确定性，不推荐依赖。







### 1.27 ConcurrentHashMap 如何实现线程安全

jdk1.8 之后 采用了**更加细粒度的锁和无锁机制（CAS 操作）**

- **更细粒度额锁 --- <font color=green>synchronized 和 ReentrantLock</font>**

- **CAS (compare and swap) --- 无锁的原子操作,  无锁插入和更新，减少锁竞争** 





**读操作**：读取操作大部分情况下是无锁的，因为`ConcurrentHashMap`使用了`volatile`变量和 CAS 操作来保证读取的可见性和一致性。

**写入操作**：写操作则需要在必要时使用锁或 CAS 操作来保证线程安全。

- synchronized块， 必要时对单个桶（bin）进行加锁，而不是整个段，从而进一步提高并发性

**高效的扩容**：Java 8 通过逐步迁移节点和**<font color=green>协作扩容机制</font>**，提高了扩容效率，减少了扩容过程中对性能的影响。









### 1.28 LRU （Least Recently Used） 算法 --- 最近最少使用淘汰算法

**LRU（最近最少使用）是一种经典的<font color=red>缓存淘汰算法</font>**

**基本原则**：当缓存空间不足时，**<font color=orange>优先淘汰最久未被访问的数据</font>**。

**<font color=green>每个元素 记录最近使用时间， 时间越久的越先被淘汰</font>**



### 1.28 LFU (Least Frequently Used) 算法 --- 最不常用淘汰算法

存在两个不足

- 频繁使用过的元素  时间久远，不被淘汰，浪费内存
- 新加入的元素 使用频率较低 容易被淘汰



**Redis缓存淘汰 针对这个问题 入了如下优化**

- 记录使用次数  **<font color=red>后8位存储次数， 前16位 存放时间</font>， 这样就保证了 一个数字 包含了次数和时间**
- **<font color=green>自动衰减</font>**  （**一分钟 使用次数 - 1**）



**Caffeine 缓存配置LFU**

```java
// Caffeine缓存库配置LFU
Cache<Integer, Data> cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .evictionPolicy(EvictionPolicy.LFU)  //TinyLFU 算法（默认策略）
    .build();
```



### 1.29 **Window-Tiny LFU**

- **准入策略**： 进入缓存的元素 必须是 能够提高缓存命中率的

- **基于频率**： 具有LFU的特点

- **保鲜机制**： LFU到达一定频率 就难以被淘汰， 但是 频率会随着时间边长而逐渐降低



窗口缓存（LRU）

主缓存： 淘汰段(LRU) + 保护段（LFU）





### 1.29 **linkedhashmap如何保证有序性？⭐⭐**

LinkedHashMap通过维护一个双向链表来保证有序性。这个双向链表记录了所有插入的键值对的顺序。根据构造方法中的参数设置，LinkedHashMap可以按插入顺序或访问顺序来维护这些键值对的顺序。









### 1.30 运行时异常 和 非运行时异常

- **<font color=green>Runtime Exceptions</font> --- 程序运行期间可能会发生的异常**

  - **无需显式捕获或声明**
  - **通常是业务错误**

  - **常见的运行时异常**：

    - `NullPointerException`

    - `ArrayIndexOutOfBoundsException`

    - `ClassCastException`

    - `IllegalArgumentException`



- **<font color=green>非运行时异常</font> - Exception 类及其子类**
  - **需要显式捕获或声明**：编译阶段 编译器会强制要求。
  - **通常是可预见的异常情况**
  - **常见的非运行时异常**：
    - `IOException`
    - `SQLException`
    - `FileNotFoundException`
    - `ClassNotFoundException`







### 1.31 Java是值传递还是引用传递？

- 说是值传递 也行
  - **基本类型 的值传递值 <font color=red>copy 值</font> 作为参数传递， 引用类型 是  <font color=red>copy 引用的地址值</font> 作为值 进行传递**
- 说是 值传递 + 引用传递 也行
  - 基本类型 同上，**引用类型的传递 因为值指向同一份 内存地址 传递的是引用地址 所以可以说是 引用传递。**





### 1.32 序列化介绍

作用：**将数据结构或对象状态转换为可存储或传输的格式的过程**

- 持久化存储 --- 转化位**字节流/文本** 存储在**文件/数据库**
- **跨平台/语言数据交换** --- java/python  json数据交换

- 网络传输 --- 将对象转换为可通过网络传输的格式  **RPC参数传递/微服务通信**

- **对象克隆 --- 深拷贝**









## 2. 分布式解决方案 （针对大型项目，高并发，海量数据）





### 2.1 大厂常问CDN/DNS 技术栈

**CDN --- Content Delivery Network 内容分发网络, 针对图片/音频/视频/文件等 静态资源**

<font color=red>通过将内容缓存到全球各地的边缘节点服务器，使用户能够从最近的节点获取内容，从而显著提高访问速度和用户体验。</font>



**缓存策略**

- **静态内容**：HTML/CSS/JS/图片/视频（缓存时间长）
- **动态内容**：通过动态加速技术优化（如路由优化、TCP优化）



**关键技术**

- **智能DNS解析**：根据用户位置返回最优节点IP
- **缓存分层**：边缘节点 → 区域中心 → 中心节点 → 源站
- **内容预取**：热门内容提前推送到边缘节点
- **协议优化**：QUIC/HTTP3、Brotli压缩等





CDN 数据流 图

<img src="./static/CDN_parse_flow.png"/>







### 2.2 域名结构树， IP域名与DNS解析



查看DNS跟服务器

```cmd
nslookup
set type=ns
.
com.

mashibing.com 
```



**CDN + NDS + Nginx**

1，访问一个网站时， 首先通过DNS查询获取对应的IP， 然后通过这个IP访问服务器

2，CND，通过全球部署的多个服务器上的缓存文件， 加速网页访问， CDN导向距离用户最近的服务器节点

3，Nginx，反向代理， 同时可以处理静态文件，负载不同个后端服务器， 通过负载均衡，均分流量



### 2.3Nginx反向代理 + 负载均衡





### 2.4 分流技术







### 2.5 Nginx常见负载均衡算法

- 轮询 默认
- 随机
- 权重 weight
- ip_hash --- 请求id 客户端IP取hash值
- fair 第三方, 根据后端响应时间， **响应时间越短，优先分配**
- url_hash
- 最少连接数。  需要实时监控



### 2.6 微服务中的负载均衡

Ribbon





### 2.7 海量数据分流与系统关系

应用分类

- 事务型
- 缓存型





**应用分类，数据库分类**

<img src="./static/category_app_database.png"/>



### 2.8 关系型数据库分流



分库分表

- 数据拆分
  - 垂直拆分
  - 读写分离









### 2.9 分布式场景下的多线程优化

多线程 批量处理。



### 2.10 海量数据的技术架构--核心业务



- 数据多写 ： 同时入库 + 入缓存， 并通过binlog进行**数据补偿+修正**

- 数据写入 -- 通知

  

Canal server 根据binlog做 增量同步 ， 同时增量更新到Redis



**海量数据下的 缓存机制/数据更新机制**

<img src="./static/huge-data_cache-or-mq.png"/>



### 2.11 海量数据下的 读服务细分

- 搜索类 服务
- 展示

海量数据--实时数据计算

<img src="./static/hugedata-real-calculation.png"/>







**数据宽表化**

**数据宽表化是一种<font color=red>数据建模技术</font>**，通过将多个关联的窄表合并为一个包含大量列的宽表，以优化分析查询性能。这种技术在大数据分析和数据仓库领域广泛应用。



**核心思想**

- **预关联**：提前完成表连接操作
- **预计算**：存储衍生指标减少运行时计算
- **扁平化**：消除多层嵌套结构



**典型应用场景**

1. **用户画像分析**
   - 整合基础属性、行为数据、消费特征
   - 支持复杂标签查询
2. **实时大屏展示**
   - 预聚合关键指标
   - 亚秒级响应时间
3. **AI/ML特征工程**
   - 提供一站式特征数据
   - 减少特征抽取时间
4. **自助分析平台**
   - 简化数据模型
   - 降低使用门槛











### 2.12 分布式锁



- **数据库实现 --- select ... for update -- 互斥锁**
- Zookeeper的锁 越来越少用  基于文件系统 znode(version/data) 实现
  - 使用临时顺序节点
- **<font color=red>Redis分布式锁 （最常用） </font>**set原子性实现
  - setnx
  - set key ex value nx/xx



Redisson的分布式锁 --- 写 hash + lua脚本的操作。







**幂等性**

- **MVCC 多版本并发控制** -- （实际开发中很少使用）
- 去重（去重表）



### 2.13 分布式事务

**大厂解决方案 一般使用<font color=green>RocketMQ 实现分布式事务解决方案</font>  ---  最终一致性**





**RocketMQ 死信队列**

- **重试次数耗尽**：默认16次重试（可通过`maxReconsumeTimes`配置）

- **主动投递死信**：消费代码中返回`RECONSUME_LATER`且重试次数达上限



**死信消息处理方案**

1. **人工修复后重新投递**

2. **转储到其他系统**：如数据库、Elasticsearch等

3. **自动修复流程**：编写补偿程序自动处理特定错误





### 2.14 消息聚合 消息分发

加入10w的用户的直播间

每秒1000条消息 TPS = 1000/s



合并： 合并1000条消息 合成一批上传。

**消息的分发**：MQ来聚合操作， 通过Stream处理。



**高可达的处理**: ACK确认机制， message,betch, 分布式ID, 幂等性去重， ACK确认。



其他优化方案：心跳维护， 超过时间限制， 则断开长连接。



### 



## 3 JVM核心原理常见面试题



### 3.1 JDK,JRE,JVM的关系

**包含关系： JDK 包含 JRE,  JRE 包含 JVM**



类加载流程 --- 本质上类的各种信息 加载到内存中，最终实现可以实例化对象的过程

<img src="./static/class_loading_flow.png"/>



**查找和导入class文件**

（1）通过一个类的全限定名获取定义此类的**二进制字节流**
（2）将这个字节流所代表的**静态存储结构转化为<font color=red>方法区(元数据区MetaSpace)的运行时数据结构</font>**
（3）在Java**<font color=green>堆中</font>**生成一个**代表这个类的java.lang.Class对象，作为对方法区中这些数据的<font color=orange>访问入口</font>**



**方法区：类信息，静态变量，常量**

**堆： 代表这个类的class 对象**



验证阶段： 文件格式正确定， 元数据正确性   字节码正确性   符号引用 正确性

准备阶段：**为类的静态变量分配内存，并且赋予当前类型的默认值**



**初始化阶段**： 执行Clinit方法， **<font color=orange>初始化静态变量</font>**, 初始化静态代码块



ClassLoader 是游离在JVM之外的代码片段， 用来加载class



<img src="./static/direct_memory_structure.png"/>





**方法区**

实现：

JDK1.7之前     永久代  持久代   Perm Space   类的总数    常量池大小    方法的数量

**<font color=green>JDK1.8以及其之后    元空间  元数据区    MetaSpace</font>**







### 3.2 堆为什么进行分代设计？

**保证 内存空间的连续性， 提高对象实例的 内存分配效率**



![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1677475348096/ae9089e883a945b1ac14fb636d832ba4.png)



![23.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/1463/1644577518000/0df155de2e8f429683e3faade45766e3.png)





**Full GC = Young GC + Old GC + MetaSpace GC(忽略不计)**



**对象头里 有一个 4位二进制 存储 分代年龄数据， 因此 15是 Young/Old 代的分水岭。** CMS是个特例



### 3.3 老年代的









### 3.4 对象的生命周期

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1677475348096/b860323b7bad4aa9b9a81d41d0a9b2e1.png)



![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/1463/1646137467048/a3a4286d19524b538b70f1aa2a208ba6.png)



因此， 被垃圾回收器 判定为 不可达状态 也不一定被回收。**<font color=red> finalize方法 可以重新建立连接</font>**， 重新进入应用阶段。

> Finalize.save_hook = this; # 在finalize方法中，重新建立引用



### 3.5 引用类型

- 强引用 --- 任何时候都不回收
- 软引用 --- OOM 之前回收
- 弱引用 --- 任何时候都会回收  GC 
- 虚引用 --- 跟没设置引用一样， **目的是回收时 收到一个通知**



**回收算法**

- **引用计数法， <font color=red>无法避免循环引用的问题</font>， 导致内存泄漏** --- 效率贼高

- 可达性分析算法， 解决上面的问题， 跟搜索， 单向引用链。



**垃圾回收的时机**

- eden, S区 不够用
- Old区不够用
- 方法区(MetaSpace)不够用
- System.gc();



**标记清楚算法**  

- 标记的是 存活对象



标记清除+ 整理算法 = 标记压缩  （使用两个指针 实现复制 和 内存整理 最终形成 内存连续）



业务线程  垃圾收集器线程

**STW 是停止业务线程 让垃圾收集器线程快速完成垃圾对象的回收**









### 3.6 垃圾收集器

Serial 单线程 最早的垃圾收集器  适用Young  复制算法 应用：Client模式下的默认新生代收集器

Serial Old， 是针对老年代的收集器， 单线程 **标记-整理算法**

ParNew 基于Serial 的多线程本版



**CMS**:  以 获取 **最短回收停顿时间** 为目标的收集器   **标记清除算法**

步骤：

- 找 root
- 遍历 标记
- 针对第二步 被重新引用的对象 重复标记
- 并发清除



**卡表**： 记忆集是针对跨代引用 提出的思想，卡表 是 具体实。

1个字节(8位) 的 字节数组， 数组的每一项 对应内存的某一块连续区域， 该区域 中的引用指向了待回收区域，卡表数组对应的元素被置为1 否则为0



hotSpot使用的卡页是2^9大小,即512字节



**G1**, jdk1.7补充版推出， 1.8推荐版本  1.9 默认使用的收集器

内存空间， 划分了2048个region区域 empty/eden/s/old/h(大对象区)

**一般禁止调整 新生代大小**



优先回收 重要的区域

- 分代回收
- 空间整理  --- 标记-整理
-  可预测的停顿



### 





## 4 并发编程核心原理常见面试题



### 4.1  sleep 和 wait 的区别

- **从方法角度**

  - Thread.sleep -- 目的 让直行该方法的线程 进入**TIMED_WAITING状态**

  - Object.wait --- 目的： 持有该锁的线程进入**TIME_WAITING状态**

- **sleep在进入阻塞状态时，<font color=red>不会释放锁资源</font>**
- **wait进入阻塞状态，<font color=green>会释放所资源</font>**

### 4.2 wait和notify做什么的， 为什么是object的方法



**wait方法就是将持有锁的线程，<font color=orange>封装为ObjectWaiter，扔到WaitSet中</font>等待被唤醒。**

**notify方法就是将WaitSet中<font color=blue>等待被唤醒的线程扔到EntryList中等待竞争锁资源</font>（被唤醒的线程，需要重新等待竞争锁资源）。<font color=green>notifyAll就是将WaitSet中等待池的所有线程都唤醒，都扔到EntryList中</font>。**





 **为什么他们是Object类中的方法？**
>因为咱们在执行wait和notify或者是notifyAll方法时，**<font color=red>本质都是在操作对象锁中的ObjectMonitor里提供的很多数据结构和一些AP</font>**I，这里需要对象锁中的ObjectMonitor的API，你不持有锁，没法调用它里面的API。。



### 4.3 线程安全的保证



**不安全的原因: 多个线程 <font color=magenta>共享/竞争</font> 临界资源。**



- **锁， 解决线程安全**
  - CAS, synchronized, ReentrantLock, ReentrantReadWriteLock, Redis分布式锁



### 4.4 自旋锁，CAS, 乐观悲观

1, 乐观/悲观

- **乐观**：认为没有并发情况，直接动手！看成功失败！**<font color=turquoise>不阻塞线程</font>**。CAS

- **悲观**：认为必然**有并发**，先尝试拿锁资源，再动手，拿不到锁资源就阻塞线程。lock，synchronized

2，共享/互斥

- **互斥**：只能有一个线程同时持有一把锁。 synchronized，lock， ReentrantLock
- **共享**：同一时间，可以有多个线程持有一把锁，一般是针对读写锁的实现。ReentrantReadWriteLock，StampedLock。

3， 重入/不可重入

> 同一个线程持有一把锁的时候，再次竞争这个锁资源，是可以直接获取的。

4，公平/非公平

- **新线程加入队列会尝试获取锁，则是非公平， 否则是公平锁**， 只有这一步的区别， 剩下的都在等待队列顺序等待。

5, **自旋锁和CAS？**

**自旋锁这个名词是synchronized内部的。在synchronized中有个轻量级锁的概念，他会<font color=copper>多次的执行CAS去尝试获取锁资源</font>，而这个<font color=quartz>多次CAS就被称为自旋锁</font>。**

而CAS本质在Java中是一个方法，**<font color=scarlet>Unsafe类中的一个native方法</font>**。**本质是利用CPU的指令来实现**

本质是在oldValue和当前值一样的情况下，将olaValue修改为newValue



**CAS的几个问题：**

- **ABA**：在多线程并发的情况下，要修改的值，本来不符合预期，但是修改的时候，因为其他线程的操作，导致符合预期了，就直接修改了。不是咱想要的。**<font color=orange>解决的方式也很简单，额外加一个版本好即可</font>**。而且Java中已经提供了对应的工具类，AtomicStampedReference，提供了除了值之外的额外的版本号可以指定。
  **Ps：ABA不一定是问题，就好比你的银行卡，你花了10块，又存了10块，之前钱没问题，就是ok的。**

- **自旋次数过多**：因为CAS不会挂起线程，可以一直执行，比如AtomicInteger，他的底层是一个do-while循环，一直CAS，直到成功为止，如果一直不成功，就会一直占用CPU资源，一直执行。。

- **无法保证一段代码的原子性**，想保证得封装，不过Java封装好了，synchronized，lock锁都是基于CAS实现的。



### 4.5 AQS --- AbstractQueuedSynchronizer

**java.util.concurrent.AbstractQueuedSynchronizer**  --- 抽象类



**<font color=red>JUC包下的大多数工具类都是基于AQS实现的</font>**

eg: 

- ThreadPoolExecutor，
- ReentrantLock，
- CountDownLatch，
- Semaphore……



**AQS中的三个核心内容**:

- **state属性**， **由volatile修饰，基于CAS修改** ，他是作为资源的int类型属性，比如CountDownLatch中他就是计数器中的内个数，ReentrantLock中他就是竞争锁修改的内个属性。。。。
  - **<font color=green>通过`getState()`、`setState()`和`compareAndSetState()`方法进行原子操作</font>**

- **同步队列（双向链表）**，拿不到资源的线程需要排队等，就在这个同步队列里等。**（类比EntryList）**

- **单向链表**，一般是跟锁有关的，当持有锁的线程，执行了AQS提供的Condition里的await时，要扔到这个单向链表中挂起，等待被signal唤醒。**（类比WaitSet）**





**独占模式（Exclusive Mode）**：独占模式下，**只有一个线程能获取同步状态**，其他线程必须等待。例如，ReentrantLock就是基于独占模式实现的。

**共享模式（Shared Mode）**：共享模式下，**多个线程可以同时获取同步状态**。例如，Semaphore和CountDownLatch就是基于共享模式实现的。



### 4.6 synchronized和lock的区别

- synchronized是关键字，lock是一个类(接口)。

- **synchronized就同步方法，同步代码块的使用方式，lock需要调用API**

- 性能的维度来说，他俩几乎没有什么区别
- 功能丰富的角度来说，lock更灵活，功能更丰富
- synchronized会**自动释放锁**资源，lock锁必须确保unlock要执行，最好扔fianlly里。
- **<font color=green>synchronized竞争锁是基于C++的方式</font>，利用CAS修改owner竞争锁，ReentrantLock是基于CAS修改state属性，从0改为1**。 
- synchronized 底层是 基于**Monitor** 与对象建立 关联实现的锁



**synchronized**

- 是**可重入锁**
- 是**非公平锁**
- **偏向悲观锁**



### 4.7 lock, tryLock, lockInterruptibly的区别

**都是ReentrantLock获取锁的方法**



- **lock**：**<font color=orange>拿不到锁直接排队</font>**，即便排队时期被中断了（interrupt），依然会继续排队，死等，拿不到锁，就不走了！等到拿道锁，确认是否被中断过，如果中断过，就保留中断标记位。

- **tryLock()**：**<font color=green>浅尝一下，就抢一次，抢到拿锁走人返回true，抢不到，返回false</font>**

- **tryLock(timeout,unit)**：浅尝timeout.unit时间，**最多等待timeout.unit时间**，拿到锁返回true，反之false。并且在等待过程中，如果被中断，会抛出InterruptedException。

- **lockInterruptibly**：**<font color=orange>拿不到锁直接排队，要么拿锁走人，要么被中断抛出InterruptedException。</font>**



### 4.8 synchronized, 锁升级



- **锁膨胀：** 如果设计到了**<font color=red>循环内加锁</font>，JIT可能会将加锁的代码<font color=blue>膨胀到循环外</font>，<font color=orange>避免频繁的加锁</font>，释放锁浪费资源。**

- **锁消除：** 在**没有锁竞争的时候，你加个synchronized，跟没加一样**。（看JIT）



**锁升级：**

- 无锁：在偏向锁延迟中，**New出来的对象都是无锁状态**。  （没有线程竞争呢还。）

- 匿名偏向锁：在偏向锁延迟之后，new出来的对象都是匿名偏向锁。  （没有线程竞争呢还。）

- 偏向锁：短时间内，就**一个线程反复的获取这个锁**，没有竞争，此时这个锁的状态就是偏向锁。

- 轻量级锁：在偏向锁时，出现了锁竞争，此时就会升级为轻量级锁，默认会执行10次CAS，去尝试获取锁资源。10次会变化，因为用的是自适应自旋锁，如果上次自旋成功，下次自旋次数会增加。

- 重量级锁：当轻量级锁的状态自旋后，没拿到锁资源，升级为重量级锁，重量级锁的状态下依然会执行多次CAS，拿不到锁资源才会挂起线程。



### 4.9 JMM --- **Java Memory Model，Java 内存模型**



**多线程并发编程规范**, 用于解决**线程间共享变量的可见性、有序性和原子性**问题，确保程序在不同硬件和操作系统上都能正确执行











### 4.10 volatile的作用



**volatile只能解决<font color=red>可见和有序</font>，他跟原子性没关系，<font color=green>原子性最底层的基本就是CAS</font>了。**



- **volatile怎么解决的可见性?**

**volatile在底层针对被修饰的属性操作时，会追加 <font color=red>lock前缀指令</font> ，这个指令会强制触发MESI，绕过那些所谓的优化，保证一致性。**





- **有序性怎么发生的**?

有序性其实就是  **<font color=orange>禁止指令重排</font>**  ，指令重排有俩地方会做，一个JIT，一个就是CPU。

**最经典的就是单例模式中的懒汉机制存在的问题。 比如<font color=blue>new对象过程的三个步骤的顺序被调整</font>。**



1、申请内存空间。 2、初始化属性。 3、地址引用赋值。

**重排为 ==>**

1、申请内存空间。2、地址引赋值。3、初始化属性。





- **volatile怎么解决的有序性?**



* 从指令层面上看，是利用**<font color=red>内存屏障</font>**解决的。
- 内存屏障会被不同的CPU解析成不同的函数或者指令。

- 在×86的CPU中，会将Store Load Barrier，转换为mfence的函数

- 在追踪函数后，依然会看到   **lock前缀指令** 。







### 4.11 JUC包下有哪些常见的工具

AQS

ReentrantLock.... 





### 4.12 <font color=red>那里用到了多线程</font>

Springboot 提供的注解

@Async  --- **<font color=red>独线程池 线程数默认8个， 等待队列无限长度Integer.MAX_VALUE</font>**

@Scheduled --- **定时任务** 独立线程池，**<font color=blue>默认只有一个线程</font>，<font color=green> 理论上 多个@Schedule任务时  串行执行</font>**



```codes
   @Configuration
   public class TaskConfig  implements SchedulingConfigurer {
       @Override
       public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
           // 自己提供即可。。。 自定义线程池
       }
   }
```





适用场景：

- **多次查询数据库或者三方服务的业务，可以基于线程池并行去查询三方以及数据库，减少网络IO带来的时间成本**。。。

- **比较大的数据，或者文件之类的东西，单个线程处理速度太慢了，可以将这种文件或者数据做好合理的切分，让多个线程并行去处理，最终汇总即可。**





### 4.13 线程池参数

- 核心数
- 工作队列
- 最大线程数

- 拒绝策略

- 最大空闲时间
- 空闲时间单位
- 线程工厂



### 4.14 提交任务到线程池的细节



- 尝试创建核心线程去处理任务
- **核心线程数达到了要求，就将任务扔到工作队列。** <font color=red>该阶段 不关心线程是否时空闲</font> 监听队列->获取任务 ->执行任务
- **<font color=orange>工作队列放满了，就会创建非核心线程去处理任务</font>**。
- 工作线程数，达到最大线程数了，执行拒绝策略。



**线程池里区分核心与非核心线程吗？  <font color=green>创建的时候，区分，干上活之后，不区分</font>。**

**线程池里关心工作线程是否空闲吗？  <font color=red>不关心，他只关心数</font>！**



**<font color=red>为什么核心满了，不去创建最大线程数，而是扔到队列后，才考虑创建非核心线程？</font>**

1. 将任务扔到队列的目的是为了**缓冲**，由现在的线程去处理任务，如果上来直接额外创建非核心线程，那核心跟非核心的意义就不大了。浪费资源，多线程了



**<font color=red>为什么非核心线程创建的时候，要优先执行投递过来的任务，而不是执行队列中任务？</font>**

1. 投递任务到线程池的目的**为了走异步**，更快的处理后续的业务，上述这个方式可以让异步的响应速度很快。
2. 如果是先处理队列的任务，那就需要先完成线程的创建，并且启动，然后从工作队列中获取任务，然后你的新任务才能投递到工作队列。
3. 反之，如果是直接由非核心线程处理， 执行到线程的创建和启动就结束了。



### 4.15 核心线程1个正在工作， 最大线程数2， 来任务想直接创建线程



1，队列长度为0

2，队列可以使用SynchronousQueue --- 

​	有线程才会把任务放进队列

​	如果没有线程**投递失败**





### 4.16 工作线程执行任务时， 抛出异常， 工作线程会被销毁吗？



**给线程池投递任务的方式有几种**？

- execute，投递Runnable的任务
- submit，投递Callable的任务（也可以投递Runnable）



**execute投递的Runnable任务，<font color=red>异常会直接抛出</font>，基于runWorker方法抛出，抛到Worker类的run方法，run方法会异常结束，run方法结束，Worker线程销毁。**



**submit投递的任务，当出现异常时，Future会将任务的异常保留在Future内部，<font color=red>不会抛出</font>，在你基于Future去<font color=green>get的时候，异常才会catch到</font>。异常是保留的FutureTask里面的outcome属性中。**









### 4.17 线程池队列有哪些？



**首先线程池要求提供的是阻塞队列，也就是<font color=orange>BlockingQueue</font>的实现**



- **ArrayBlockingQueue：底层数组，定长**

- **LinkedBlockingQueue：底层链表，也可以定长，也可以不定长**

- PriorityBlockingQueue：底层是数组实现的二叉堆，一般用于定时处理。

- SynchronousQueue：不存储任务，直接以匹配的方式。

- DelayQueue：底层也是二叉堆，是PriorityBlockingQueue的二次封装。





**常用的就是ArrayBlockingQueue和LinkedBlockingQueue，而我们要求就使用<font color=red>LinkedBlockingQueue</font>，因为线程池中的<font color=blue>队列存放的任务会进进出出，增删多，那链表结构更合适</font>。**







### 4.18 设计一个异步任务执行框架，如何利用多线程来执行耗时的任务，并在任务完成后通知主线程

**已有的方案：<font color=red>CompletableFuture</font>**



1、优先构建好Java提供的线程池，ThreadPoolExecutor

2、主动通知主线程, 回调函数，至少要提供成功与失败的方法。 onComplete，onError

3、提供一个提交任务的方法，这个方法至少两个参数，一个是具体的任务Runnable，一个回调Callback

4、执行超时

5、任务取消



### 4.19 设计一个后台任务调度系统，如何利用多线程来并行执行多个定时任务，提高任务的处理效率

开源框架非常多

单体: Quartz， 微服务：Elastic-Job，XXL-Job



1, 定时任务框架要支持cron表达式

2, 线程池，ThreadPoolExecutor

3, 基于cron的定时任务，单纯的Runnable没有办法支撑。自己要封装一个任务类

4, 定时任务框架，任务要以周期性执行，需要有一个容器可以把任务存储起来，而且涉及到多个任务，所以需要一个线程安全的容器，直接上ConcurrentHashMap

5, 添加上**移除任务**和**添加任务**的功能，**暂停任务**和**启动任务**的功能，还有**立即执行**

6, 定时任务框架开始的时候，就直接要在一个**<font color=green>死循环里遍历ConcurrentHashMap中的任务，查询每一个任务是否现在可以执行，可以就直接执行</font>，不可以就拉倒。直到遍历结束，再开启下一次循环**



```codes
<dependency>
   <groupId>com.cronutils</groupId>
   <artifactId>cron-utils</artifactId>
   <version>9.2.0</version>
</dependency>

```







### 4.20 描述如何使用多线程来处理大数据集，例如在数据分析应用中，将数据拆分成多个部分并行处理



**线程池 + CountDownLatch就可以解决**



**<font color=red>核心思路自然就将大数据集拆分成多个部分，分别交给线程池去处理</font>，等到所有任务才完成后，汇总即可。**



**利用CompletableFuture做好任务的编排工作**







### 4.21 描述如何实现一个网络爬虫，利用多线程并行抓取多个网页，提高数据抓取的效率



1， 获取对应的URL

2， 获取对应的响应信息

3， 利用Jsoup提供的API对数据进行解析

4， 解析数据，落库



多个线程并行去访问URL

利用Redis或者本地的CHM，确保访问一次即可，减少重复访问的问题。

多个线程并行的利用Jsoup去解析

批量入库





### 4.22 描述如何在Java应用中使用多线程执行数据库的批量插入或更新操作，以提高数据库的写入效率



1， 根据数据集大小来合理的划分任务

2， 准备线程池，多线程批量插入

3， 做好压测







**建议事务的隔离级别，上<font color=red>read committed</font>。可以规避掉好多修改和插入并行的死锁问题**









### 4.23 线程的创建方式

- Thread
- Runnable
- 实现Callable接口创建线程 + FutureTask管理返回结果
- 线程池 --- ExecutorService 来创建和管理线程池









### 4.24 线程优先级

**线程优先级是对线程调度器的一种建议**，调度器会根据优先级来决定哪个线程应该优先执行。然而，线程优先级**并不能保证线程一定会按照优先级顺序执行**，**<font color=red>具体的调度行为依赖于操作系统的线程调度策略。</font>**



setPriority(int newPriority) --- 设置 优先级  [1-10]  **数值越大 优先级越高**







### 4.25 线程的基本方法

- start() --- **启动创建的新线程**  等待系统线程调度器 执行， -> run()
- run() --- 线程执行的代码
- sleep(long millis) --- 使当前线程**休眠**指定的毫秒数
- join() --- **等待线程终止**  等待被调用线程执行完毕后再继续执行。
- interrupt() --- 中断线程
- isInterrupted() --- 检查线程是否被中断  返回boolean
- setPriority --- 设置优先级 1-10,  默认5
- setName(String name) --- **设置线程名称**

- yield() --- **通知调度器当前线程愿意放弃 CPU 使用权**, 只是一个提示，操作系统可以选择忽略这个提示。





### 4.26 线程的终止方式

- 自行终止  --- 执行完毕
- 标志位 ---  类似自行终止
- interrupt() --- 线程需要在合适的地方检查是否被中断
- Future的取消方法cancel()  --- 如果是使用线程池 创建和管理线程







### 4.27 线程的安全三大特性

- 原子性（Atomicity） --- **同步块**或**锁**（如ReentrantLock） 或者 **原子类**（如AtomicInteger、AtomicLong等）
- 可见性（Visibility）--- volatile， **同步块**或**锁**
- 有序性（Ordering）--- volatile（禁止指令重排）， **同步块**或**锁**





### 4.28 Volatile 是如何防止指令重排？

**主要依赖于内存屏障（Memory Barriers，也称为内存栅栏）**。内存屏障是一种指令，用于限制处理器和编译器对指令的重排序行为。

**读屏障**

**在每个volatile读操作的后面插入一个loadload屏障，禁止处理器把上面的volatile读与下面的普通读重排序。**

**在每个volatile读操作的后面插入一个loadstore屏障，禁止处理器把上面的volatile读与下面的普通写重排序。**

**写屏障**

**在每个volatile写操作的前面插入一个storestore屏障，可以保证在volatile写之前，其前面的所有普通写操作都已经刷新到主内存中。**

**在每个volatile写操作的后面插入一个storeload屏障，作用是避免volatile写与后面可能有的volatile读/写操作重排）**







### 4.29 synchronized解锁后是如何唤醒其它正在阻塞的线程的？

线程退出一个 synchronized 块或方法时，它会释放持有的锁。**<font color=red>释放锁的操作会触发 JVM 去唤醒等待该锁的线程。具体的唤醒机制由 JVM 实现决定</font>**，JVM 会选择一个或多个等待该锁的线程并将其唤醒，使其能够**重新竞争**锁的获取。







### 4.30 DCL为什么需要volatile来修饰

```java
public class LazySingleton {
    // volatile 防止指令重排， 保证顺序
    private static volatile LazySingleton lazySingleton;
    
    private LazySingleton() {
        
    }
    public static LazySingleton getLazySingleton() {
        if (null == lazySingleton) {
            synchronized(LazySingleton.class) {
                if (null == lazySingleton) {
                    // 这条语句 分为多条 指令， 因此 存在 线程 安全性
                    lazySingleton = new LazySingleton();
                }
            }
        }
        return lazySingleton;
    }
}
```









## 5 MySQL



### 5.1 Mysql的体系结构是什么样子的（一条查询语句它到底是怎么执行的？）



**连接的本质时 TCP长链接**



**查看维护连接的线程**

```
show global status like '%Threads%';

show global variables  like 'wait_timeout'; -- 非交互式超时时间(s), eg: JDBC
show global variables like 'interactive_timeout'; -- 交互式超时时间(s), eg: 数据库工具
```



最大连接数

```sql
show global variables  like '%max_connections%'; -- 最大连接数 默认151个连接
```



**一个链接大概占用 8MB, 因此可以根据物理机内存 设置最大连接数**



SQL执行流程



词法解析 -> 语法分析 -> 语义解析 -> 预处理器 ->



查询优化器做了什么

- 优化
- 



### 5.2 一条更新语句要经历那些流程

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1644586172000/01e9d3d2bfbd4910b208df11be7920ff.png)



1， 查询

2，将查询的数据 修改 并放入 buffer pool

3，写入redo log

4，写入bin log

5，commit

6，redo log里将这个事务标记为commit状态





### 5.3 为什么Mysql要使用B+树做为索引     B树

1. **B+树能<font color=red>显著减少IO次数，提高效率</font>**
2. B+树的查询效率更加稳定，因为数据放在叶子节点
3. B+树能提高范围查询的效率，因为叶子节点指向下一个叶子节点
4. B+树采取顺序读





### 5.4 磁盘的顺序读以及随机读有什么区别？

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1644586172000/6234e5f591114a659a9994346c990a9b.png)



顺序读 固然 性能高于随机读



**单次的IO时间 =   寻道时间 + 旋转延迟 + 传送时间**











### 5.5 慢查询

```
-- SQL
SELECT * FROM orders
WHERE user_id = 1001
ORDER BY order_time DESC
LIMIT 100000, 20; -- 翻页到第5000页时需跳过10万条记录
```

**EXPLAIN 结果显示：<font color=green>type=ALL</font>（全表扫描），Extra=Using filesort（文件排序）。**

1， 添加联合索引 

**优化SQL写法（延迟关联）** ：

   ```
   SELECT o.* 
   FROM orders o
   JOIN (
     SELECT id 
     FROM orders 
     WHERE user_id = 1001
     ORDER BY order_time DESC
     LIMIT 100000, 20
   ) AS tmp ON o.id = tmp.id;
   ```

**延迟关联<font color=red>先定位 主键， 再通过主键回表  减少数据扫描</font>**





**分页优化（游标分页）** ：

<font color=orange>记录上一页最后一条记录的order_time和id</font>

   ```
   -- 记录上一页最后一条记录的order_time和id
   SELECT * FROM orders
   WHERE user_id = 1001
     AND (order_time < '2023-10-01 12:00:00' OR (order_time = '2023-10-01 12:00:00' AND id < 12345))
   ORDER BY order_time DESC, id DESC
   LIMIT 20;
   ```





### 5.6 索引失效-like '%xxx%'

**索引失效原因** ：

   * **<font color=red>左模糊匹配</font>** ：`LIKE '%小明%'` 导致索引失效（B+树无法利用前缀匹配）。
   * **排序与索引不匹配** ：`ORDER BY created_at` 未在索引中，触发文件排序（`Using filesort`）。
   * **回表开销** ：即使使用索引，仍需回表查询所有字段。
   * **<font color=red>索引上使用函数</font> year(), price*2, concat(index,"aa")**
   * **<font color=red>OR条件使用不当</font> where indexColumn = 'a' or content = 'bbb'**
   * **<font color=red>NOT IN, <>, !=操作符</font>**



**优化方案**

1. **使用全文索引:**

   ```
   -- 添加全文索引
   ALTER TABLE users ADD FULLTEXT INDEX ft_nickname (nickname);
   
   -- 优化后SQL
   SELECT * FROM users 
   WHERE MATCH(nickname) AGAINST('+小明' IN BOOLEAN MODE)
   ORDER BY created_at DESC 
   LIMIT 20;
   ```

   * **优势** ：全文索引支持任意位置的文本匹配，避免左模糊问题。
   * **限制** ：需处理停用词，且中文需配合分词插件（如ngram）。

2.  **覆盖索引优化（若无法使用全文索引）**

```
 -- 新增联合索引（覆盖查询和排序字段）
   ALTER TABLE users ADD INDEX idx_nickname_created_at (nickname, created_at);

   -- 强制右模糊查询
   SELECT * FROM users 
   WHERE nickname LIKE '小明%'  -- 仅右模糊可利用B+树索引
   ORDER BY created_at DESC 
   LIMIT 20;
```







### 5.7 雪花算法与MySQL结合出现的问题



1. **主键索引页频繁分裂**

**问题分析**
雪花 ID 在同一毫秒内局部乱序（如不同节点生成的 ID 交叉插入），导致主键索引页频繁分裂，写入性能下降。

**解决方案**

* **主键改用自增 ID + 冗余雪花 ID**
  <font color=red>保留雪花 ID 作为业务唯一标识（如订单号），但主键改用 MySQL 自增 ID，确保物理写入顺序性</font>。

  ```
  CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 自增主键，保证物理有序
    order_no BIGINT UNSIGNED NOT NULL,    -- 雪花算法生成的业务 ID
    user_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL,
    INDEX idx_order_no (order_no),
    INDEX idx_create_time (create_time)
  ) ENGINE=InnoDB;
  ```

**效果**

* 写入吞吐量提升  **40%** （索引分裂减少）。
* 业务层仍可通过 `order_no` 保证分布式唯一性。





### 5.8 事务并发会带来哪些问题?



- **脏读** --- **<font color=red>同一个事务中 两次相同的查询 结果不同</font>** (第二个事务中的修改操作 尚未commit)

- **不可重复读** --- **<font color=green>同一个事务中 两次相同的查询 结果不同</font>** (第二个事务中的修改操作 已commit)

- **幻读** --- **<font color=orange>一个事务前后两次读取数据数据不一致，是由于其他事务插入数据造成的，这种情况我们把它叫做幻读。</font>**  **<font color=red>其他事务insert  读多了数据</font>**



解决方案：

- LBCC --- 锁

- MVCC --- 版本号 控制







### 5.9 数据库死锁怎么办，你会怎么处理？



**一、常见死锁场景**

1. **交叉资源访问顺序**
   * **场景** ：事务A先锁资源X，再请求资源Y；事务B先锁资源Y，再请求资源X。
   * **原因** ：事务对资源的加锁顺序不一致。
2. **长事务占用资源**
   * **场景** ：事务A长时间持有锁未提交，事务B等待该锁时自身持有的锁也被其他事务等待。
   * **原因** ：未及时提交/回滚事务，导致锁长期占用。
3. **批量操作锁升级**
   * **场景** ：批量更新/删除未走索引，导致行锁升级为表锁，阻塞其他事务。
   * **原因** ：索引缺失或SQL优化不足，引发全表扫描。
4. **间隙锁（Gap Lock）冲突**
   * **场景** ：事务A锁定某范围的间隙，事务B尝试插入该范围内的数据。
   * **常见于** ：MySQL的 `REPEATABLE READ`隔离级别。



**二、死锁处理方案**

**1. 自动处理机制**

* **死锁检测**
  数据库（如InnoDB）自动检测死锁，强制回滚代价较小的事务。

  ```
  SHOW ENGINE INNODB STATUS; -- 查看MySQL死锁日志
  ```
* **锁超时设置**
  <font color=red>设置事务等待锁的超时时间，超时后自动回滚。</font>

  ```
  SET innodb_lock_wait_timeout = 30; -- MySQL设置锁超时时间（秒）
  ```



 **2. 代码与设计优化**

* **<font color=green>统一资源访问顺序</font>**
  确保所有事务按固定顺序访问资源（如按主键排序更新）。
* **短事务原则**
  减少事务执行时间，尽快提交或回滚，避免长事务占用锁。
* **优化SQL与索引**
  * 为查询条件添加索引，避免全表扫描。
  * 避免批量操作锁大量数据，分批处理。
  * 使用 `SELECT ... FOR UPDATE`时明确指定索引。
* **降低隔离级别**
  使用 `READ COMMITTED`代替 `REPEATABLE READ`，减少间隙锁的使用（需权衡数据一致性）。



**3. 程序容错机制**

* **重试策略**
  捕获死锁异常（如MySQL的 `1213`错误码），在代码层重试事务。

  

 **三、排查工具**

1. **数据库日志**
   * MySQL：`SHOW ENGINE INNODB STATUS`
   * PostgreSQL：`pg_locks`视图 + `deadlock_timeout`参数
2. **监控工具**
   * Prometheus + Grafana监控锁等待。
   * 第三方工具（如Percona Toolkit、pt-deadlock-logger）。



**总结**

**<font color=red>预防死锁的核心是规范资源访问顺序、优化事务设计</font>。处理时优先依赖数据库的自动回滚机制，并通过日志分析根因。在代码层添加重试逻辑和监控，可显著降低死锁对业务的影响。**





### 5.10 MySQL 事务的四大特性

- **原子性（Atomicity）** ---  undo log来保证， 成功 commit, 失败 rollback
- **一致性（consistent）** --- 业务层保证
- **隔离性（isolation） --- 事务的隔离性是通过读写锁+MVCC来实现的 **  隔离性 事物的隔离，4个隔离级别， RR, RC RU, S
  - MVCC 解决了 脏读和不可重复读的问题

  - 行锁/表锁 解决了 幻读的问题
- **持久性（Durable）** --- 持久性 Buffer pool 缓存区优化， redo_log 文件保证持久化修改





### 5.11 MyISAM 与 InnoDB 的区别？

- MySQL 5.5 之前使用MyISAM 作为存储引擎， V5.5.5 及之后 使用 InnoDB作为存储引擎

- 文件存储方式不同， 
  - MyISAM 数据文件 和 Index 索引文件分开存储
  - InnoDB B+数的结构 存储在一个文件中
- MyISAM 只支持表锁， InnoDB 支持表锁/行锁/也锁/间隙锁等
- MyISAM 不支持事务， InnoDB 支持ACID 事务
- MyISAM不支持 外键， InnoDB 支持外键







### 5.12 MySQL 如何实现数据同步到ES中

- 双写 --- 业务代码 实现 写入mysql一份  同时 写入ES 一份，  耦合度高
- MQ --- 解耦，削峰，最终一致
- binlog日志监听 --- Canal组件监听 处理数据









### 5.13 MySQL中事物的实现原理？ACID

**<font color=red>保证数据的ACID特性 即可</font>**

1. A --- **Atomic** 原子性  --- undo log来保证， 成功 commit, 失败 rollback

2. C --- **Consistence** 一致性   --- 业务层保证

3. I --- **Isolation** 隔离性 事物的隔离，4个隔离级别， RR, RC RU, S

   - MVCC 解决了 脏读和不可重复读的问题

   - 行锁/表锁 解决了 幻读的问题

4. D --- **Durability** 持久性 Buffer pool 缓存区优化， redo_log 文件保证持久化修改







### 5.14 表字段设计原则

- **更小的通常更好**，通常**更快**，**占用更少的磁盘、内存和CPU缓存**

- 类型的操作需要更少的CPU周期。例如，**整型比字符操作代价更低**，比如应该使用MySQL内建的类型而不是字符串来存储日期和时间。

- **尽量避免NULL**，NULL对MySQL来说更难优化。**当可为NULL的列被索引时，每个索引记录需要一个额外的字节**。





### 5.15 脏读和幻读问题



1， **脏读**---- 一个事务读取了另一个**未提交事务**修改过的数据。未提交的事务最终被回滚（Rollback），数据无效。

解决方案：

-  **Read Committed**  ---  高一致
- **锁   共享锁/排他锁  防止其他事务读取未提交的数据。**
- **乐观锁  --- 添加 version 字段**， update * set *** where version = 5;

2，**幻读** --- **同一个事务内**，连续执行**两次相同的查询，第二次看到的"幻影行"（新插入的行）**

解决方案：

- **Serializable隔离界别**  --- 性能代价高   **5.7及以下**
- **RR + 间隙锁**    select **** form update   **8.0+**













## 6 Redis面试场景题



### 6.1 Redis管道技术（Pipeline）

通信的优化方式，它允许客户端一次性发送多个命令到服务器，而无需等待每个命令的响应，最后再一次性读取所有响应结果。









### 6.1





## 7 MQ



### 7.1 RabbitMQ的消息处理模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1741771732097/4f590d5614994010b10b50df6696625f.png)





RabbitMQ 是一个基于 **AMQP（Advanced Message Queuing Protocol）** 协议的消息队列系统

1. **生产者（Producer）**
2. **交换器（Exchange）**
3. **路由键（Routing Key）**
4. **队列（Queue）**
5. **消费者（Consumer）**
6. **生产者（Producer）**



 **交换器（Exchange）**

交换器是消息的路由中心，负责接收生产者发送的消息，并根据 **路由键（Routing Key）** 和 **绑定规则（Binding）** 将消息分发到一个或多个队列。

* **类型** ：RabbitMQ 支持多种交换器类型，每种类型有不同的路由规则：

  * **Direct Exchange** ：精确匹配路由键。
  * **Topic Exchange** ：基于通配符匹配路由键。
  * **Fanout Exchange** ：广播消息到所有绑定的队列。



**路由键（Routing Key）**

**<font color=red>用于决定消息如何从交换器路由到队列</font>**

* **作用** ：交换器根据路由键和绑定规则**将消息分发到队列**。
* **匹配规则** ：不同的交换器类型对路由键的匹配方式不同。



**队列（Queue）**

队列是消息的**<font color=orange>存储容器</font>**，用于存储从交换器路由过来的消息，直到消费者处理它们。





#### 7.1.1 延时消息

1. **消息设置TTL** (Time To Live) --- 生产者发送 带过期时间的消息
2. **<font color=red>消息过期后 进入死信队列</font>**
3. **<font color=green>消费者从死信队列获取消息</font>**



### 7.2 Kafka的消息处理模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1741771732097/1e0c8c3c62c141e99722484d42d524eb.png)

Kafka 是一个分布式的流处理平台，其消息处理模型基于  **发布-订阅模式** ，核心组件包括  **生产者（Producer）** 、 **Kafka 集群（Brokers）** 、 **主题（Topic）** 、**分区（Partition）** 和 **消费者（Consumer）**



1. **生产者发送消息** ：

   * 生产者将消息发送到  **主题 A** 。
   * 根据消息的键（Key）或轮询策略，消息被分配到 **分区 0** 或  **分区 1** 。
2. **消息存储** ：

   * 如果消息被分配到  **分区 0** ，它会被存储在 **Broker1** 上（因为 **分区 0** 的首领在 **Broker1** 上）。
   * 如果消息被分配到  **分区 1** ，它会被存储在 **Broker2** 上（因为 **分区 1** 的首领在 **Broker2** 上）。
3. **消费者消费消息** ：

   * 消费者组中的消费者订阅  **主题 A** 。
   * 消费者 1 从  **分区 0** （位于  **Broker1** ）读取消息。
   * 消费者 2 从  **分区 1** （位于  **Broker2** ）读取消息。
4. **并行处理** ：

   * 由于 **分区 0** 和 **分区 1** 位于不同的 Broker 上，消费者可以并行处理消息，提高吞吐量。





#### 7.2.1 延时消息

- 基于时间分区的方案（最常用）

**实现原理**

1. 创建多个分区对应不同的延时时间区间
2. 生产者根据消息的延时需求发送到对应分区
3. 消费者按分区顺序消费，实现近似延时





### 7.3 RocketMQ的消息处理模型

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1741771732097/bd46b03127ce4ccabbdcecc0c8ce6f9e.png)

RocketMQ 是一个分布式消息中间件，其消息处理模型基于  **发布-订阅模式** ，核心组件包括  **生产者（Producer）** 、 **Broker** 、 **NameServer** 、**主题（Topic）、队列** 和 **消费者（Consumer）**

NameServer 是 RocketMQ 的轻量级服务发现组件，负责管理 Broker 的路由信息。

Broker 是 RocketMQ 的消息存储和转发节点，负责存储消息、处理生产者和消费者的请求。

**主题（Topic）**

* 主题可以创建在一台 Master 上，也可以创建在多台 Master 上以提高并发能力。
* 如果主题创建在多台 Master 上，消息会被均匀分布到不同的 Master。

**消息处理流程**

1. **主题创建** ：
   * **主题 A** 可以创建在 **Master1** 上，也可以同时创建在 **Master1** 和 **Master2** 上。
   * 如果创建在 **Master1** 和 **Master2** 上，消息会被均匀分布到两个 Master，提高并发能力。
2. **生产者发送消息** ：
   * 生产者将消息发送到  **主题 A** 。
   * 如果 **主题 A** 创建在 **Master1** 和 **Master2** 上，消息会被均匀分布到 **Master1** 和  **Master2** 。
3. **消息存储** ：
   * 消息被存储在 **Master1** 或 **Master2** 的队列中。
   * **Slave1** 和 **Slave2** 分别从 **Master1** 和 **Master2** 同步数据，提供数据备份。
4. **消费者消费消息** ：
   * 消费者从 **Master1** 或 **Master2** 的队列中拉取消息。
   * 如果 **Master1** 阻塞或宕机，消费者可以从 **Slave1** 消费消息。
   * 如果 **Master2** 阻塞或宕机，消费者可以从 **Slave2** 消费消息。







### 7.4 如何在MQ中实现消息的顺序性，分析相关的设计与实现细节！

为了保证消息的顺序性，通常需要遵循以下规则：

* **单线程生产** ：确保**<font color=red>生产者以单线程方式发送消息</font>，避免并发发送导致消息乱序。**
* **单线程消费** ：**确保<font color=green>消费者以单线程方式消费消息</font>，避免并发消费导致消息乱序。**
* **单个队列** ：**将所有消息<font color=blue>发送到同一个队列中</font>，确保消息的顺序性。**
* **单个生产者/消费者** ：避免多个生产者或消费者同时操作同一个队列，导致消息顺序混乱。







### 7.5 RocketMQ：延时消息

RocketMQ原生支持延迟消息，可以直接设置消息的延迟级别来实现订单超时处理。在RocketMQ5的版本中可以设置任意的延迟时间。

```
// 设置延迟级别，3对应10分钟，4对应30分钟
        msg.setDelayTimeLevel(4);
```

* 在RocketMQ 5.x中，发送消息时可以通过 `setDelayTimeMs`方法设置任意的延迟时间（以毫秒为单位）。
* 例如，设置延迟30分钟，可以将延迟时间设置为 `30 * 60 * 1000`毫秒。

  ![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/5983/1741771732097/5fb71145309f471986c48594e3505c28.png)

```
 Message message = provider.newMessageBuilder()
                .setTopic("order_topic")
                .setBody(body)
                .setDelayTimeMs(30 * 60 * 1000) // 设置延迟时间（30分钟）
                .build();
```









### 7.6 在支付系统中，如何利用MQ处理支付请求，确保支付的可靠性和事务的一致性

<img src="./static/mq_transaction_impl.png"/>

其他的细讲见代码



**MQ中流量控制的实现，包括限流策略和流量监控，分析源码中相关的实现逻辑**



#### **RabbitMQ**

通过设置消费者的预取数量（prefetch count），可以限制消费者从队列中拉取的消息数量，从而控制消费速率。

```
 @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置 prefetchCount，限制消费者每次从队列中拉取的消息数量
        factory.setPrefetchCount(10); // 每次最多拉取 10 条消息
        return factory;
    }

```







**回查** --- LocalTransactionState checkLocalTransaction(MessageExt messageExt)







### 7.7 MQ实现限流



1， **RabbitMQ**

通过设置消费者的预取数量（prefetch count），可以限制消费者从队列中拉取的消息数量，从而控制消费速率。

```
 @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置 prefetchCount，限制消费者每次从队列中拉取的消息数量
        factory.setPrefetchCount(10); // 每次最多拉取 10 条消息
        return factory;
    }

```



2， **Kafka**

- 生产者

  通过配置 `producer.properties` 中的 `max.in.flight.requests.per.connection` 和 `linger.ms` 参数，控制生产者的发送速率。

  ```
         // 设置 max.in.flight.requests.per.connection
          props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
          // 设置 linger.ms
          props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
  ```

  `max.in.flight.requests.per.connection`：**<font color=red>控制每个连接上未确认的请求数量。</font>**





















## 8 架构



### 8.1  什么是4A架构

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/1463/1742458726022/15d636cdb1a74916a4c9cea134dd8aa6.png)



- **业务架构（Business Architecture）**

  - 京东   电商  --  自营电商     电商-----仓储----物流 ---售后   （）

  - 淘宝   电商  --  B-C  让天下没有难做的生意      交易的保证        菜鸟网络
  - **输出：<font color=red>业务流程图</font>**

- **应用架构（Application Architecture）**
  - 服务拆分     架构分层   可拓展性  定义业务的边界     订单的模块的业务边界
  - **输出：<font color=green>应用组件图、服务划分、接口规范</font>**

- **数据架构（Data Architecture）**
  - 数据的存储   数据流  业务流

  - 用户token---解析用户id-----商品的详情信息+token ------商品id ----- 生成临时订单id---临时订单的id返回-----主订单库               数据模型 （对象）     临时对象   永久对象
  - **输出：<font color=orange>数据流图</font>**

- **技术架构（Technology Architecture）**
  - 选择合适的技术平台和基础设施
  - **输出：<font color=blue>技术栈选型（技术架构图）、部署拓扑（网络拓扑图）</font>**





### 8.2 四大架构的本质区别

| **架构类型** | **核心问题**         | **设计目标**                   | **典型输出物**                                               |
| ------------ | -------------------- | ------------------------------ | ------------------------------------------------------------ |
| **业务架构** | 业务如何运转？       | 对齐战略，优化业务流程         | **业务流程图**、价值链模型、领域模型                         |
| **应用架构** | 系统如何分工协作？   | 支撑业务流程，定义应用边界     | 应用组件图、服务划分、**接口规范**                           |
| **数据架构** | 数据如何流动与存储？ | 确保数据可用、可信、可分析     | 数据模型、**数据流图**、数据治理规范                         |
| **技术架构** | 技术如何实现需求？   | 保障系统高性能、高可用、可扩展 | 技术栈选型（**技术架构图**）、部署拓扑（网络拓扑图）、容灾方案 |







### 8.3 微服务的五大核心组件，三大配件，一般都会用到哪些组件

**五大核心组件            服务治理**

**注册中心**     Eureka     AP 高可用        **Nacos**   Consul   ZK      CP     服务治理的八大功能

**配置中心**      **apollo   Nacos**    disconf     config   配置是如何推送到服务端的       是不是实时生效的      权限

**网关**             过滤器链        zuul      **gataway**

**声明式远程调用**            Fegin    **OpenFegin**

**负载均衡**                 Ribbon       **loadbanlance**





**三大配件**

**链路追踪 **                     用户 20W ---  首页  15W------商品详情页  25W---------购物车  20W ----订单  2W----库存  5W    ---   支付   1W           1W的并发

zipkin    **skywalking**    Cat    pingpoint

**日志监控告警**            Elk（偏开发）                  Prometheus  + Grafana（偏运维）

**断路器**           双11    618   电商  主动的降低并发    关键的节点    限流的操作   100    100订单

**Sentinel**      [Hystrix](https://my.oschina.net/7001/blog/1619842)s               20台服务器







## 9 核心框架源码常见问题





### 9.1 MyBatis中#与$的区别

- **预编译处理（PreparedStatement）**
  - 是基于占位符的方式，将参数注入到SQL语句的指定位置。完成预编译的效果，传入的参数不会被解析为关键字，就是一个字符串，**<font color=red>可以预防SQL</font>**。

- **$字符串的拼接（Statement）**

  -  **<font color=green>$就是字符串的拼接</font>**，他不会做预编译，So，$$传参时，无法预防SQL注入的问题。

  - 在一些特殊的情况下，需要传入一些**<font color=orange>表名，字段之类</font>**的内容，这些就不能去做预编译的处理，所以在传入字段，表名之类的内容时，一定要用$传参。

   ```
     select xxx from ${table} order by ${column}





### 9.2 MyBatis中的一级缓存和二级缓存的区别

**1、本质的实现流程区别**

- 首先一级缓存是基于BaseExecutor去查询一个PerpetualCache中的HashMap得到的缓存结果。

- 二级缓存是基于CachingExecutor去查询一个PerpetualCache中的HashMap得到的缓存结果。

- Ps：虽然都是PerpetualCache，但是  不是一个对象！



 **2、查询Cache的区别**
- 一级缓存，**<font color=red>他只去查询PerpetualCache</font>**，不涉及其他的Cache实例。 **<font color=red>默认开启</font>**

- 二级缓存，**<font color=green>他会经历很多个Cache</font>**，最后才会到PerpetualCache中查询数据。 **<font color=red>默认关闭</font>**

  - SynchronizedCache：加锁，确保线程安全

  - SerializedCache：对数据做序列化和反序列化的操作

  - LoggingCache：记录缓存命中率的日志。

  - LruCache：基于Lru删除最近最少使用的缓存对象，Lru策略就是基于LinkedHashMap实现的，最大长度默认为1024。

 **3、作用域**
- 一级缓存的作用域是SqlSession级别。  （线程）

- 二级缓存的作用域是SqlSessionFactory级别。  （全局  NameSpace级别） 



**4、优先级别**

**<font color=orange>二级缓存的优先级高于一级缓存 </font>**



**5、默认开关**
- 一级默认开启

- 二级默认关闭，需要在配置文件手动开启







### 9.3 MyBatis中的Executor

Executor就是咱们在使用MyBatis和数据库交互时，底层就是在Executor的位置去搞Statement之类的内容



**BaseExecutor：** 基本的执行器 一级缓存执行器

**CachingExecutor：** 二级缓存执行器

**SimpleExecutor：** 一般默认使用的就是SimpleExecutor，每次执行SQL语句，都会创建一个Statement对象去和数据库完成交互



 **选择Executor，默认是在MyBatis的核心配置文件中修改settings，指定defaultExecutorType。**



### 9.4 谈谈你对IOC的理解（常识性）

<font color=red> **解耦：**</font>

IOC就是帮你创建对象，同时将对象地址扔到引用里。

某个通过IOC，各个模块之间的对象耦合性变的更低



**本质：**

在程序启动时，先加载xml以及注解的相关内容，获取bean的一些元数据，将这些**<font color=red>元数据</font>**封装为BeanDefinition的实例，扔到一个集合中

**基于反射的形式将对象构建出来，并且扔到一级缓存中**







### 9.5 Spring创建Bean的过程（Bean的生命周期、Spring的拓展接口）



> 1. 执行各种Aware接口。（ApplicationContextAware，也可以点一嘴实际的应用，SpringUtil工具类）
> 2. BeanPostProcessor的Before方法。
> 3. InitializingBean的afterPropertiesSet方法。
> 4. init-method方法
> 5. BeanPostProcessor的After方法。
> 6. 正常使用~~~
> 7. DisposableBean的destory方法
> 8. destory-method方法

![image.png](https://fynotefile.oss-cn-zhangjiakou.aliyuncs.com/fynote/fyfile/2746/1742987304035/c593b6ed40a04926bf2c4c3f2776b80d.png)











### 9.6 AOP



 **<font color=orange>在不改变源代码要动态注入功能的点</font>**



 AOP底层实现：

- AOP底层是基于动态代理实现的，他有两种实现的方案

  - **<font color=red>jdk动态代理：基于接口（interface）去构建代理对象</font>**。

  - **<font color=green>cglib动态代理：基于继承（extends）去构建代理对象</font>**。
    - **<font color=blue>cglib底层是基于字节码增强实现的, 代理效率更好</font>**
  -  **如果一个类没有实现接口，并且被final修饰，那这个类的对象就没法被代理。**



**声明式事务底层也是AOP** 

**Shiro，SpringSecurity在授权注解校验里都是基于AOP**



**代理对象的创建是基于BeanPostProcessor的after后置处理创建的。**





**<font color=red>代理的最终目的，是为了给某一个方法织入一些功能</font>**

**连接点：** 可以被织入功能的**<font color=green>方法</font>**，就叫连接点。

**切入点：** 真正要被织入功能的方法，就叫切入点。 **（切入点可以基于切入点表达式来指定）**

**增强：** 要被注入的功能，就要增强。

~~织入：将增强扔到切入点里的动作就是织入。 （不用聊~~）~~

**切面：** 将增强织入到切入点的过程就是切面。









### 9.7 Spring涉及到了哪些设计模式。

Spring涉及到的设计模式太多了，能说几个是几个，但是一定要点到具体是哪里涉及到了。

- 单例：Spring默认维护的bean都是单例的

- 工厂：Spring内部提供了各种工厂，顶级接口是BeanFactory

- 代理：AOP底层就是基于代理实现的

- 原型：Spring可以将bean的scope属性设置为prototype

- 装饰者：Spring在构建bean之后，会将器包装为BeanWrapper

- 构建者：在构建BeanDefinition的时候，属性贼多，内部提供了BeanDefinitionBuilder

- 责任链：Interceptor拦截器，多个拦截器就具备责任链的效果。

- 模板：RedisTemplate，RabbitTemplate…………各种模板~

- 策略：ClassPathXMLApplicationContext以及对应的FileSystemXmlApplicationContext

- 观察者：各种Listener，各种Event事件~~

- 委托…………………………等等









### 9.8 Spring的循环依赖



   ```codes
   class A{
       B b;
   }
   class B{
       A a;
   }
   ```



**Spring解决这个问题的方式**：

**<font color=green>Spring是允许将未完成初始化的实例提前暴露出来使用的</font>，所以上述的流程不会出现循环依赖的问题**



**<font color=red>二级缓存</font>就是分别存储提前暴露出来的对象，以及完成初始化的对象**

```codes
public class DefaultSingletonBeanRegistry ……{
   	/** 一级缓存，存储完成初始化的对象 */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** 二级缓存，存储提前暴露出来的对象 */
	private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
	
	// 三级缓存：存放 Bean 工厂对象，用于生成早期引用
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
}
```



**<font color=red>Spring提供了三级缓存</font>**

Spring提供了AOP的机制, 某个bean需要被代理，需要将代理对象提前暴露出来, 基于三级缓存中提供的getObject的方式将对象代理后，再从三级缓存中拿到二级或者一级缓存



**<font color=orange>获取对象的流程： 三级 --> 二级 --> 一级</font>**





### 9.9 SpringBoot的自动装配实现原理



**<font color=red>本质就是约定大于配置的</font>**



**注解方式的回答：**
- 在启动类的头上，有一个**@SpringBootApplication**的注解，这个注解是一个**<font color=green>组合注解</font>**。

- 在这个组合注解中，有一个@EnableAutoConfiguration的注解。同时他也是一个组合注解。

- 在这个组合注解中，有一个@Import的注解，引用了AutoConfigurationImportSelector的类。

- 在项目启动时，会加载到这个类，去读取META-INF下的spring.factories文件。

- 在这个spring.factories文件中，就存储着那些提前写好的配置。





**从源码维度的方式回答：**

* **启动类中注解包含了@Import注解，他引入了一个AutoConfigurationImportSelector的类。**
- **本质是AutoConfigurationImportSelector去选择出需要加载的各种AutoConfiguration的类。**

- **加载的过程是在SpringBoot项目启动后，基于加载ConfigurationClassPostProcessor去解析启动类中的@Import注解**

- **在基于CCPP~~内部的ConfigurationClassParser的类~~去解析启动类，最终会将启动类中引入到ACIS类，扔到一个List集合中**

- **然后再将List集合中的AutoConfigurationImportSelector类进行加载，会执行他的process方法，最终会拿到所有的AutoConfiguration，再选择需要进行加载的内容**









### 9.10 Nacos的服务注册



服务注册：Nacos客户端将自己的各种元数据（服务名，IP，Port等等）封装好，**<font color=red>基于grpc请求</font>将自己的元数据注册到NacosServer中**。



**大致流程**:

- 在注册之前，Nacos客户端会将自己的各种信息**封装成一个Instance实例**，里面包含了服务名、IP、Port、权重、健康信息、是否开启、是否是临时节点等。

- 基于NacosNamingService，将封装好的Instace注册要NacosServer上。

- 咱们自己的服务一般都是临时服务，那就默认走的都是**grpc的方式注册**上去，利用NamingGrpcClientProxy实现的请求发送。







### 9.11 Nacos1.x服务的心跳



**默认每隔5s要发送一次心跳请求（HTTP）。如果NacosServer15s没收到心跳，将服务的健康设置为false，30s没收到心跳，直接从注册表中剔除。**



本质其实就是利用**<font color=green>JUC包下的ScheduledThreadPoolExecutor去实现的定时任务</font>**，每隔5s，利用Java默认提供的方式发起的HTTP请求。



在内部会提交BeatTask任务，内部其实就是发送心跳请求，以及在当前服务没有在Nacos中找到时，会重新注册上去。





### 9.12 Nacos2.x服务的心跳



- **心跳 优化为了一个grpc的长连接**

- **长连接的建立，在<font color=red>服务注册之前完成</font>**

- 建立连接成功之后，会向一个队列中投递连接事件。







### 9.13 Nacos的服务发现

1、服务的发现，其实是**根据对应服务的名称去拉取服务的元数据**。

2、**第一步找本地缓存（ConcurrentHashMap）**，如果没有，再尝试访问NacosServer

3、在这会开启一个定时任务，延迟1s去NacosServer中拉取信息同步到ConcurrentHashMap中。

4、没有的话就直接发送一个grpc请求，找NacosServer去查询具体的服务信息。







### 9.14 Nacos的配置动态刷新



两个方式，一个Push，一个Pull





**客户端默认<font color=red>每10ms发送一次请求</font>到服务端，服务端不会立马响应, <font color=green>默认超时是30s，期间配置有变化，直接响应，如果没变，超时前响应</font>**











### 9.15 SpringBoot Start 组件的意义？

Spring Boot Starter 是 Spring Boot 框架的核心设计理念之一，它极大地简化了企业级 Java 应用的依赖管理和配置工作。

- **依赖管理简化**
- 自动配置  遵循 约定优于配置， 通过 META-INF/spring.factories 配置加载到 IoC容器

- @Conditional/ConditionalOnClass/ConditionalOnMissingBean  等 系列注解实现智能配置， **实现条件话装配**







### 9.16 SPI --- Service Provider Interface

**服务发现机制**，用于实现**接口与实现分离**和**可插拔架构**,**<font color=red>本质上是 一种"接口定义 + 动态发现"的扩展机制</font>**



- Service Interface --- 定义服务接口
- Service Provider --- 提供接口的具体实现

- 配置注册实现 --- META-INFO/service/.... 写入实现类的全名
- Service Loader --- 运行时加载



**经典应用场景**

1. **JDBC驱动加载**
   - 接口：`java.sql.Driver`
   - 实现：`com.mysql.cj.jdbc.Driver`
   - 配置文件：`META-INF/services/java.sql.Driver`
2. **日志框架适配**
   - SLF4J通过SPI绑定Logback、Log4j2等实现
3. **Spring Boot自动配置**
   - `spring.factories`机制是SPI的变种
4. **Dubbo扩展点**
   - 通过SPI实现可扩展的RPC框架





**<font color=red>SPI与Spring Boot的对比</font>**

| 机制     | **Java SPI**         | **Spring Boot自动配置** |
| :------- | :------------------- | :---------------------- |
| 配置文件 | `META-INF/services/` | `META-INF/spring/`      |
| 加载方式 | `ServiceLoader`      | `SpringFactoriesLoader` |
| 条件控制 | 无                   | 支持`@Conditional`      |
| 依赖范围 | JDK原生              | 需Spring环境            |

```
# 示例：META-INF/services/com.example.Storage
com.example.LocalStorage
com.example.CloudStorage
```



**SPI与API的区别**

| 特性         | **SPI**                                               | **API**                                             |
| :----------- | :---------------------------------------------------- | :-------------------------------------------------- |
| **调用方向** | **<font color=green>由实现方提供，由框架调用</font>** | **<font color=red>由框架提供，由开发者调用</font>** |
| **关注点**   | 扩展机制（如何被调用）                                | 功能接口（如何调用）                                |
| **典型例子** | JDBC驱动实现                                          | JDBC的`Connection`接口                              |







## 10 Spring相关面试题





### 10.1 Spring事务传播机制 / 事务传递

1. **REQUIRED（默认）**
   - **<font color=red>存在事务，则加入该事务；没有事务，则创建</font>**
2. **SUPPORTS**
   - **<font color=green>存在事务，则加入该事务</font>**
   - 如果当前**<font color=blue>没有事务，则以非事务方式执行</font>**
3. **MANDATORY**
   - 如果当前**存在事务，则加入该事务**
   - 如果当前**<font color=red>没有事务，则抛出异常</font>**
4. **REQUIRES_NEW**
   - **<font color=orange>创建一个新事务</font>**
   - 如果**<font color=red>当前存在事务，则挂起当前事务</font>**
   - **新事务与原有事务相互独立**
5. **NOT_SUPPORTED**
   - **以非事务方式执行**
   - 如果当前存在事务，则挂起当前事务
6. **NEVER**
   - **以非事务方式执行**
   - 如果当前**<font color=#aa00ff>存在事务，则抛出异常</font>**
7. **NESTED **--- **<font color=red>需要部分回滚的场景</font>**
   - 如果当前**<font color=#00aabb>存在事务，则在嵌套事务内执行</font>**
   - 如果当前**没有事务，则创建一个新事务**
   - 嵌套事务是外部事务的子事务，**<font color=#ff00aa>可以独立回滚</font>**





### 10.2 有三个线程T1,T2,T3 怎么确保他们都按顺序执行?

- 使用join  --- T2{ t1.join()}... 
- 单个线程的线程池 --- executor.submit()
- 使用计数器 --- `CountDownLatch`
- 使用信号量 --- Semaphore 

 



### 10.3 如何在20s内 对比两个表的100w数据的不同？

1. 分批数据读取
2. 多字段组合hash --- Adler32键哈希， 一种快速hash算法
3. 并行流数据比对 --- Stream.parallelStream()...









## 11 SpringMVC



### 11.1 SpringMVC 五大组件

- DispatcherServlet --- 前端/中央处理器
- HandlerMapping --- 处理映射器
- Controller --- 控制器
- ModelAndView --- 试图数据模型
- ViewResolver --- 视图解析器





### 11.2 过滤器（filter）和拦截器（Interceptor）的区别

Filter ---> Servlet ---> Interceptor ---> Controller

- 运行顺序 不同
  - Filter 在 Servlet 容器接受请求之后 处理请求之前 触发
  - Interceptor  是 servlet 调用之后  响应response数据之前 触发
- 配置方式不同
  - filter ---> Web.xml
  - interceptor ----> spring 配置文件 or  注解配置

- Filter 依赖Servlet容器， interceptor 不依赖 Servlet容器
- Filter 只能操作response/request, Interceptor 可以操作request/response/modelAndView/handler/exception



## 12 SpringBoot

SprinngBoot 是在 Spring 框架基础上推出的**快速应用开发框架**，通过"**<font color=red>约定优于配置</font>**"的理念简化了 Spring 应用的初始搭建和开发过程.



### 12.1 SpringBoot 核心特性

1. **自动配置（Auto-Configuration）** --- @EnableAutoConfiguration  
2. **起步依赖（Starter POMs）** ---- 添加依赖 spring-boot-starter-web
3. **内嵌服务（Embedded Server）** --- 如 tomcat/jetty 等
4. **Actuator监控**
5. **外部化配置**
6. **SpringBoot CLI**  --- 可以直接执行Grovy简本



### 12.2 自动装配流程(Auto-Configuration)  --- SpringBoot的和新特性之一 

@EnableAutoConfiguration  -->  AutoConfigurationImportSelector  --> SpringFactoriesLoader ---> **<font color=red>spring.factories</font>**



**实现机制**

- 通过`@EnableAutoConfiguration`触发
- 基于`spring.factories`/`AutoConfiguration.imports`发现配置
- 使用`@Conditional`系列注解控制生效条件



**排除特定自动配置**

```java
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
```



## 13 项目介绍 结构

- 基本信息介绍

1. 项目背景（项目为了解决什么问题）
2. 项目包含哪些功能模块， 简单说下每个功能的作用
3. 你参与并负责的模块
4. 项目采用的技术架构



- 难点/亮点/个人成长
  - 亮点/难点
    - 体现解决问题的能力
    - 体现学习能力
    - 体现你的架构思想
    - 团队协作精神
    - 管理能力
    - 技术能力
  - 主要体现出你能力的描述总结， eg, 高并发/高性能
  - eg: 从0到1微服务架构搭建， 线上问题诊断解决



