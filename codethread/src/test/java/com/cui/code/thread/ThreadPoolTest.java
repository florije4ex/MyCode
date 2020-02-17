package com.cui.code.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 线程池测试
 *
 * @author cuishixiang
 * @since 2018-11-27
 */
@Slf4j
public class ThreadPoolTest {
    private static final LinkedBlockingQueue<Runnable> tasksQueue = new LinkedBlockingQueue<>(100);
    private static final ExecutorService executorPool =
            new ThreadPoolExecutor(
                    10,
                    20,
                    60L,
                    TimeUnit.SECONDS,
                    tasksQueue,
                    new ThreadPoolExecutor.DiscardPolicy()
            );

    /**
     * 测试线程池提交任务的时间关系：get 阻塞时间
     */
    @Test
    public void testFutureGetTime() {
        log.info("test start time:{}", System.currentTimeMillis());
        Future<?> future = executorPool.submit(() -> {
            log.info("task start time:{}", System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        });
        log.info("test submit finish time:{}", System.currentTimeMillis());
        try {
            Object result = future.get();
            log.info("task result:{}", result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("test error time：{}", System.currentTimeMillis(), e);
        }
    }

    /**
     * 测试线程池提交任务的时间关系：get 阻塞超时
     */
    @Test
    public void testFutureGetTimeOut() {
        log.info("test start time:{}", System.currentTimeMillis());
        Future<?> future = executorPool.submit(() -> {
            log.info("task start time:{}", System.currentTimeMillis());
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        });
        log.info("test submit finish time:{}", System.currentTimeMillis());
        try {
            Object result = future.get(10000, TimeUnit.MILLISECONDS);
            log.info("task result:{}", result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("test error time：{}", System.currentTimeMillis(), e);
        }
    }

}
