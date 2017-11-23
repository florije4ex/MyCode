/*
package cui;

class Person
{
	String name;
	String sex;
	boolean flag = false;
}

class Input implements Runnable
{
	Person person;

	public Input(Person person)
	{
		this.person = person;
	}

	public void run()
	{
		boolean bool = true;
		while (true)
		{
			synchronized (person)
			{

				if (person.flag)
				{
					try
					{
						person.wait();
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bool)
				{
					person.name = "小三";
					person.sex = "男";
				} else
				{
					person.name = "xiaosi";
					person.sex = "WOMAN";
				}
				bool = !bool;
				
				person.flag = true;
				person.notify();
			}

		}

	}

}

class Output implements Runnable
{
	Person person;

	public Output(Person person)
	{
		this.person = person;
	}

	public void run()
	{
		while (true)
		{
			synchronized (person)
			{
				if (!person.flag)
				{
					try
					{
						person.wait();
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(person.name + "..." + person.sex);

				person.flag=false;
				person.notify();
			}

		}

	}

}

public class InputOutputDemo
{


	public static void main(String[] args)
	{

		Person person = new Person();

		Input input = new Input(person);
		Output out = new Output(person);

		Thread t1 = new Thread(input);
		Thread t2 = new Thread(out);

		t1.start();
		t2.start();
	}
}
 */

//代码优化：

package com.cui.code.test;

class Person
{
	private String name;
	private String sex;
	private boolean flag = false;

	public synchronized void set(String name, String sex)
	{
		if (flag)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.name = name;
		this.sex = sex;
		
		flag = true;
		this.notify();

	}

	public synchronized void out()
	{
		if (!flag)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(name + "..." + sex);

		flag = false;
		this.notify();
	}
}

class Input implements Runnable
{
	Person person;

	public Input(Person person)
	{
		this.person = person;
	}

	public void run()
	{
		boolean bool = true;
		while (true)
		{
			if (bool)
				person.set("小三", "男");
			 else
				person.set("xiaosi", "WOMAN");
			bool = !bool;
		}
	}
}

class Output implements Runnable
{
	Person person;

	public Output(Person person)
	{
		this.person = person;
	}

	public void run()
	{
		while (true)
		{
			person.out();
		}
	}
}

public class InputOutputDemo
{

	public static void main(String[] args)
	{
		Person person = new Person();

		new Thread(new Input(person)).start();
		new Thread(new Output(person)).start();
	}
}
