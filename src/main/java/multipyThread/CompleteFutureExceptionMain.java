package multipyThread;

import java.util.concurrent.CompletableFuture;

/**
 * @auth Felix
 * @since 2025/3/2 0:13
 */
public class CompleteFutureExceptionMain {


    public static void main(String[] args) {

        CompletableFuture<String> resultFuture = CompletableFuture.supplyAsync(() -> "result-0;")
                .thenApply(result1 -> {
                    var re = 1 / 0;
                    return result1 + "result-1;";
                }).handle((r1, err) -> {
                    if (null == err) {
                        return r1;
                    }
                    System.out.println("发生了异常-" + err.getMessage());
                    return "R1-null;";
                })
                .thenApply(result2 -> {
                    return result2 + "result-2;";
                })
                .handle((r2, err) -> {
                    if (null == err) {
                        return r2;
                    }
                    return "R2 error = " + err.getMessage();
                })
                .thenApply(result3 -> {
                    return result3 + "result-3;";
                })
                .exceptionally(err -> {
                    err.printStackTrace();
                    return "发生了异常-" + (err.getMessage());
                });

        System.out.println(resultFuture.join());

    }
}
