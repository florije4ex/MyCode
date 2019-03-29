package com.cui.code.thread;

import org.junit.Test;

/**
 * 死锁测试，用jvisualvm查看dump出来的线程，可看到BLOCKED的两个线程，且各自拥有的锁。
 *
 * @author cuishixiang
 * @date 2019-03-29
 */
public class DeadLockTest {
    private String lock1 = "lock1";
    private String lock2 = "lock2";

    @Test
    public void testDeadLock() {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "：获取到锁：" + lock1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "：获取到锁：" + lock2);
                }
            }
        }, "线程1");
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "：获取到锁：" + lock2);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "：获取到锁：" + lock1);
                }
            }
        }, "线程2");

        thread1.start();
        thread2.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
