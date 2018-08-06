package com.shine.thread.communication;

import java.util.concurrent.Exchanger;


/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午7:11:04
* @describe
*/
public class ExchangerDemo {
//抓取a的数据
public void a (Exchanger<String> exch) {//获取exchanger对象
		System.out.println("a 方法执行...");
		try {
			System.out.println("a 线程正在抓取数据...");
			Thread.sleep(2000);
			System.out.println("a 线程抓取到数据...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String res = "12345";
		try {
			System.out.println("a 等待对比结果...");
			exch.exchange(res);//抓取数据
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	//抓取b的数据
	public void b (Exchanger<String> exch) {
		System.out.println("b 方法开始执行...");
		try {
			System.out.println("b 方法开始抓取数据...");
			Thread.sleep(4000);
			System.out.println("b 方法抓取数据结束...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String res = "12345";
		try {
			String value = exch.exchange(res);//抓取数据
			System.out.println("开始进行比对...");
			System.out.println("比对结果为：" + value.equals(res));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ExchangerDemo d = new ExchangerDemo();
		Exchanger<String> exch = new Exchanger<>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.a(exch);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.b(exch);
			}
		}).start();
	}
}
