package com.shine.database.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @author 高伟鹏 
* @email gaoweipeng3@gmail.com
* @version 创建时间：2018年8月6日 下午3:11:07
* @describe
*/
public class MyDataSource {
	private LinkedList<Connection> pool = new LinkedList<>();//链接链表
	private static final int INIT_CONNECTIONS = 10;//初始化数值
	private static final String DRIVER_CLASS = "";//驱动
	private static final String USER = "";//用户名
	private static final String PASSWORD = "";//密码
	private static final String URL = "";//数据库
    //获取锁
	private Lock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public MyDataSource() {//初始化链接
		for (int i = 0; i < INIT_CONNECTIONS; i++) {
			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				pool.addLast(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Connection getConnect() {//获取链接
		Connection result = null;
		lock.lock();
		try {
			while (pool.size() <= 0) {
				try {
					c1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!pool.isEmpty()) {
				result = pool.removeFirst();
			}
			return result;
		} finally {
			lock.unlock();
		}
	}
	public void release(Connection conn) {//释放链接
		if (conn != null) {
			lock.lock();
			try {
				pool.addLast(conn);
				c1.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
