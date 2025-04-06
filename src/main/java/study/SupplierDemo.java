package study;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 21:21
 */
public class SupplierDemo {

    public static void main(String[] args) {
        method(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int[] arr = {4,2,3,7,5,9,1};
                Arrays.sort(arr);
                return arr[arr.length - 1];
            }
        });
    }

    public static void method(Supplier<Integer> supplier){
        Integer max = supplier.get();
        System.out.println("max = " + max);
    }
}
