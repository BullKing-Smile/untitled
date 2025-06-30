# Multi-Thread

## 线程的启动方式
- 继承 Thread 类
- 实现 Runnable 接口
- 实现 Callable 接口

## CompletableFuture
优势
- 快速创建/链式依赖/组合多个Future 提供大量便利
- 提供了多种场景的回调函数，非常便利的异常处理支持
- 支持lambda/stream
- 把异步编程/函数式编程/响应式编程 集于一身

### 创建任务
- CompletableFuture\<Void> runAsync() -- 不从任务返回中获取内容
- CompletableFuture\<Void> runAsync(Runnable runnable, Executor executor) // 指定线程池

- \<U> CompletableFuture\<U> supplyAsync()  可以从任务返回中 <b>获取内容</b>
- CompletableFuture\<U> supplyAsync(Supplier\<U> supplier, Executor executor) // 指定线程池

### 结束线程池 所有线程
> Will stop all actively executing tasks, waiting the actively finished<br>
> executor.shutdown() <br>

> WIll stop all actively executing tasks, does not wait for actively executing tasks to terminate<br>
> executor.shutdownNow() <br> 


### 线程池的最佳实践
<b><font color=orange size=4>创建属于自己业务的线程池</font></b>如果所有的CompletableFuture共用一个线程池，那么当有非常耗时的IO操作时，将导致线程池中的<b>所有线程都阻塞在IO操作上</b>，从而造成<b><font color=yellow size=3>线程饥饿</font></b>


### 异步任务的回调
> - CompletableFuture\<U> <font color=grass>thenApply</font>(Function<T,N> fn) // 任务执行后 获取结果<br>
> - CompletableFuture\<Void> <b><font color=grass>thenAccept</font>(Consumer<? super T> action)</b> // 任务执行结束的回调， 不返回任何结果<br>
> 接收上一步的结果， 通常用在回调栈中的最后一个回调<br>
> - CompletableFuture\<Void> <font color=grass>thenRun</font>(Runnable action)<br> // 只是接受一个通知， <font color=red size=3 style=blod>不使用上一步的结果</font><br>
> 异步任务完成后， 只想得到一个完成的通知，并不使用上一步的结果。<br>
> 通常也是用在链式调用的末端<br>


### 异步任务编排 - thenCompose
> - CompletableFuture\<U> thenCompose(Function<? super T, ? extends CompletionStage\<U>> fn)
> 接收上一个异步任务的结果 进行应用， 经function回调转换后的结果是一个CompletableFuture对象， 可以在主线程中拿到编排后的结果<br>
> 


### 编排两个非依赖关系的异步任务 - themCombine
> - CompletableFuture<V> thenCombine(
    CompletionStage<? extends U> other,
    BiFunction<? super T,? super U,? extends V> fn)
```java
package multipyThread;

import lombok.val;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureCombineMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        CommonUtil.printMessage("CompletableFuture start running...");

        val filterWordsFuture = CompletableFuture.supplyAsync(() -> {
            String content = CommonUtil.readFile("filterWords.txt");
            CommonUtil.printMessage(content);
            return content.split(",");
        });

        val newsContentFuture = CompletableFuture.supplyAsync(() -> {
            String news = CommonUtil.readFile("news.txt");
            CommonUtil.printMessage(news);
            return news;
        }, executor);
        val finalFuture = filterWordsFuture.thenCombine(newsContentFuture, (words, content) -> {

            for (String word : words) {
                if (content.contains(word)) {
                    content = content.replace(word, "**");
                }
            }
            CommonUtil.printMessage("Filter finish");
            return content;
        });
        var finalContent = finalFuture.get();

        CommonUtil.printMessage(finalContent);
        executor.shutdownNow();
    }
}

```


### 合并多个异步任务 allOf/anyOf
- CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
- CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)

> allOf() 适合多个异步任务 都完成后 做一些处理 

### 异步调用链路上 异常处理 handle()
> 接收到来给你个参数， 一个是上一个异步任务的结果和异常信息， 当然如果一场为空， 则数据继续向下传递
>


### 异步调用链路上异常接收 exceptionally()
> 该方法只接受异常信息， 链路上任意一个异步任务出现异常，则直接回调到该方法
> 

## CompletableFuture进阶

### 异步任务的交互

### 先到先得 
- CompletableFuture\<U> applyToEither()
- CompletableFuture<Void> acceptEither()
```java
package multipyThread;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureAcceptToEitherMain {


    public static void main(String[] args) throws InterruptedException {


        CompletableFuture<Integer> task1Future = CompletableFuture.supplyAsync(() -> {
            var second = new Random().nextInt(3);
            CommonUtil.printMessage("Sleep-1 - " + second + "s");
            try {
                TimeUnit.SECONDS.sleep(second);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return second;
        });

        CompletableFuture<Integer> task2Future = CompletableFuture.supplyAsync(() -> {
            var second = new Random().nextInt(3);
            CommonUtil.printMessage("Sleep-2 - " + second + "s");
            try {
                TimeUnit.SECONDS.sleep(second);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return second;
        });

        task1Future.acceptEither(task2Future, result -> {
            CommonUtil.printMessage("Final Result is = "+result);
        });

        TimeUnit.SECONDS.sleep(4);
    }
}

```

