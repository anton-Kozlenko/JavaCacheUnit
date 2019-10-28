package com.hit.cacheServer;

import java.io.BufferedReader;
//import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hit.dm.DataModel;

public class Server implements Runnable{

	private Socket socket;
	//private static ServerApi myServer = ServerApi.getServerApiInstance();
	
	public Server(Socket socket) {
		this.socket = socket;
	}
	
	private static JSONArray incomeSocketHandler(String service, JSONObject inputData) {
		
		/*String service = incomeJson.getString("service");
		JSONObject inputData = incomeJson.getJSONObject("data");*/
		
		System.out.println("Started socket handling.");
		
		if(service.equalsIgnoreCase("create")) {
			System.out.println("service is 'create'!.");
			processCreate(inputData);
			return null;
		}
		else if(service.equalsIgnoreCase("read")) {
			System.out.println("service is 'read'!.");
			return processRead(inputData);
		}
		else if(service.equalsIgnoreCase("all")) {
			System.out.println("service is 'read'!.");
			return processReadAll();
		}
		else if(service.equalsIgnoreCase("update")) {
			processUpdate(inputData);
			return null;
		}
		else if(service.equalsIgnoreCase("delete")) {
			System.out.println("service is 'delete'!.");
			processDelete(inputData);
			return null;
		}
		else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void processCreate(JSONObject inputData) {
		
		System.out.println("starting to create");
		
		JSONArray newBooksInJson = inputData.getJSONArray("data");
		DataModel<BookPojo>[] newModel = new DataModel[newBooksInJson.length()];
		
		for(int i=0; i<newBooksInJson.length(); i++) {
			System.out.println("found new Book!");
			newModel[i] = new DataModel<BookPojo>(newBooksInJson.getJSONObject(i).getLong("modelID"), new BookPojo(newBooksInJson.getJSONObject(i).getString("author"),
					newBooksInJson.getJSONObject(i).getString("publisher"),
					newBooksInJson.getJSONObject(i).getString("title"),
					newBooksInJson.getJSONObject(i).getString("date")));
			
			System.out.println("Book num: " + i + " data: " + newModel[i].toString());
		}
		System.out.println("before actual create.");
		ServerApi.serverApiCreate(newModel);
		System.out.println("after actual create.");
	}
	
	private static JSONArray processRead(JSONObject inputData) {
		
		System.out.println("starting to read");
		JSONArray booksToRead = inputData.getJSONArray("data");
		Long idsToRead[] = new Long[booksToRead.length()];
		
		for(int i=0; i<booksToRead.length(); i++) {
			System.out.println("found new Book to read");
			idsToRead[i] = booksToRead.getLong(i);
			System.out.println("Book num: " + i + " id: " + idsToRead[i]);
		}
		System.out.println("before actual read.");
		JSONArray outputModels = ServerApi.serverApiRead(idsToRead);
		System.out.println("after actual read.");
		
		return outputModels;
	}
	
	private static JSONArray processReadAll() {
		
		System.out.println("starting to read");
		
		System.out.println("before actual read.");
		JSONArray outputModels = ServerApi.serverApiReadAll();
		System.out.println("after actual read.");
		
		return outputModels;
	}

	@SuppressWarnings("unchecked")
	private static void processUpdate(JSONObject inputData) {
		System.out.println("starting to update");
		
		JSONArray newBooksInJson = inputData.getJSONArray("data");
		DataModel<BookPojo>[] newModel = new DataModel[newBooksInJson.length()];
		
		for(int i=0; i<newBooksInJson.length(); i++) {
			System.out.println("found new Book!");
			newModel[i] = new DataModel<BookPojo>(newBooksInJson.getJSONObject(i).getLong("modelID"), new BookPojo(newBooksInJson.getJSONObject(i).getString("author"),
					newBooksInJson.getJSONObject(i).getString("publisher"),
					newBooksInJson.getJSONObject(i).getString("title"),
					newBooksInJson.getJSONObject(i).getString("date")));
			
			System.out.println("Book num: " + i + " data: " + newModel[i].toString());
		}
		System.out.println("before actual update.");
		ServerApi.serverApiUpdate(newModel);
		System.out.println("after actual update.");
	}

	@SuppressWarnings("unchecked")
	private static void processDelete(JSONObject inputData) {
		
		System.out.println("starting to delete");
		JSONArray booksToDelete = inputData.getJSONArray("data");
		DataModel<BookPojo>[] remModel = new DataModel[booksToDelete.length()];
		
		for(int i=0; i<booksToDelete.length(); i++) {
			System.out.println("found new Book to remove!");
			remModel[i] = new DataModel<BookPojo>(booksToDelete.getJSONObject(i).getLong("modelID"), new BookPojo(booksToDelete.getJSONObject(i).getString("author"),
					booksToDelete.getJSONObject(i).getString("publisher"),
					booksToDelete.getJSONObject(i).getString("title"),
					booksToDelete.getJSONObject(i).getString("date")));
			
			System.out.println("Book num: " + i + " data: " + remModel[i].toString());
		}
		
		System.out.println("before actual delete.");
		ServerApi.serverApiDelete(remModel);
		System.out.println("after actual delete.");
	}
	
	@Override
	public void run() {
		
		BufferedReader socketReader;
		Random rand = new Random();
		int identifier = rand.nextInt(1000);
		JSONObject incomeJson;
		String desiredService = "";
		JSONArray output = null;
		
		System.out.println("Started new Thread: " + identifier);
		try {
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			try {
				String content = socketReader.readLine();
				System.out.println(content);
				incomeJson = new JSONObject(content);
				desiredService = incomeJson.getString("service");
				output = incomeSocketHandler(desiredService, incomeJson);
			}
			catch(SocketTimeoutException e) {
				System.out.println("timeout on server");
				e.printStackTrace();
			}
			
			System.out.println("Sending server response...");
			PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
			outStream.println(output == null ? desiredService : output);
			
			System.out.println("Done Thread: " + identifier);
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
