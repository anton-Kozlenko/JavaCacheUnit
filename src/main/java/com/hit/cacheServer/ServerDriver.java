package com.hit.cacheServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDriver {
	private final static int serverPort = 8024;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		System.out.println("Java server is listenning on port: " + serverPort);
		try {
			ServerSocket server = new ServerSocket(serverPort);
			while(true) {
				Socket mySock = server.accept();
				mySock.setSoTimeout(2500);
				new Thread(new Server(mySock)).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
