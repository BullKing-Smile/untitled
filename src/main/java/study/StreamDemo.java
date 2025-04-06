package study;

import model.Person;
import model.Person2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 22:01
 */
public class StreamDemo {
    public static void main(String[] args) {
//        var list = new ArrayList<String>();
//        list.add("Tom");
//        list.add("Lily");
//        list.add("Amand");
//
//        var newList = list.stream().filter(s-> s.length() <= 4)
//                .filter(s-> s.startsWith("L"))
//                .collect(Collectors.toList());
//        System.out.println(newList);
//
//        forEachFun();
//        concatFun();
//        distinctFun();
//        distinctFun2();
//        distinctFun3();
//        mapFun();


//        System.out.printf("000"+ " abc "+"000\n"); //000 abc 000
//        System.out.printf("000"+supplierFun(" abc "::trim)+"000\n"); //000abc000
//        double d = supplierFun2(Math::random);
//        System.out.println(d);
        Person2 p = supplierFun3(Person2::new, "abc");
        System.out.println(p);
    }



    public static void forEachFun() {
        var arr = new Integer[]{2,3,7,5,4,8,1,9,10,0,23};
        Stream.of(arr)
                .filter(num-> num>=5)
                .sorted((n1, n2)->  n2 - n1)
                .forEach(System.out::println);
    }

    public static void concatFun() {
        Stream<String> stream1 = Stream.of("Tom", "Aba", "Muhail");
        Stream<String> stream2 = Stream.of("Edward", "Felix", "Jesica");

        Stream.concat(stream1, stream2).forEach(System.out::println);
    }

    public static void streamToList() {
        Stream<String> stream1 = Stream.of("Tom", "Aba", "Muhail");
//stream1.collect(Collectors.toList())
        List<String> list = stream1.toList();
    }

    public static void distinctFun() {
        Stream<String> stream1 = Stream.of("Tom", "Aba", "Tom", "Libai");

        stream1.distinct().forEach(System.out::println);
    }

    public static void distinctFun2() {
        Stream<Person> stream1 = Stream.of(
                new Person("Tom", 19),
                new Person("Jicc", 30),
                new Person("Tom", 19));

        stream1.distinct().forEach(System.out::println);
    }
    public static void distinctFun3() {
        Stream<Person2> stream1 = Stream.of(
                new Person2("Tom", 19),
                new Person2("Jicc", 30),
                new Person2("Tom", 19));

        stream1.distinct().forEach(System.out::println);
    }
    public static void mapFun() {
        Stream<Person2> stream1 = Stream.of(
                new Person2("Tom", 19),
                new Person2("Jicc", 30),
                new Person2("Tom", 19));

        stream1.map(p-> p.getName())
                .forEach(System.out::println);
    }

    public static String supplierFun(Supplier<String> supplier) {
        return supplier.get();
    }

    public static Double supplierFun2(Supplier<Double> supplier) {
        return supplier.get();
    }

    public static Person2 supplierFun3(Function<String, Person2> function, String name) {
        return function.apply(name);
    }
}
