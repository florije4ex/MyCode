package com.cui.code.thread;

import org.junit.Test;

/**
 * 并发测试：并发一定比串行执行快吗？
 *
 * @author cuishixiang
 * @since 2019-12-05
 */
public class ConcurrencyTest {

    private static final int count = 100_00000;

    /**
     * 并发和串行执行耗时测试，在循环次数不超过百万次的时候串行比并行快，这个具体数值也看机器
     */
    @Test
    public void testCostTime() throws InterruptedException {
        concurrency();
        serial();
    }

    private void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < count; i++) {
                sum += i;
            }
        });

        int sumb = 0;
        for (int i = 0; i < count; i++) {
            sumb += i;
        }

        long costTime = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency cost:" + costTime);
    }

    private void serial() {
        long start = System.currentTimeMillis();

        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += i;
        }
        int sumb = 0;
        for (int i = 0; i < count; i++) {
            sumb += i;
        }

        long costTime = System.currentTimeMillis() - start;
        System.out.println("serial cost:" + costTime);
    }
}
