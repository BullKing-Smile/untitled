package model;


public record Person(String name, int age) {
    static int b;

    public void speak() {
        System.out.printf("这是常规方法---speak---");
    }
    public static void speak2() {
        System.out.printf("这是静态方法方法---static speak---");
    }
}
