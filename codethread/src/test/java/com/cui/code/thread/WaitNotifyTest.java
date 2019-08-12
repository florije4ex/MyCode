package com.cui.code.thread;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 等待、通知机制
 *
 * @author CUI
 * @date 2019-08-12
 */
public class WaitNotifyTest {
    static boolean flag = true;
    static Object lock = new Object();

    @Test
    public void testWaitNotify() throws InterruptedException {
        Thread wait = new Thread(new Wait(), "WaitThread");
        wait.start();
        TimeUnit.SECONDS.sleep(1);

        Thread notify = new Thread(new Notify(), "NotifyThread");
        notify.start();

        notify.join();
    }


    class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                // 条件不满足时就等着wait，并且释放锁
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is :" + flag + ", wait at " + new Date());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 条件满足后就执行任务
                System.out.println(Thread.currentThread() + " flag is : " + flag + ", running at " + new Date());
            }
        }
    }

    class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                // 获取锁，然后进行通知，通知时不会释放锁，直到当前线程释放锁以后，等待的线程才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold lock, notify at " + new Date());
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again, sleep at " + new Date());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
