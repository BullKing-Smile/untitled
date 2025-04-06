package multipyThread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureParallelStreamMain {


    public static void main(String[] args) throws InterruptedException {



        long start = System.currentTimeMillis();
        System.out.println("Start：" + start);
        IntStream stream = IntStream.range(0, 10);
        int CPU_C = Runtime.getRuntime().availableProcessors();

        List<MyTask> collect = stream.mapToObj(m -> new MyTask(m)).collect(Collectors.toList());
        System.out.printf("CPU C = %d\n", CPU_C);
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(collect.size(), CPU_C * 2));
        List<CompletableFuture<Integer>> resultFutures = collect.stream().map(t -> CompletableFuture.supplyAsync(() -> {
            t.doWrok();
            return 1;
        }, executor)).collect(Collectors.toList());
        int sum = resultFutures.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        long end = System.currentTimeMillis();
        float cost = (end - start) / 1000f;
        System.out.println("End：" + end);
        System.out.println(sum);
        System.out.printf("用时：%.2f\n", cost);
        executor.shutdown();
    }
}
