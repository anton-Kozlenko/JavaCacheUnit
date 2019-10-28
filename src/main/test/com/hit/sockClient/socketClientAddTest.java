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

public class socketClientAddTest {

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
		myJSON.putOnce("service", "create");
		  
		  
		JSONObject book1 = new JSONObject();
		book1.putOnce("author", "Elina Kolesnik");
		book1.putOnce("publisher", "fatty patty");
		book1.putOnce("title","The mighty hamburger");
		book1.putOnce("date", "10/10/1992");
		book1.putOnce("modelID", "5");
		
		/*
		 * JSONObject book2 = new JSONObject(); book2.putOnce("author", "Dor Swartz");
		 * book2.putOnce("publisher", "fatty patty");
		 * book2.putOnce("title","The sweet sweet glida"); book2.putOnce("date",
		 * "2/2/2011"); book2.putOnce("modelID", "936");
		 * 
		 * JSONObject book3 = new JSONObject(); book3.putOnce("author",
		 * "Yehonatan Dahan"); book3.putOnce("publisher", "fatty patty");
		 * book3.putOnce("title","Mr Puding"); book3.putOnce("date", "10/10/2008");
		 * book3.putOnce("modelID", "937");
		 * 
		 * JSONObject book4 = new JSONObject(); book4.putOnce("author", "Dolev Lasman");
		 * book4.putOnce("publisher", "fatty patty");
		 * book4.putOnce("title","The lone fat guy"); book4.putOnce("date", "1/1/2012");
		 * book4.putOnce("modelID", "938");
		 * 
		 * JSONObject book5 = new JSONObject(); book5.putOnce("author", "Shaul Cohen");
		 * book5.putOnce("publisher", "fatty patty");
		 * book5.putOnce("title","Stupid man"); book5.putOnce("date", "1/9/2012");
		 * book5.putOnce("modelID", "944");
		 * 
		 * JSONObject book6 = new JSONObject(); book6.putOnce("author",
		 * "Lidor Shoshani"); book6.putOnce("publisher", "fatty patty");
		 * book6.putOnce("title","ELK master"); book6.putOnce("date", "1/1/2012");
		 * book6.putOnce("modelID", "946");
		 * 
		 * JSONObject book7 = new JSONObject(); book7.putOnce("author",
		 * "Elisha Davidi"); book7.putOnce("publisher", "fatty patty");
		 * book7.putOnce("title","Elk new guy"); book7.putOnce("date", "1/1/2012");
		 * book7.putOnce("modelID", "998");
		 * 
		 * JSONObject book8 = new JSONObject(); book8.putOnce("author", "Emanuel Roro");
		 * book8.putOnce("publisher", "fatty patty");
		 * book8.putOnce("title","Study Study"); book8.putOnce("date", "3/3/2017");
		 * book8.putOnce("modelID", "966");
		 */
		  
		JSONObject[] booksArray = { book1/* , book2, book3, book4, book5, book6, book7, book8 */};  
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

		assertEquals(sockRes, "create");
	}

}
