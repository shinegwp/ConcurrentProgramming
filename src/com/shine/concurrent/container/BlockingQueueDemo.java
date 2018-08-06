package com.shine.concurrent.container;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午9:35:17
* @describe
*/
public class BlockingQueueDemo {

public static void main(String[] args) {
		Shop tmall = new Tmall();
		PushTarget p = new PushTarget(tmall);
		TakeTarget t = new TakeTarget(tmall);
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				tmall.size();				
			}
		}).start();
	}
}
interface Shop {
	public void push ();
	public void take ();
	public void size() ;
}
class PushTarget implements Runnable {
	private Shop tmall;
	public PushTarget(Shop tmall) {
		this.tmall = tmall;
	}
	@Override
	public void run() {
		while(true) {
			tmall.push();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class TakeTarget implements Runnable {
	private Shop tmall;
	public TakeTarget(Shop tmall) {
		this.tmall = tmall;
	}
	@Override
	public void run() {
		while(true) {
			tmall.take();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class Tmall implements Shop {
	public final int MAX_COUNT = 10;
	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(MAX_COUNT);//获取阻塞队列
	public void push() {
		try {
			queue.put(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void take() {
		try {
			queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void size() {
		while (true) {
			System.out.println("当前队列的长队为：" + queue.size());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
