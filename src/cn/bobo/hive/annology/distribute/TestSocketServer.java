package cn.bobo.hive.annology.distribute;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer {
	ServerSocket serverSocket = null;

	public TestSocketServer() throws IOException {

		serverSocket = new ServerSocket(988);
	}

	public void conn() throws IOException {

		while (true) {
			Socket sc = serverSocket.accept();
			new TestServerThread(sc).start();
		}

	}

	public static void main(String[] args) throws IOException {
		TestSocketServer testSocket = new TestSocketServer();
		testSocket.conn();
	}

}
