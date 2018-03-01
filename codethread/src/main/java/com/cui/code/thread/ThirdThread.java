package com.cui.code.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程实现方式三：FutureTask Callable
 *
 * @author cuishixiang
 * @date 2018-02-28
 */
public class ThirdThread {

    public static void main(String[] args) {

        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            int i = 0;
            for (; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "：" + i);
            }
            return i;
        });

        for (int i = 0; i < 100; i++) {
            //获取当前线程名
            System.out.println(Thread.currentThread().getName() + "：" + i);

            if (i == 20) {
                new Thread(futureTask, "callable线程").start();
            }
        }

        try {
            System.out.println("Callable线程返回值：" + futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
