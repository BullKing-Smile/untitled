package study;

import java.util.function.Consumer;

/**
 * @Author shenfei.wang@g42.ai
 * @Description 消费性函数接口
 * @Date 2025/2/24 21:28
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        //
        method(s -> System.out.println(s), "abcdef");
    }

    public static void method(Consumer<String> consumer, String str) {
        consumer.accept(str);
    }
}
