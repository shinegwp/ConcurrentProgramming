package com.shine.thread.communication;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午3:43:36
* @describe
*/
public class JoinDemo {

	public void a(Thread joinThread) {
		System.out.println("方法a执行了");
		joinThread.start();
		
		try {
			joinThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("a方法执行完毕");
	}
	
	public void b () {
		System.out.println("加塞线程执行开始");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("加塞线程执行完毕");
	}
	
	public static void main(String[] args) {
		JoinDemo jd = new JoinDemo();
		
		Thread joinThread = new Thread(new Runnable() {
			@Override
			public void run() {
				jd.b();
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jd.a(joinThread);
			}
		}).start();;
	}
}
