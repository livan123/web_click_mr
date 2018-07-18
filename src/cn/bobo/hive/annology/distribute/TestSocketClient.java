package cn.bobo.hive.annology.distribute;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket sc = new Socket("127.0.0.1", 988);
		OutputStream os = sc.getOutputStream();
		os.write("hello".getBytes());
		os.close();
		sc.close();
	}

}
