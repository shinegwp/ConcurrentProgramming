package com.shine.thread.designermodel;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午12:11:51
* @describe
*/
public class Factory {
	private int count;
	private final int MAX_COUNT = 10;
	public synchronized void push (){
		if (count > MAX_COUNT) {
			try {
				System.out.println(Thread.currentThread().getName()+"库存达到上限，无法进行生产");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count++;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+"生产，当前库存为："+count);
		
	}
	public synchronized void take() {
		if (count <= 0) {
			try {
				System.out.println("库存为零"+Thread.currentThread().getName()+"进行等待");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count--;
		notifyAll();
		System.out.println(Thread.currentThread().getName()+"执行成功，剩余库存为："+count);
	}
}
