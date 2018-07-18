package cn.bobo.hive.annolog.distjh;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class InteractiveServer {

	public static final int PORT = 8000;//监听的端口号   

	public static void main(String[] args) {  
		InteractiveServer server = new InteractiveServer();  
		server.init();  
	}  

	public void init() {  
		ServerSocket serverSocket = null;
		try {  
			serverSocket = new ServerSocket(PORT);  
			while (true) {  
				Socket client = serverSocket.accept();  
				//一个客户端连接就开户两个线程处理读写  
				new Thread(new ReadHandlerThread(client)).start();  
				new Thread(new WriteHandlerThread(client)).start(); 
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{
			try {
				if(serverSocket != null){
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}  
}  

/*
 *处理读操作的线程 
 */
class ReadHandlerThread implements Runnable{
	private Socket client;

	public ReadHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try{
			while(true){
				//读取客户端数据  
				dis = new DataInputStream(client.getInputStream());
				String reciver = dis.readUTF();
				System.out.println("客户端发过来的内容:" + reciver); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(dis != null){
					dis.close();
				}
				if(client != null){
					client = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * 处理写操作的线程
 */
class ServerWriteHandlerThread implements Runnable{
	private Socket client;

	public ServerWriteHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataOutputStream dos = null;
		BufferedReader br = null;
		try{
			while(true){
				//向客户端回复信息  
				dos = new DataOutputStream(client.getOutputStream());  
				System.out.print("请输入:\t");  
				// 键盘录入  
				br = new BufferedReader(new InputStreamReader(System.in));
				String send = br.readLine();  
				//发送数据
				dos.writeUTF(send);  
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(dos != null){
					dos.close();
				}
				if(br != null){
					br.close();
				}
				if(client != null){
					client = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}