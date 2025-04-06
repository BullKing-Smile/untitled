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
