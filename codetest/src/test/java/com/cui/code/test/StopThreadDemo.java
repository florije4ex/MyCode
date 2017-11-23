package com.cui.code.test;

public class StopThreadDemo
{
	public static void main(String[] args)
	{
		StopThread stopThread = new StopThread();
		Thread t1 = new Thread(stopThread);
		Thread t2 = new Thread(stopThread);

		t1.start();
		t2.start();

		int num = 0;

		while (true)
		{
			if (num++ == 50)
			{
				t1.interrupt();
				t2.interrupt();
				break;
			}

			System.out.println(Thread.currentThread().getName() + "..." + num);
		}
		System.out.println("--over--");
	}

}

class StopThread implements Runnable
{
	private boolean flag = true;

	@Override
	public synchronized void run()
	{
		while (flag)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				System.out.println(Thread.currentThread().getName() + "...Exception");
				flag = false;
			}
		}
	}
}