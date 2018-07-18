package src.cn.bobo.hive.annolog.dist.hdfs;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class AppSlaveReceiveBlock extends Thread {
	Socket sc = null;
	InputStream in = null;

	public AppSlaveReceiveBlock(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try {
			InputStream in = sc.getInputStream();
			FileOutputStream fo = new FileOutputStream("d:/workdir/block2");
			BufferedOutputStream bo = new BufferedOutputStream(fo);
			
			int b;
			while ((b = in.read()) != -1) {
				bo.write(b);
			}
			bo.flush();
			bo.close();
			sc.close();

		} catch (Exception exception) {
		}

	}

}
