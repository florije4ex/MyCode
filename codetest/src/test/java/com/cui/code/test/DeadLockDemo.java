package com.cui.code.test;

class MyLock
{
	static Object locka = new Object();
	static Object lockb = new Object();
}

class DeadLock implements Runnable
{
	private boolean flag;

	public DeadLock(boolean flag)
	{
		this.flag = flag;
	}

	@Override
	public void run()
	{
		if (flag)
		{
			while (true)
			{
				synchronized (MyLock.locka)
				{
					System.out.println("if Lock--a");
					synchronized (MyLock.lockb)
					{
						System.out.println("if Lock--b");

					}

				}
			}
		}

		else
		{
			while (true)
			{
				synchronized (MyLock.lockb)
				{
					System.out.println("else Lock--b");
					synchronized (MyLock.locka)
					{
						System.out.println("else Lock--a");

					}

				}
			}
		}
	}
}

public class DeadLockDemo
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread t1 = new Thread(new DeadLock(true));
		Thread t2 = new Thread(new DeadLock(true));

		t1.start();
		t2.start();
	}

}
