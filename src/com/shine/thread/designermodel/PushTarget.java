package com.shine.thread.designermodel;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午12:13:39
* @describe
*/
public class PushTarget implements Runnable{
	private Factory factory;
	public PushTarget(Factory factory) {
		// TODO Auto-generated constructor stub
		this.factory = factory;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			factory.push();
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
}
