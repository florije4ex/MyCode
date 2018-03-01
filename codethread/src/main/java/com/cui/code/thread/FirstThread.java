package com.cui.code.thread;

/**
 * 线程实现方式一：extends Thread
 *
 * @author cuishixiang
 * @date 2018-02-28
 */
public class FirstThread extends Thread {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            //获取当前线程名
            System.out.println(Thread.currentThread().getName() + "：" + i);

            if (i == 20) {
                new FirstThread().start();
                new FirstThread().start();
            }
        }
    }

    private int i;

    //线程执行体
    @Override
    public void run() {
        for (; i < 100; i++) {
            //可直接通过getName获取当前线程名
            System.out.println(getName() + "：" + i);

        }
    }
}
