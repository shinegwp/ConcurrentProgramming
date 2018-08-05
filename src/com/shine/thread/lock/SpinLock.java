package com.shine.thread.lock;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午2:05:16
* @describe
*/
//多个线程执行完毕之后打印一句话
public class SpinLock {
	public static void main(String[] args) {
		new Thread (new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName()+"线程开始执行");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕");
			}
		}).start();
		new Thread (new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName()+"线程开始执行");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"线程执行完毕");
			}
		}).start();
		while (Thread.activeCount() != 1) {
			//自旋等待
		}
		System.out.println("所有线程执行完毕");
	}
}
