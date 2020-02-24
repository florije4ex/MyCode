package com.cui.code.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        });
        log.info("test submit finish time:{}", System.currentTimeMillis());
        try {
            Object result = future.get();
            log.info("task result:{}", result);
        } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        });
        log.info("test submit finish time:{}", System.currentTimeMillis());
        try {
            Object result = future.get(10000, TimeUnit.MILLISECONDS);
            log.info("task result:{}", result);
        } catch (Exception e) {
            log.error("test error time：{}", System.currentTimeMillis(), e);
        }
    }

    /**
     * 测试线程池提交任务的时间关系：等待所有任务执行完成才会返回,没有超时
     */
    @Test
    public void testInvokeAll() {
        log.info("test start time:{}", System.currentTimeMillis());
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            tasks.add(() -> {
                int random = new Random().nextInt(10000);
                log.info("task start time:{},sleep time:{}", System.currentTimeMillis(), random);
                try {
                    Thread.sleep(random);
                } catch (Exception e) {
                    log.error("current thread error", e);
                }
                return Thread.currentThread().getName() + " " + System.currentTimeMillis();
            });
        }

        log.info("task submit start time:{}", System.currentTimeMillis());
        List<Future<Object>> futures = null;
        try {
            futures = executorPool.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("task submit end time:{}", System.currentTimeMillis());

        for (Future<Object> future : futures) {
            try {
                Object result = future.get();
                log.info("task result:{}", result);
            } catch (Exception e) {
                log.error("test error time：{}", System.currentTimeMillis(), e);
            }
        }
    }

    /**
     * 测试线程池提交任务的时间关系：未等到所有任务执行完成就超时
     */
    @Test
    public void testInvokeAllTimeOut() {
        log.info("test start time:{}", System.currentTimeMillis());
        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(() -> {
                int random = new Random().nextInt(10000);
                log.info("task start time:{},sleep time:{}", System.currentTimeMillis(), random);
                try {
                    Thread.sleep(random);
                } catch (Exception e) {
                    log.error("current thread error", e);
                }
                log.info("^^^^^^^^^^^");
                return Thread.currentThread().getName() + " " + System.currentTimeMillis();
            });
        }

        log.info("task submit start time:{}", System.currentTimeMillis());
        List<Future<Object>> futures = null;
        try {
            futures = executorPool.invokeAll(tasks, 5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("executor pool error", e);
        }
        log.info("task submit end time:{}", System.currentTimeMillis());

        for (Future<Object> future : futures) {
            try {
                Object result = future.get();
                log.info("task result:{}", result);
            } catch (Exception e) {
                log.error("test error time：{}", System.currentTimeMillis(), e);
            }
        }
    }
}
