package com.shine.thread.lock;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午2:19:56
* @describe
*/
public class Volatile {
    //通过锁来保证可见性的前提：多个线程是拿的同一把锁
	public volatile boolean run = false;
	public static void main(String[] args) {
		Volatile d = new Volatile();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					System.err.println("执行了第 " + i + " 次");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				d.run = true;
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!d.run) {
					// 不执行
				}
				System.err.println("线程2执行了...");
			}
		}).start();
	}
}