### get() 与 join() 的区别
> 两者都是 <b>以阻塞的方式</b>获取异步任务结果的方法<br>
- get()会 抛出 编译时异常， 必须处理<br>
- <font color=yellow>join() 只会有运行时异常， 可以不处理， 因此 更适合流式编程方式</font><br>



### 合理配置线程池中的线程数
- CompletableFuture & ParallelStream
> - 任务是IO密集型 建议使用CompletableFuture<br>
> - 任务是CPU密集型, 建议使用 ParallelStream, 使用处理器 多线程是没有意义的。

- I/O密集型 & CPU密集型
> - CPU密集型， 也叫计算密集型， 系统运行时<font color=yellow>大部分时间CPU占有率100%</font>， I/O会在很短时间内完成。
> - I/O密集型，<font color=pink>大部分时间CPU在等I/O(硬盘/内存)的读写操作</font>，但CPU使用率不高。


- 多少线程数合适
> - CPU密集型，要尽量压榨CPU, 参考值：Ncpu + 1
> - I/O密集型，参考值：2 * Ncpu(cpu核心数)


### CompletableFuture 优化多个线程耗时操作 ---- 商品比价
```java
package comparePrice;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;
/**
 * @auth Felix
 * @since 2025/3/2 20:47
 */
public class CompareService {
    public PriceResult getCheapestPrice_2(String productName) {
        // 使用thenCombine组合执行多线程异步任务, 最后join()方法获取返回结果
        CompletableFuture<PriceResult> tbFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getTBPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getTBDiscount), this::getRealPrice);

        CompletableFuture<PriceResult> jdFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getJDPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getJDDiscount), this::getRealPrice);

        CompletableFuture<PriceResult> pddFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getPDDPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getPDDDiscount), this::getRealPrice);

        // !!! 重点代码在这里 !!!
        return Stream.of(tbFuture, jdFuture, pddFuture)
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getReadPrice))
                .get();
    }


    public PriceResult getRealPrice(PriceResult priceResult, int discount) {
        priceResult.setReadPrice(priceResult.getPrice() - discount);
        priceResult.setDiscount(discount);
        return priceResult;
    }
}

```
// 模拟耗时操作的类
```java
package comparePrice;

import multipyThread.CommonUtil;

import java.util.concurrent.TimeUnit;

/**
 * 模拟耗时操作类
 * @auth Felix
 * @since 2025/3/2 20:38
 */
public final class HttpRequests {
    public static PriceResult getTBPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("Taobao price = " + 5099);
        return PriceResult.builder()
                .price(5099f)
                .platform("Taobao")
                .name(productName)
                .build();
    }
    public static int getTBDiscount() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("Taobao discount = " + 800);
        return 800;
    }

    public static PriceResult getJDPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("JD price = " + 5199);
        return PriceResult.builder()
                .price(5199f)
                .platform("JD")
                .name(productName)
                .build();
    }

    public static int getJDDiscount() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("JD discount = " + 600);
        return 600;
    }

    public static PriceResult getPDDPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("获取PDD price = " + 5099);
        return PriceResult.builder()
                .price(5099f)
                .platform("PDD")
                .name(productName)
                .build();
    }

    public static int getPDDDiscount() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonUtil.printMessage("获取PDD discount = " + 5000);
        return 5000;
    }
}
```

### CompletableFuture 优化多个线程耗时操作 ---- 商品批量比价

- 开始批量比较
```java
package comparePrice;

import multipyThread.CommonUtil;

import java.util.Arrays;

/**
 * @auth Felix
 * @since 2025/3/2 21:06
 */
public class MainTesting {

    public static void main(String[] args) {
        CompareService compareService = new CompareService();
        long start = System.currentTimeMillis();
//        PriceResult beautiful_phone = compareService.getCheapestPrice("Beautiful Phone");
//        PriceResult beautiful_phone = compareService.getCheapestPrice_2("Beautiful Phone");
        PriceResult beautiful_phone = compareService.getCheapestPrice_3(Arrays.asList("HWPhone", "iPhone", "XiaoMi", "Dream", "BYD"));

        long end = System.currentTimeMillis();
        CommonUtil.printMessage(beautiful_phone.toString());
        CommonUtil.printMessage("Cost = " + (end - start));
    }
}

```

```java
package comparePrice;

import multipyThread.CommonUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auth Felix
 * @since 2025/3/2 20:47
 */
public class CompareService {
/*
     * 批量比价
     * @return comparePrice.PriceResult
     */
    public PriceResult getCheapestPrice_3(List<String> productNames) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        List<CompletableFuture<PriceResult>> completableFutures = productNames.stream().map(p -> CompletableFuture.supplyAsync(() -> HttpRequests.getTBPrice(p), executorService)
                .thenCombineAsync(CompletableFuture.supplyAsync(HttpRequests::getTBDiscount), (priceResult, discount) ->
                        this.getRealPrice(priceResult, discount), executorService)).toList();
//        return completableFutures.stream().map(CompletableFuture::join).min(Comparator.comparing(PriceResult::getReadPrice)).get();

        try {
            return completableFutures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(PriceResult::getReadPrice))
                    .findFirst().get();
        } finally {
            executorService.shutdown();
            CommonUtil.printMessage("----- shutdown -----");
        }
    }
    public PriceResult getRealPrice(PriceResult priceResult, int discount) {
        priceResult.setReadPrice(priceResult.getPrice() - discount);
        priceResult.setDiscount(discount);
        return priceResult;
    }
}

```











