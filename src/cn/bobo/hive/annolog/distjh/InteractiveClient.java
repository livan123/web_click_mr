package cn.bobo.hive.annolog.distjh;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InteractiveClient {

	public static final String IP = "localhost";//服务器地址 
	public static final int PORT = 8000;//服务器端口号  

	public static void main(String[] args) {  
		handler(); 
	}

	private static void handler(){
		try {
			//实例化一个Socket，并指定服务器地址和端口
			Socket client = new Socket(IP, PORT);
			//开启两个线程，一个负责读，一个负责写
			new Thread(new ClientReadHandlerThread(client)).start();
			new Thread(new ServerWriteHandlerThread(client)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}  

/*
 *处理读操作的线程 
 */
class ClientReadHandlerThread implements Runnable{
	private Socket client;

	public ClientReadHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true){
				//读取服务器端数据  
				dis = new DataInputStream(client.getInputStream());
				String receive = dis.readUTF();   
				System.out.println("服务器端返回过来的是: " + receive);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
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
class WriteHandlerThread implements Runnable{
	private Socket client;

	public WriteHandlerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataOutputStream dos = null;
		BufferedReader br = null;
		try {
			while(true){
				//取得输出流
				dos = new DataOutputStream(client.getOutputStream());
				System.out.print("请输入: \t");  
				//键盘录入
				br = new BufferedReader(new InputStreamReader(System.in));
				String send = br.readLine();  
				//发送数据
				dos.writeUTF(send);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try{
				if(dos != null){
					dos.close();
				}
				if(br != null){
					br.close();
				}
				if(client != null){
					client = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
