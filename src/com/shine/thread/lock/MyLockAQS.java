package com.shine.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author 高伟鹏
 * @email gaoweipeng3@gmail.com
 * @version 创建时间：2018年8月5日 下午8:34:33
 * @describe
 */
public class MyLockAQS implements Lock {

	Helper helper = new Helper();

	private class Helper extends AbstractQueuedSynchronizer {
		@Override
		protected boolean tryAcquire(int arg) {
			// 如果第一个线程进来可以拿到锁，则我们返回true
			// 如果第二个线程进来拿不到锁，返回false，有种特例，若进来的线程和当前保存的线程是同一个线程，可以拿到锁，并更新状态值
			// 如何判断是第几个线程进入
			int state = getState();
            Thread t = Thread.currentThread();
			if (state == 0) {
				if (compareAndSetState(0, arg)) {
					setExclusiveOwnerThread(Thread.currentThread());
					return true;
				}
			} else if (getExclusiveOwnerThread() == t) {
				setState(state+1);
				return true;
			}
			return false;
		}
		@Override
		protected boolean tryRelease(int arg) {
			// 锁的获取和释放肯定是一一对应的，那么调用此方法的线程 一定是当前线程
			if (Thread.currentThread() != getExclusiveOwnerThread()) {
				throw new RuntimeException();
			}
			int state = getState() - arg;
			boolean flag = false;
			if (state == 0) {
				flag = true;
				setExclusiveOwnerThread(null);
			}
			setState(state);
			return flag;
		}
		Condition newConditionObject() {
			return new ConditionObject();
		}
	}
	@Override
	public void lock() {
		helper.acquire(1);
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		helper.acquireInterruptibly(1);
	}
	@Override
	public boolean tryLock() {
		return helper.tryAcquire(1);
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return helper.tryAcquireNanos(1, unit.toNanos(time));
	}
	@Override
	public void unlock() {
		helper.release(1);
	}
	@Override
	public Condition newCondition() {
		return helper.newConditionObject();
	}
	public static void main(String[] args) {
		Sequenceb s = new Sequenceb();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName() + ":" + s.getNext());
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName() + ":" + s.getNext());
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println(Thread.currentThread().getName() + ":" + s.getNext());
				}
			}
		}).start();
	}
}
class Sequenceb {
	private int value;
	private MyLockAQS ml = new MyLockAQS();
	public int getNext() {
		ml.lock();
		try {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value++;
		} finally {
			ml.unlock();
		}
	}
}