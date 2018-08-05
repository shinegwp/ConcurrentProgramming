package com.shine.thread.base;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 上午10:28:43
* @describe
*/
public class ThreadPriority {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Target());
		Thread t2 = new Thread(new Target());
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		t2.start();
	}
}
class Target implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println(Thread.currentThread().getName());
		}
	}
}