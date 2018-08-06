package com.shine.thread.future;

import java.util.Random;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午8:03:19
* @describe
*/
public class MyFutureDemo {
public static void main(String[] args) {
		ProductFactory pf = new ProductFactory();
		// 下单，交钱
		Future f = pf.createProduct("蛋糕");
		System.out.println("我去上班了，下了班我来取蛋糕...");
		// 拿着订单获取产品
		System.out.println("我拿着蛋糕回家." + f.get());
	}
}
class ProductFactory {
	public Future createProduct(String name) {
		Future f = new Future(); // 创建一个订单
		System.out.println("下单成功，你可以去上班了》。。");
		// 生产产品
		new Thread(new Runnable() {
			@Override
			public void run() {
				Product p = new Product(new Random().nextInt(), name);
				f.setProduct(p);
			}
		}).start();
		return f;
	}
}
class Future {//订单，包含产品和下单两个属性
	private Product product;
	private boolean down;
	public synchronized void setProduct (Product product) {//生产产品
		if(down) {
			return;
		}
		this.product = product;
		this.down = true;
		notifyAll();
	}
	public synchronized Product get () {//获取产品
		while(!down) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return product;
	}
}
class Product {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
	public Product(int id, String name) {
		System.out.println("开始生产 " + name);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.id = id;
		this.name = name;
		System.out.println(name + " 生产完毕");
	}
}

