package com.shine.thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月7日 下午2:58:55
* @describe
*/
public class StampedLockDemp {

	private StampedLock lock = new StampedLock();
	
	private int balance;
	
public void conditionReadWrite (int value) {
		// 首先判断balance的值是否符合更新的条件
		long stamp = lock.readLock();
		while (balance > 0) {
			long writeStamp = lock.tryConvertToWriteLock(stamp);
			if(writeStamp != 0) { // 成功转换成为写锁
				stamp = writeStamp;
				balance += value;
				break;
			} else {
				// 没有转换成写锁，这里需要首先释放读锁，然后再拿到写锁
				lock.unlockRead(stamp);
				// 获取写锁
				stamp = lock.writeLock();
			}
 		}
		lock.unlock(stamp);
	}
	public void optimisticRead () {
		long stamp = lock.tryOptimisticRead();//获取乐观锁
		System.out.println(balance);
		//这里可能进行了写操作，所以要进行判断
		if (!lock.validate(stamp)) {
			//重新读取
			long readStamp = lock.readLock();
			System.out.println(balance);
			stamp = readStamp;
		}
		lock.unlockRead(stamp);
	}
	public void read () {
		long stamp = lock.readLock();//读锁
		System.out.println(balance);
		lock.unlockRead(stamp);
	}
	public void write (int value) {
		long stamp = lock.writeLock();//写锁
		balance += value;
		lock.unlockWrite(stamp);
	}
}
