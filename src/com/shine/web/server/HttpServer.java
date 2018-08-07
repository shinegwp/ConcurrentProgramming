package com.shine.web.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author 高伟鹏
 * @email gaoweipeng3@gmail.com
 * @version 创建时间：2018年8月7日 上午10:52:17
 * @describe
 */
public class HttpServer {
	public static void main(String[] args) {
		//创建线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		try {
			//创建服务器的端口
			ServerSocket ss = new ServerSocket(8888);
			//不停的接受客户端请求
			while (!Thread.interrupted()) {
				Socket client = ss.accept();
				pool.execute(new ServerThread(client));//向线程池中提交任务
//				new Thread (new ServerThread(client)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class ServerThread implements Runnable {
	private static Map<String,String> contentMap = new HashMap<>();//存储对应文件类型的扩展名
	static {
		contentMap.put("html", "text/html;charset = utf-8");
		contentMap.put("jpg", "image/jpeg");
	}
	private Socket client;
	private InputStream is;//输入流
	private OutputStream os;//输出流
	public ServerThread(Socket client) {
		// TODO Auto-generated constructor stub
		this.client = client;
		init();//初始化

	}
	private void init() {
		// TODO Auto-generated method stub
		// 获取输入输出流

		try {
			is = client.getInputStream();
			os = client.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//设置根目录
    private static final String webroot = "C:\\shinegwp\\eclipseT\\eclipseworkspace\\ConcurrentProgramming\\src\\com\\shine\\web\\server\\";
	private void go() throws IOException {
		// 读取请求内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = reader.readLine().split(" ")[1].replace("/", "\\");
		if (line.equals("\\")) {
			line += "gwp.html";//主页
		}
		System.out.println(line);
		// 给用户响应
		PrintWriter pw = new PrintWriter(os);
		InputStream i = new FileInputStream(webroot+line);
//		BufferedReader br = new BufferedReader(new InputStreamReader(i));
		//编写消息头
		pw.println("http/1.1 200 OK");
		pw.println("Content-Type:"+contentMap.get(line.substring(line.lastIndexOf(".")+1,line.length())));
		pw.println("Content-Length:" + i.available());
		pw.println();
		pw.flush();
		//获取响应内容
		byte[] buff = new byte[1024];
		int len = 0;
		while ((len = i.read(buff)) != -1) {
			os.write(buff, 0, len);
		}
		pw.flush();
        //关闭流与端口
		pw.close();
		i.close();
		reader.close();
		client.close();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			go();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
