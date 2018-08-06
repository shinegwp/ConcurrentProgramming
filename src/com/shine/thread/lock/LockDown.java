package com.shine.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 上午10:44:33
* @describe
*/
public class LockDown {
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	private Lock r = rwl.readLock();
	private Lock w = rwl.writeLock();
	private volatile boolean isUpdate;
	private Map<String, Object> map = new HashMap<>();
	public void readWrite () {
		r.lock();//保证isUpdate能够拿到最新的值
		
		if (isUpdate) {
			r.unlock();
			w.lock();
			map.put("xxx", "xxx");
			r.lock();//写锁未释放时，加上读锁
			w.unlock();
		}
		Object obj = map.get("xxx");
		System.out.println(obj);
		r.unlock();
	}
	public Object get(String key){
		r.lock();
		System.out.println(Thread.currentThread().getName()+"正在执行读锁");
		try {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this.map.get(key);
		} finally {
			r.unlock();
			System.out.println(Thread.currentThread().getName()+"读锁执行完毕");
		}
	}
	public void put(String key, Object obj){
		w.lock();
		System.out.println(Thread.currentThread().getName()+"正在执行写锁");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.map.put(key, obj);
		w.unlock();
		System.out.println(Thread.currentThread().getName()+"写锁执行完毕");
	}
	public static void main(String[] args) {
		LockDown r = new LockDown();
		new Thread(new Runnable(){
			@Override
			public void run() {
				r.put("1", "11");
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println(r.get("1"));
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				r.put("3", "33");
			}
		}).start();
	}
}
