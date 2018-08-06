package com.shine.thread.designermodel;
/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午1:50:17
* @describe
*/
public class Main {
	public static void main(String[] args) {
		Factory factory = new Factory();
		PushTarget p = new PushTarget(factory);
		TakeTarger t = new TakeTarger(factory);
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(t).start();
	}
}
