package multipyThread;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureAnyOfMain {

//    public static CompletableFuture<String> readFile(String path) {
//        return CompletableFuture.supplyAsync(()->{
//            try {
//                return Files.readString(Paths.get(path), CharsetUtil.UTF_8);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "---";
//            }
//        });
//    }

    public static void main(String[] args)  {

        CompletableFuture<String> task1Future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Task-1 finished.";
        });
        CompletableFuture<String> task2Future = CompletableFuture.supplyAsync(() ->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Task-2 finished.";
        } );
        CompletableFuture<String> task3Future = CompletableFuture.supplyAsync(() ->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Task-3 finished.";
        } );

        CompletableFuture<Object> anyResultFuture = CompletableFuture.anyOf(task1Future, task2Future, task3Future);
        while (!anyResultFuture.isCompletedExceptionally()) {
            System.out.println(anyResultFuture.join());
        }
    }
}
