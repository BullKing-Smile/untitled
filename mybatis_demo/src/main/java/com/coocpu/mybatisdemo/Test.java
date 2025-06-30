package com.coocpu.mybatisdemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auth Felix
 * @since 2025/4/16 23:25
 */
public class Test {

    static int a=0, b = 0;
    static int x=0, y=0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;i++) {
              a = 0;
              b = 0;
              x = 0;
              y = 0;
            CountDownLatch lock = new CountDownLatch(2);
            Thread threadA = new Thread(() -> {
                b = 1;
                x = a;
                lock.countDown();
            });
            Thread threadB = new Thread(() -> {
                a = 1;
                y = b;
                lock.countDown();
            });
            threadA.start();
            threadB.start();
            lock.await();

            // 第1045624次， x = 0, y = 0

            if (x == 0 && y == 0) {
                System.out.println("第"+i+"次， x = 0, y = 0");
                break;
            }
        }
    }
}
