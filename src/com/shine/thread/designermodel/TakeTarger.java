package com.shine.thread.designermodel;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午1:47:35
* @describe
*/
public class TakeTarger implements Runnable{
	private Factory factory;
	public TakeTarger(Factory factory) {
		// TODO Auto-generated constructor stub
		this.factory = factory;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			factory.take();
            try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
}
