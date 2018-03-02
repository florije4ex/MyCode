package com.cui.code.thread;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 线程调试测试：在IDEA中调试时，如果是启动了多个线程，则调试时应该使用断点然后用F9才能进入新线程，如果是F8则只会在当前线程中往下执行
 *
 * @author cuishixiang
 * @date 2018-03-01
 */
public class ThreadDebugTest {

    public static void main(String[] args) {
        System.out.println("主线程开始：" + Thread.currentThread().getName());

//        测试一
//        new Thread(() -> {
//            System.out.println("新线程：" + Thread.currentThread().getName());
//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();


//        测试二
        RecursiveTask<Integer> task = new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                System.out.println("池中线程：" + Thread.currentThread().getName());
                return new Random().nextInt();
            }
        };

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.submit(task);
        try {
            System.out.println(task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束：" + Thread.currentThread().getName());
    }
}
