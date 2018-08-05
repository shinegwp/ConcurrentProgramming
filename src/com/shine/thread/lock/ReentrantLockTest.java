package com.shine.thread.lock;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月5日 下午1:54:22
* @describe
*/
public class ReentrantLockTest {
    //线程获取到锁之后，任然可以进入其他加锁部分
	public synchronized void a () {
		System.out.println("高伟鹏在a中疯狂工作");
		b();
	}
	public synchronized void b () {
		System.out.println("高伟鹏在b中疯狂工作");
	}
	public static void main(String[] args) {
		new Thread (new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ReentrantLockTest rl = new ReentrantLockTest();
				rl.a();
			}
		}).start();
	}
}
