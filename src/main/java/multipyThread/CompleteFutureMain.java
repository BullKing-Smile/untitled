package multipyThread;

import lombok.val;

import java.util.concurrent.*;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService executor = Executors.newFixedThreadPool(5);

        CommonUtil.printMessage("CompletableFuture start running...");

         CompletableFuture.supplyAsync(()-> {
             String content = CommonUtil.readFile("filterWords.txt");
            CommonUtil.printMessage(content);
             return content.split(",");
        }).thenApply(filterWords->{
            String news = CommonUtil.readFile("news.txt");
            CommonUtil.printMessage(news);

            for (String word : filterWords) {
                if (news.contains(word)) {
                    news = news.replace(word, "**");
                }
            }
            return news;
        }).thenAccept(CommonUtil::printMessage).join();


        CommonUtil.printMessage("CompletableFuture start running 2...");

        val stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String content = CommonUtil.readFile("filterWords.txt");
            CommonUtil.printMessage(content);
            return content.split(",");
        }).thenApply(filterWords -> {
            String news = CommonUtil.readFile("news.txt");
            CommonUtil.printMessage(news);

            for (String word : filterWords) {
                if (news.contains(word)) {
                    news = news.replace(word, "**");
                }
            }
            return news;
        });

       var newNewsContent = stringCompletableFuture.get();
        CommonUtil.printMessage(newNewsContent);

//        CompletableFuture.supplyAsync(()->CommonUtil.readFile("news.txt")).thenCombine(, (()-> CommonUtil.readFile("news.txt"), ()-> CommonUtil.readFile("words.txt"), ))
    }
}
