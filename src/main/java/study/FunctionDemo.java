package study;

import java.util.function.Function;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 21:32
 */
public class FunctionDemo {
    public static void main(String[] args) {
        method(integer -> String.valueOf(integer), 10);

    }
    public static void method(Function<Integer, String> function, Integer number) {
        String s = function.apply(number * 2);
        System.out.println(s);
    }
}
