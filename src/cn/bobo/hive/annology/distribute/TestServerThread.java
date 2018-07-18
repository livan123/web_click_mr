package cn.bobo.hive.annology.distribute;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class TestServerThread extends Thread {
	Socket sc = null;

	public TestServerThread(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try {
			InputStream in = sc.getInputStream();
			List<String> lines = IOUtils.readLines(in);
			for (String line : lines) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
