package com.shine.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 高伟鹏
 * @email gaoweipeng3@gmail.com
 * @version 创建时间：2018年8月6日 下午7:52:12
 * @describe
 */
public class ThreadFuture {
	/**
	 * Callalbe和Runnable的区别
	 * Runnable run方法是被线程调用的，在run方法是异步执行的
	 * Callable的call方法，不是异步执行的，是由Future的run方法调用的
	 * @param args
	 */
	public static void main(String[] args){
		Callable<Integer> call = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("正在计算结果...");//正在执行长时间的任务
				Thread.sleep(3000);
				return 1;
			}
		};
		FutureTask<Integer> task = new FutureTask<>(call);//获取call的future对象
		Thread thread = new Thread(task);
		thread.start();//运行线程
		System.out.println(" 干点别的...");//线程运行期间进行其他事情
		Integer result = null;
		try {
			result = task.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("拿到的结果为：" + result);
	}
}
