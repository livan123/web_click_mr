package src.cn.bobo.hive.annolog.dist.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


//java -cp c:/slave2.jar cn.itcast.bigdata.annolog.dist.hdfs.AppSlave
public class AppSlave {
	ServerSocket server = null;
	InputStream in = null;

	public void conn() throws IOException {

		server = new ServerSocket(987);
		while (true) {
			Socket sc = server.accept();
			AppSlaveReceiveBlock appSlaveReceiveBlock = new AppSlaveReceiveBlock(sc);
			appSlaveReceiveBlock.start();
		}

	}

	public static void main(String[] args) throws Exception {

		AppSlave appSlave = new AppSlave();
		appSlave.conn();
		Thread.sleep(50000);
	}

}
