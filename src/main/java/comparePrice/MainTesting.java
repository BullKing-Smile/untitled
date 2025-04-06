package comparePrice;

import multipyThread.CommonUtil;

import java.util.Arrays;

/**
 * @auth Felix
 * @since 2025/3/2 21:06
 */
public class MainTesting {

    public static void main(String[] args) {
        CompareService compareService = new CompareService();
        long start = System.currentTimeMillis();
//        PriceResult beautiful_phone = compareService.getCheapestPrice("Beautiful Phone");
//        PriceResult beautiful_phone = compareService.getCheapestPrice_2("Beautiful Phone");
        PriceResult beautiful_phone = compareService.getCheapestPrice_3(Arrays.asList("HWPhone", "iPhone", "XiaoMi", "Dream", "BYD"));

        long end = System.currentTimeMillis();
        CommonUtil.printMessage(beautiful_phone.toString());
        CommonUtil.printMessage("Cost = " + (end - start));
    }
}
