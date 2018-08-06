package com.shine.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午2:16:46
* @describe
*/
public class ConditionTest {
	private int signal;
	Lock lock = new ReentrantLock();
	Condition a = lock.newCondition();
	Condition b = lock.newCondition();
	Condition c = lock.newCondition();
	public  void a () {
		lock.lock();
		while (signal != 0) {
			try {
				a.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("a");
		signal++;
		b.signal();
		lock.unlock();
	}
	public  void b () {
		lock.lock();
		while (signal != 1 ){
			try {
				b.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("b");
		signal++;
		c.signal();
		lock.unlock();
	}
	public  void c () {
		lock.lock();
		while (signal != 2 ){
			try {
				c.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("c");
		signal=0;
		a.signal();
		lock.unlock();
	}
	public static void main(String[] args) {
		ConditionTest ct = new ConditionTest();
		A a = new A(ct);
		B b = new B(ct);
		C c = new C(ct);
		new Thread(a).start();
		new Thread(b).start();
		new Thread(c).start();
	}
}
class A implements Runnable {
    private ConditionTest ct;
    public A(ConditionTest ct) {
    	this.ct = ct;
	}
	@Override
	public void run() {
		while (true) {
			ct.a();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class B implements Runnable {
	private ConditionTest ct;
	public B(ConditionTest ct) {
		this.ct = ct;
	}
	@Override
	public void run() {
		while (true) {
			ct.b();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class C implements Runnable {
	private ConditionTest ct;
	public C(ConditionTest ct) {
		this.ct = ct;
	}
	@Override
	public void run() {
		while (true) {
			ct.c();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}