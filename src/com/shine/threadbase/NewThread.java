package com.shine.threadbase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月4日 下午7:17:51
* @describe 创建线程的方式
*/


//Thread的源码部分内容解析
public class NewThread extends Thread{
    /**
     * 
     * @param g            所属的线程组，用于对线程进行分组的，对线程进行批量操作
     * @param target       所指定的线程任务
     * @param name         线程的名字
     * @param stackSize    指定栈的大小，供虚拟机使用
     * @param acc
     */
	/*private void init(ThreadGroup g, Runnable target, String name,
            long stackSize, AccessControlContext acc) {
	
	}*/
	
	/**
	 * 线程执行完本次任务后中断线程，并使线程释放所有资源，与interrupeted与isInterrupeted配合使用
	 */
	/*@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
	}*/
	/**
	 * thread类中中的run方法
	 */
	/*public void run() {
        if (target != null) {
            target.run();
        }
    }*/
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			try {
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName()+"作为一个守护线程");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		NewThread thread = new NewThread();
		
		thread.setDaemon(true);//设置为守护线程,程序中后台的调用，当程序执行完毕，即使他执行不完毕，也会退出
		
		thread.start();
		
		try {
			long start = System.currentTimeMillis();
			thread.sleep(1000);
			System.out.println("程序执行完毕,共执行"+(System.currentTimeMillis() - start)+"毫秒");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}

//第一种方式，实现runnable接口
//runnable接口中只有一个run方法
//作为一个线程的任务存在
class NewThread1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true) {
			//演示sleep
			
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()+"开始工作了");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	
	
	public static void main(String[] args) {
		Thread thread = new Thread(new NewThread());
		
		thread.setName("伟鹏");
		thread.start();
		System.out.println("main的时间");
	}
	
}

//第二种继承Thread类
class NewThread2 extends Thread{
	@Override
	
	public synchronized void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				wait();
				System.out.println(Thread.currentThread().getName()+"又开始工作了");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread thread1 = new NewThread2();
		thread1.setName("伟鹏啊");
		thread1.start();
		while (true) {
			System.out.println("main的时间");
			synchronized (thread1) {
				
				try {
					Thread.sleep(1000);
					thread1.notify();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}

//第三种
//匿名内部类
class NewThread3{
	public static void main(String[] args) {
		new Thread () {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				System.out.println("匿名内部类实现");
			}
		}.start();
		
		new Thread (new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("接口");
			}
			
		}).start();
		
		//动态分派
		new Thread (new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("father");
			}
			
		}) {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("sub");
			}
		}.start();
	}
}

//第四种  实现Callable接口，具有返回值和抛异常
//Callable中只有一个call方法,类似于run方法
class NewThread4 implements Callable<Integer>{
    
	
	public static void main(String[] args) {
		NewThread4 t4 = new NewThread4();
		//对线程任务进行封装，实现了runnable接口
		FutureTask<Integer> task = new FutureTask<>(t4);
		
		Thread t = new Thread(task);
		
		t.start();
		
		try {
			Integer result = task.get();
			System.out.println("线程执行的结果为"+result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("正在忙碌的敲代码");
		return 1;
	}
	
}

//第五种  定时器Timer(可用quartz框架)
class NewThread5{
	
	
	public static void main(String[] args) {
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("高伟鹏正在努力工作啊！"+System.currentTimeMillis());
			}
			
		}, 0, 1000);
	}
}


//第六种  使用线程池（Executor）来实现，使用线程，从线程中拿，用完了再放回去；等于拿空间换时间
class NewThread6{
	
	public static void main(String[] args) {
		Executor threadPool = Executors.newFixedThreadPool(10);//创建一个有10个线程的线程池
		
		threadPool.execute(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("高伟鹏在努力工作啊"+new Date().toLocaleString());
			}
			
		});
		
		
		Executor threadPool1 = Executors.newCachedThreadPool();
		//会创建出一部分线程，认位不够用就创建，认为够用就回收
		for (int i = 0; i< 1000; i++) {
			threadPool1.execute(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("高伟鹏在努力工作啊"+new Date().toLocaleString()+Thread.currentThread().getName());
				}
				
			});
		}
	}
}
//使用Lambda表达式实现
class NewThread7{
	
	public int add (List<Integer> values) {
		values.parallelStream().forEach(System.out::println);
		return values.parallelStream().mapToInt(a -> a).sum();
	}
	public static void main(String[] args) {
		List<Integer> values = Arrays.asList(10,20,30,40);
		
		int res = new NewThread7().add(values);
		System.out.println("计算结果为：" + res);
	}
	
	
}