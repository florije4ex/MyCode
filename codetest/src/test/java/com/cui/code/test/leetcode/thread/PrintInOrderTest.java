package com.cui.code.test.leetcode.thread;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 按序打印：
 * https://leetcode-cn.com/problems/print-in-order/
 *
 * @author CUI
 * @since 2020-03-02
 */
public class PrintInOrderTest {

    private void printInOrder(int[] array) {
        Foo foo = new Foo();

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName());
        };

        Runnable first = () -> {
            try {
                foo.first(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable second = () -> {
            try {
                foo.second(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable third = () -> {
            try {
                foo.third(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Thread threadA = new Thread(first, "first");
        Thread threadB = new Thread(second, "second");
        Thread threadC = new Thread(third, "third");
        Map<Integer, Thread> threadMap = new HashMap<>();
        threadMap.put(1, threadA);
        threadMap.put(2, threadB);
        threadMap.put(3, threadC);

        for (int num : array) {
            System.out.println("thread num:" + num);
            Thread thread = threadMap.get(num);
            thread.start();
        }
    }

    @Test
    public void testPrint() {
        int[] array = new int[]{2, 3, 1};
        printInOrder(array);

        System.out.println("-----------------");

        array = new int[]{1, 2, 3};
        printInOrder(array);

        System.out.println("-----------------");

        array = new int[]{1, 2, 3};
        printInOrder(array);
    }
}


class Foo {
    private CountDownLatch second = new CountDownLatch(1);
    private CountDownLatch third = new CountDownLatch(1);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        second.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        second.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        third.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        third.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}