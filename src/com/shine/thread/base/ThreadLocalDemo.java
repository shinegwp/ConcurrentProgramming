package com.shine.thread.base;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午3:55:52
* @describe
*/
public class ThreadLocalDemo {
	private ThreadLocal<Integer> count = new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return new Integer(0);
		};
	};
	public int getNext () {
		Integer value = count.get();
		value++;
		count.set(value);
		return value;
	}
	public static void main(String[] args) {
		ThreadLocalDemo tld = new ThreadLocalDemo();
		new Thread (new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					System.out.println(Thread.currentThread().getName()+"正在执行"+tld.getNext());
					try {
						Thread.sleep(1000);
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
				while (true) {
					System.out.println(Thread.currentThread().getName() + " " + tld.getNext());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
