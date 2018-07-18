package cn.bobo.hive.annology.distribute;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class AppSlaveRunTaskThread extends Thread{
	Socket sc = null;
	InputStream in = null;

	public AppSlaveRunTaskThread(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try {
			InputStream in = sc.getInputStream();
			byte[] buf = new byte[4096];
			int b = in.read(buf);
			String res = new String(buf, 0, b);
			System.out.println(res);

			Process exec = Runtime.getRuntime().exec(res);
			InputStream inputStream = exec.getInputStream();
			// InputStream errorStream = process.getErrorStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String inLine = null;
			while ((inLine = bufferedReader.readLine()) != null) {
				System.out.println(inLine);
			}

		} catch (Exception exception) {
		}

	}

	
	

}
