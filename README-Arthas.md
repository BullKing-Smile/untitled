# Arthas
[Document](https://github.com/alibaba/arthas/blob/master/README_CN.md)
Alibaba在2018年9月开源的Java诊断工具
当你遇到以下类似问题而束手无策时，Arthas可以帮助你解决：

- 这个类从哪个 jar 包加载的？为什么会报各种类相关的 Exception？
- 我改的代码为什么没有执行到？难道是我没 commit？分支搞错了？
- 遇到问题无法在线上 debug，难道只能通过加日志再重新发布吗？
- 线上遇到某个用户的数据处理有问题，但线上同样无法 debug，线下无法重现！
- 是否有一个全局视角来查看系统的运行状况？
- 有什么办法可以监控到JVM的实时运行状态？
- 怎么快速定位应用的热点，生成火焰图？
- 怎样直接从JVM内查找某个类的实例？
 



## Command
下载
curl -O https://arthas.aliyun.com/arthas-boot.jar
启动
java -jar arthas-boot.jar --- 启动arthas服务


### dashboard
查看dashboard
dashboard

### thread
一目了然的了解系统的状态，哪些线程比较占cpu？他们到底在做什么？
查看指定线程信息
thread [thread id] 查看具体信息
thread -n [thread id] --- 哪些线程比较占cpu？他们到底在做什么？
thread -b 查看blocked 信息


### jad --- 在线反编译， 查看源代码
对类进行反编译:
$ jad javax.servlet.Servlet

### mc
Memory Compiler/内存编译器，编译.java文件生成.class。
mc /tmp/Test.java

### retransform
加载外部的.class文件，retransform 热更新jvm已加载的类。

retransform /tmp/Test.class
retransform -c 327a647b /tmp/Test.class /tmp/Test\$Inner.class

### sc
查找JVM中已经加载的类


### VMtool
从JVM heap中获取指定类的实例
vmtool --action getInstances --className java.lang.String --limit 10

### Stack
查看方法 test.arthas.TestStack#doGet 的调用堆栈：
stack com.coocpu.example.UserService doGet
Press Ctrl+C to abort

### Trace 
观察方法执行的时候哪个子调用比较慢:
trace com.coocpu.example.UserService getToken
Press Ctrl+C to abort

### Watch
观察方法 test.arthas.TestWatch#doGet 执行的入参，仅当方法抛出异常时才输出。
watch test.arthas.TestWatch doGet {params[0], throwExp} -e
Press Ctrl+C to abort.

### Monitor
监控某个特殊方法的调用统计数据，包括总调用次数，平均rt，成功率等信息，每隔5秒输出一次。
monitor -c 5 org.apache.dubbo.demo.provider.DemoServiceImpl sayHello
Press Ctrl+C to abort.

### Time Tunnel(tt)
记录方法调用信息，支持事后查看方法调用的参数，返回值，抛出的异常等信息，仿佛穿越时空隧道回到调用现场一般
tt -t com.coocpu.example.service.UserService getUserInfo
Press Ctrl+C to abort

### Classloader
当前系统中有多少类加载器，以及每个加载器加载的类数量，帮助您判断是否有类加载器泄露。
classloader

### redefine --- 重新加载class
在线修改 执行新版本的代码















