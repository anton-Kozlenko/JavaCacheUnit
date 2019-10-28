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

public class socketClientUpdateTest {

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
		myJSON.putOnce("service", "update");
		  
		JSONObject book = new JSONObject(); book.putOnce("author", "Naor Ahdut");
		book.putOnce("publisher", "fatty patty");
		book.putOnce("title","The mighty Double Mega Super Hamburger");
		book.putOnce("date", "1/1/2012");
		book.putOnce("modelID", "935");
		  
		JSONObject[] booksArray = {book};  
		myJSON.putOnce("data", booksArray);
		 

		outStream.println(myJSON.toString());
		System.out.println("client sent: " + myJSON.toString());

		Thread.sleep(5000);
		String sockRes = "";

		try {
			sockRes = inStream.readLine().toString();
			System.out.println("got message from server: " + sockRes);

		} catch (SocketTimeoutException e) {
			System.out.println("timeout on client");
			e.printStackTrace();
		}
		socket.close();

		assertEquals(sockRes, "update");
	}

}
