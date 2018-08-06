package com.shine.thread.communication;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午6:42:54
* @describe
*/
public class CyclicBarrierDemo {
	Random random = new Random();
	public void meeting(CyclicBarrier barrier) {
		try {
			Thread.sleep(random.nextInt(4000));//等待几秒
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " 到达会议室，等待开会..");
		if(Thread.currentThread().getName().equals("Thread-7")) {
			try {
				Thread.sleep(5000);//指定7号线程休眠5秒
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			barrier.await();//所有线程都调用此方法之前进行等待
			barrier.reset();//将屏障重置为初始状态
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CyclicBarrierDemo demo = new CyclicBarrierDemo();
		CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {
			@Override
			public void run() {
				System.out.println("好！我们开始开会...");
			}
		});
		for (int i = 0; i < 10; i++) {//创建十个线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					demo.meeting(barrier);
				}
			}).start();
		}
		// 监控等待线程数
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("等待的线程数 " + barrier.getNumberWaiting());
					System.out.println("is broken " + barrier.isBroken());
				}
			}
		}).start();
	}
}
