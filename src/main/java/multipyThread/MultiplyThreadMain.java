package multipyThread;

import java.util.concurrent.*;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class MultiplyThreadMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<String[]> filterWords = executor.submit(()-> {
             String content = CommonUtil.readFile("filterWords.txt");
            CommonUtil.printMessage(content);
             return content.split(",");
        });
        Future<String> newsContent = executor.submit(()->{
            String news = CommonUtil.readFile("news.txt");
            CommonUtil.printMessage(news);
            return news;
        });

        Future<String> replacedContent = executor.submit(()->{
            try {
                String[] words = filterWords.get();
                String content = newsContent.get();
                for (String word : words) {
                    if (content.contains(word)) {
                        content = content.replace(word, "**");
                    }
                }
                return content;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        CommonUtil.printMessage(replacedContent.get());
//        executor.shutdownNow();
    }
}
