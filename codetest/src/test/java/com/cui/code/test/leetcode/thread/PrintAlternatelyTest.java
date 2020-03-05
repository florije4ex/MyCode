package com.cui.code.test.leetcode.thread;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * 交替打印FooBar
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author CUI
 * @since 2020-03-05
 */
public class PrintAlternatelyTest {

    private void printAlternately(FooBar fooBar) {
        Runnable printTask = () -> {
            System.out.print(Thread.currentThread().getName());
        };

        Runnable first = () -> {
            try {
                fooBar.foo(printTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable second = () -> {
            try {
                fooBar.bar(printTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(first, "foo");
        Thread thread2 = new Thread(second, "bar");

        thread1.start();
        thread2.start();
    }

    @Test
    public void testPrintAlternately() {
        FooBar fooBar = new FooBar(10);
        printAlternately(fooBar);
    }

}

class FooBar {
    private Semaphore semaphoreFoo = new Semaphore(1);
    private Semaphore semaphoreBar = new Semaphore(0);

    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphoreFoo.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            System.out.println("-" + i);
            semaphoreBar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphoreBar.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            System.out.println("-" + i);
            semaphoreFoo.release();
        }
    }
}