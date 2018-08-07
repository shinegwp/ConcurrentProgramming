package com.shine.concurrent.container;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月7日 上午9:55:13
* @describe
*/
public class ThreadPoolDemo {
	public static void main(String[] args) {
		//核心池的大小/最大线程池的大小/线程池保持存活的时间/单位/阻塞队列/handle
		//handler ： 线程池对拒绝任务的处理策略。 CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 50, 10, TimeUnit.DAYS, new ArrayBlockingQueue<>(10),new ThreadPoolExecutor.CallerRunsPolicy());
		AtomicInteger count = new AtomicInteger();//获取jdk提供的原子类来保证线程安全
		for(int i = 0; i < 100 ;i ++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
					count.getAndIncrement();
				}
			});
		}
		threadPool.shutdown();
		while(Thread.activeCount() > 1) {
		}
		System.out.println(count.get());
	}
}
