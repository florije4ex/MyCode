package com.cui.code.thread;

import org.junit.Test;

/**
 * 这也属于线程的等待通知机制
 *
 * @author CUI
 * @date 2019-08-12
 */
public class JoinThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JoinThread(previous), i + "-t");
            thread.start();
            previous = thread;
        }

        Thread.sleep(5000);
        System.out.println(Thread.currentThread() + " finish.");
    }

    @Test
    public void testJoin() throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JoinThread(previous), i + "-t");
            thread.start();
            previous = thread;
        }

        Thread.sleep(5000);
        System.out.println(Thread.currentThread() + " finish.");

    }

    static class JoinThread implements Runnable {
        private Thread previous;

        public JoinThread(Thread previous) {
            this.previous = previous;
        }

        @Override
        public void run() {
            try {
                previous.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " finish.");
        }
    }

}
