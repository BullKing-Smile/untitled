package multipyThread;

import io.netty.util.CharsetUtil;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureAllofMain {

    public static CompletableFuture<String> readFile(String path) {
        return CompletableFuture.supplyAsync(()->{
            try {
                return Files.readString(Paths.get(path), CharsetUtil.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                return "---";
            }
        });
    }

    public static void main(String[] args)  {
//        ExecutorService executor = Executors.newFixedThreadPool(4);
        // 文件名称集合
        List<String> files = List.of("news1.txt","news2.txt","news3.txt");

        List<CompletableFuture<String>> futures = files.stream().map(file-> readFile(file)).collect(Collectors.toList());
        CompletableFuture[] filesArr = futures.toArray(new CompletableFuture[0]);

        CompletableFuture<Long> completeFuture = CompletableFuture.allOf(filesArr).thenApply(v -> {
            return futures.stream().map(f -> f.join()).filter(content -> content.contains("CompleteFuture")).count();
        });

        var count = completeFuture.join();

        CommonUtil.printMessage("Contains count = " + count);
//        executor.shutdownNow();
    }
}
