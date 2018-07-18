package cn.bobo.hive.annology.distribute;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AppSlave {
	ServerSocket server = null;
	InputStream in = null;

	public void conn() throws IOException {

		server = new ServerSocket(988);
		int step = 1;
		while (true) {
			Socket sc = server.accept();
			if (step == 1) {
				AppSlaveGetJarThread appSlaveGetJarThread = new AppSlaveGetJarThread(sc);
				appSlaveGetJarThread.start();
				step=2;
			} else {
				AppSlaveRunTaskThread appSlaveRunTaskThread = new AppSlaveRunTaskThread(sc);
				appSlaveRunTaskThread.start();
				step=3;
			}
		}

	}

	public static void main(String[] args) throws Exception {

		AppSlave appSlave = new AppSlave();
		appSlave.conn();
		Thread.sleep(50000);
	}

}
