package com.shine.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午3:21:04
* @describe
*/
public class MyLock implements Lock{
	Thread lockBy = null;
	int lockCount = 0;
	private boolean isLocked = false;
	@Override
	public synchronized void lock() {
		Thread currentThread = Thread.currentThread();
		while (isLocked && currentThread != lockBy) {
			try{
				wait();
			}catch(Exception e){
				System.out.println("这里出现异常了");
			}
		}
		lockBy = currentThread;
		lockCount++;
		isLocked = true;
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
	}
	@Override
	public boolean tryLock() {
		return false;
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
	@Override
	public synchronized void unlock() {
		if (lockBy == Thread.currentThread()) {
			lockCount--;
			if (lockCount == 0) {
				notify();
				isLocked = false;
			}
		}
	}
	@Override
	public Condition newCondition() {
		return null;
	}
	public static void main(String[] args) {
		Sequencea s = new Sequencea();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());	
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());	
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());	
				}
			}
		}).start();
	}
}
class Sequencea{
	private int value;
	private MyLock  ml= new MyLock();
	public int getNext(){
	    ml.lock();
	    try{
	    	return value++;
	    } finally {
	    	ml.unlock();
	    }
		
	}
}