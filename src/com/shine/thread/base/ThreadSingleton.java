package com.shine.thread.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 上午11:31:52
* @describe
*/
class Singleton {
    //出现安全问题的三个条件
	/*
	 * 1.多线程
	 * 2.共享资源
	 * 3.非原子性操作
	 */
	//所以饿汉式没有线程安全问题，但是饿汉式存在的问题就是不管是否用到该类，类都会被实例化，占用内存空间
	private Singleton() {
		// TODO Auto-generated constructor stub
	}
//	private static Singleton instance = new Singleton();//这是饿汉式
	/*public static Singleton getInstance () {
		return instance;
	}*/
	//下面为懒汉式
	private volatile static Singleton instance;//volatile可以阻止指令重排序
	//加synchronized锁来保证线程安全，但不要把synchronized挡在方法上，而是放在代码中,放在代码上会形成重量级锁，代码中只是轻量级锁
	public  static Singleton getInstance () {
		if (instance == null){
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();//当进行指令重排序时可能还是会出现线程安全问题
				}
			}
		}
		return instance;
	}
}
public class ThreadSingleton{
	public static void main(String[] args) {
		ExecutorService  threadPool = Executors.newFixedThreadPool(20);
		for (int i =0; i< 20; i++) {
			threadPool.execute(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName()+":"+Singleton.getInstance());
				}
			});
		}
	}
}