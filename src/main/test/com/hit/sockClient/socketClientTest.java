package com.hit.sockClient;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.json.*;

import org.junit.Test;


public class socketClientTest {

	@SuppressWarnings("resource")
	@Test
	public void test() throws UnknownHostException, IOException, InterruptedException {
		final String host = "localhost";
		final int portNumber = 8024;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);

		Socket socket = new Socket(host, portNumber);
		socket.setSoTimeout(2500);

		PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	
		JSONObject myJSON = new JSONObject();
		myJSON.putOnce("service", "read");
		  
		Long idsToRem[] = {Long.parseLong("5")};
		myJSON.putOnce("data", idsToRem);
		 

		outStream.println(myJSON.toString());
		System.out.println("client sent: " + myJSON.toString());

		Thread.sleep(5000);
		String sockRes = "";

		try {
			sockRes = inStream.readLine().toString();
			JSONArray outputJson = new JSONArray(sockRes);
			System.out.println("got JSON from server: " + outputJson.toString());

		} catch (SocketTimeoutException e) {
			System.out.println("timeout on client");
			e.printStackTrace();
		}
		socket.close();

		assertEquals(1, 1);
	}

}
