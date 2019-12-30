package com.cui.logback.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * log4j2 测试类
 *
 * @author CUI
 * @since 2019-12-30
 */
public class Log4j2Test {

    @Test
    public void testBase() {
        Logger logger = LoggerFactory.getLogger(Log4j2Test.class);
        logger.info("测试配置");
    }

    /**
     * 50 个线程，打印固定长度日志，总共 100万行日志，
     * 异步日志性能测试：带 %file:%line 参数
     * 带：16454、18416
     * 不带：9072
     * <p>
     * 异步测试：
     * ThreadCount=50
     * AsyncAppender：17714、
     * AsyncLogger：18480
     * <p>
     * ThreadCount=5
     * AsyncAppender：9955
     * AsyncLogger：14946
     *
     * <p>
     * 本机配置：
     * 型号名称：	MacBook Pro
     * 型号标识符：	MacBookPro14,1
     * 处理器名称：	Intel Core i5
     * 处理器速度：	2.3 GHz
     * 处理器数目：	1
     * 核总数：	2
     * L2 缓存（每个核）：	256 KB
     * L3 缓存：	4 MB
     * 超线程技术：	已启用
     * 内存：	8 GB
     * 貌似并没有提高，不知道和机器配置是否有关系
     */
    @Test
    public void testPerformance() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        int threadCount = 5;
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < 200000; j++) {
                    logger.info("test performance……");
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info("log4j2 async appender cost time:{}", costTime);
        System.out.println(costTime);
    }
}
