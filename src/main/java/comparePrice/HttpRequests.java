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
