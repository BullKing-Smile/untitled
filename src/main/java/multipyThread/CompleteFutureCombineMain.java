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
