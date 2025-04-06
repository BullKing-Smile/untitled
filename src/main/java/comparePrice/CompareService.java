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
    public PriceResult getCheapestPrice(String productName) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Taobao
//        PriceResult tbPrice = HttpRequests.getTBPrice();
//        int tbDiscount = HttpRequests.getTBDiscount();

        Future<PriceResult> tbFuture = executorService.submit(() -> {
            // Taobao
            PriceResult tbPrice = HttpRequests.getTBPrice(productName);
            int tbDiscount = HttpRequests.getTBDiscount();
            return getRealPrice(tbPrice, tbDiscount);
        });
        Future<PriceResult> jdFuture = executorService.submit(() -> {
            // JD
            PriceResult jdPrice = HttpRequests.getJDPrice(productName);
            int jdDiscount = HttpRequests.getJDDiscount();
            return getRealPrice(jdPrice, jdDiscount);
        });
        Future<PriceResult> pddFuture = executorService.submit(() -> {
            // PDD
            PriceResult pddPrice = HttpRequests.getPDDPrice(productName);
            int pddDiscount = HttpRequests.getPDDDiscount();
            return getRealPrice(pddPrice, pddDiscount);
        });
        executorService.shutdown();
        // 计算各个平台最终价格
         return Stream.of(tbFuture, jdFuture, pddFuture)
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getReadPrice)).get();


    }

    public PriceResult getCheapestPrice_2(String productName) {

        CompletableFuture<PriceResult> tbFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getTBPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getTBDiscount), this::getRealPrice);

        CompletableFuture<PriceResult> jdFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getJDPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getJDDiscount), this::getRealPrice);

        CompletableFuture<PriceResult> pddFuture = CompletableFuture.supplyAsync(() -> HttpRequests.getPDDPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(HttpRequests::getPDDDiscount), this::getRealPrice);

        return Stream.of(tbFuture, jdFuture, pddFuture)
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getReadPrice))
                .get();
    }


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
