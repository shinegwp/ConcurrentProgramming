package com.shine.thread.communication;

import java.util.concurrent.Semaphore;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午7:03:57
* @describe
*/
public class SemaphoreDemo {

public void method (Semaphore semaphore) {
		try {
			semaphore.acquire();//从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " is run ...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();//释放一个许可，将其返回给信号量。
	}
	public static void main(String[] args) {
		SemaphoreDemo d = new SemaphoreDemo();
		Semaphore semaphore = new Semaphore(10);//获取具有十个许可证的信号计数量
		while(true) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					d.method(semaphore);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
