package com.cui.code.thread;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 非阻塞同步 测试：Atomic采用了CAS指令
 *
 * @author cuishixiang
 * @date 2019-03-26
 */
public class AtomicTest {

    public static AtomicInteger race = new AtomicInteger(0);

    // incrementAndGet是原子性的操作
    public static void increase() {
        race.incrementAndGet();
    }

    // 也可以使用synchronized阻塞同步包过代码块，但是性能没有这个非阻塞的好
    @Test
    public void testAtomic() {
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
