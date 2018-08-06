package com.shine.thread.communication;

import java.util.concurrent.TimeUnit;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 上午11:38:39
* @describe
*/
public class SignalDemo {
	private volatile int signal;
	public synchronized void set (int signal) {
		notifyAll(); // notify方法会随机叫醒一个处于wait状态的线程
		 // notifyAll叫醒所有的处于wait线程，争夺到时间片的线程只有一个
		System.out.println("叫醒线程叫醒之后休眠开始...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized int get () {
		System.out.println(Thread.currentThread().getName() + " 方法执行了...");
		if(signal != 1) {
			try {
				wait();
				System.out.println("叫醒之后");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " 方法执行完毕...");
		return signal;
	}
	public static void main(String[] args) {
		SignalDemo cm = new SignalDemo();
		Target1 t1 = new Target1(cm);
		Target2 t2 = new Target2(cm);
		new Thread(t2).start();
		new Thread(t2).start();
		new Thread(t2).start();
		new Thread(t2).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(t1).start();
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (cm) {
					System.out.println("修改线程正在执行......");    
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cm.set(1);
					System.out.println("高伟鹏修改完状态了");
					cm.notify();
				}
			}
		}).start();
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				synchronized (cm) {
					//等待信号为1时开始执行，否则不执行
					while (cm.get() != 1) {
						try {
							cm.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					//当信号为1时执行代码
					System.out.println("模拟代码执行");
				}
			}
		}).start();*/
	}
}
class Target1 implements Runnable {
	private SignalDemo demo;
	public Target1(SignalDemo demo) {
		this.demo = demo;
	}
	@Override
	public void run() {
		demo.set(1);
	}
}
class Target2 implements Runnable {
	private SignalDemo demo;
	public Target2(SignalDemo demo) {
		this.demo = demo;
	}
	@Override
	public void run() {
		demo.get();
	}
}