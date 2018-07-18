package cn.bobo.hive.annology.distribute;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;


class AppMaster{
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		
		Socket socket = new Socket("127.0.0.1",988);
		OutputStream out = socket.getOutputStream();
		//发送命令，请求发送jar包
//		out.write("jar".getBytes());
		//发送jar包
		FileInputStream fi = new FileInputStream("c:/wc.jar");
		int b;
		while((b=fi.read())!=-1){
			out.write(b);
		}
		
		out.close();
		Thread.sleep(1000);
		
		//发送启动task启动命令
		socket = new Socket("127.0.0.1",988);
		out=socket.getOutputStream();
		out.write("java -cp d:\\wc.jar cn.itcast.bigdata.annology.distribute.AppTask".getBytes());
		out.close();
		
		socket.close();
		
	}
	
}



