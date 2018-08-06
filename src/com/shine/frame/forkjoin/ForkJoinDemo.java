package com.shine.frame.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午8:23:11
* @describe
*/
public class ForkJoinDemo extends RecursiveTask<Integer> {
	private int begin;
	private int end;
	public ForkJoinDemo(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
	@Override
	protected Integer compute() {
		System.out.println(Thread.currentThread().getName() + " ... ");
		int sum = 0;
		// 拆分任务
		if (end - begin <= 2) {
			// 计算
			for (int i = begin; i <= end; i++) {
				sum += i;
			}
		} else {
			// 拆分
			ForkJoinDemo d1 = new ForkJoinDemo(begin, (begin + end) / 2);
			ForkJoinDemo d2 = new ForkJoinDemo((begin + end)/2 + 1, end);
			// 执行任务
			d1.fork();
			d2.fork();
			Integer a = d1.join();//合并
			Integer b = d2.join();
			sum = a + b;
		}
		return sum;
	}
	public static void main(String[] args) throws Exception {
		ForkJoinPool pool = new ForkJoinPool(3);//获取ForkJoin的线程池，并指定大小为3
		Future<Integer> future = pool.submit(new ForkJoinDemo(1, 1000000000));//提交任务
		System.out.println("....");
		System.out.println("计算的值为：" + future.get());
	}
}
