package study;

import java.util.function.Predicate;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 21:44
 */
public class PredicateDemo {
    public static void main(String[] args) {
        method(s->  s.length() == 7, "abcdefg");
    }

    public static void method(Predicate<String> predicate, String s) {
        boolean test = predicate.test(s);
        System.out.printf("result="+test);
    }
}
