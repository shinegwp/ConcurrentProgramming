package com.shine.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午2:49:08
* @describe
*/
public class AtomicClass {
	public static void main(String[] args) {
		Sequence se = new Sequence();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println(Thread.currentThread().getName() + "正在执行" + se.getNext());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println(Thread.currentThread().getName() + "正在执行" + se.getNext());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println(Thread.currentThread().getName() + "正在执行" + se.getNext());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
class Sequence {
	private AtomicInteger value = new AtomicInteger();
	public int getNext() {
		return value.getAndIncrement();
	}
}
