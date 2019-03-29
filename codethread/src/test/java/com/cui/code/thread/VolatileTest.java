package com.cui.code.thread;

import org.junit.Test;

/**
 * volatile 测试
 *
 * @author cuishixiang
 * @date 2019-03-25
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    // 测试volatile并非绝对同步可见的,同步的race本应是10000*20的，但每次都比200000小
    @Test
    public void testVolatile() {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    increase();
                }
            });
            threads[i].start();
        }

        // 等待所有的累加线程都结束
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println(race);
    }
}
