package com.cui.code.thread;

/**
 * 线程实现方式二：implements Runnable
 *
 * @author cuishixiang
 * @date 2018-02-28
 */
public class SecondThread implements Runnable {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            //获取当前线程名
            System.out.println(Thread.currentThread().getName() + "：" + i);

            if (i == 20) {
                SecondThread secondThread = new SecondThread();
                new Thread(secondThread, "线程1").start();
                new Thread(secondThread, "线程2").start();
            }
        }
    }


    private int i;

    @Override
    public void run() {
        for (; i < 100; i++) {
            //不能通过getName获取当前线程名
            //只能通过Thread.currentThread()先获取当前线程，再getName获取线程名
            System.out.println(Thread.currentThread().getName() + "：" + i);
        }
    }

}
