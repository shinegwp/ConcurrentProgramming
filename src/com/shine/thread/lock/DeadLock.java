package com.shine.thread.lock;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午2:10:53
* @describe
*/
public class DeadLock {
	private Object obj1 = new Object();
	private Object obj2 = new Object();
	public void a () {
		synchronized (obj1) {
			try {
				Thread.sleep(100);
				synchronized (obj2) {
					System.out.println("g高伟鹏在A中");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void b () {
		synchronized (obj2) {
			try {
				Thread.sleep(100);
				synchronized (obj1) {
					System.out.println("g高伟鹏在A中");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		DeadLock dl = new DeadLock();
		new Thread (new Runnable () {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dl.a();
			}
		}).start();
		new Thread (new Runnable () {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dl.b();
			}
		}).start();
	}
}
