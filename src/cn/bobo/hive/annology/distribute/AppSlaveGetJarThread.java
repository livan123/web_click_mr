package cn.bobo.hive.annology.distribute;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class AppSlaveGetJarThread extends Thread {
	Socket sc = null;
	InputStream in = null;

	public AppSlaveGetJarThread(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try {
			InputStream in = sc.getInputStream();
			 FileOutputStream fo = new FileOutputStream("d:/wc.jar");
			 IOUtils.copy(in, fo);
			 sc.close();

		} catch (Exception exception) {
		}

	}

}
