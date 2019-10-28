package com.hit.cacheServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import com.hit.project.SecondChance;

public class ServerApi {
	
	private static CacheUnit<BookPojo> myCache = new CacheUnit<BookPojo>(
			new SecondChance<Long, DataModel<BookPojo>>(5));
	
	private static DaoFileImpl<BookPojo> myDao = new DaoFileImpl<BookPojo>(
			"C:\\Users\\Anton\\eclipse-workspace\\CacheUnitProject\\src\\main\\resources\\dataSource.txt");
	
	private static DataModel<BookPojo>[] cachUnitResultArray;
	
	private static ServerApi serverApi = new ServerApi();
	
	private ServerApi() {
	}
	
	public static ServerApi getServerApiInstance() {
		return serverApi;
	}
	
	public static boolean serverApiCreate (DataModel<BookPojo>[] input) {
		cachUnitResultArray = myCache.putDataModels(input);
		
		if(cachUnitResultArray != null) {
			for(int i=0; i< cachUnitResultArray.length; i++) {
				System.out.println("Returned from CacheUnit create: " + cachUnitResultArray[i].toString());
			}
			
			for(DataModel<BookPojo> entity : (DataModel<BookPojo>[]) cachUnitResultArray) {
				if(myDao.find(entity.getdataModelId()) != null) {
					System.out.println(myDao.find(entity.getdataModelId()).toString());
				}
				else {
					System.out.println("entity: " + entity.toString() + " is not saved in resource.");
					myDao.save(entity);
				}
			}
		}
		return true;
	}
	
	
	public static JSONArray serverApiReadAll () {
		DataModel<BookPojo>[] output;
		ArrayList<DataModel<BookPojo>> helperList = new ArrayList<DataModel<BookPojo>>();
		HashMap<Long, DataModel<BookPojo>> helperBooksHash;
		
		List<Long> idsToFindInResource = new ArrayList<Long>();
		
		output = myCache.getDataModelsAll();
		
		if(output != null) {
			System.out.println("output is not null: " + output.toString());
			for(DataModel<BookPojo> dm : output) {
				System.out.println("removing: " + dm.getdataModelId() + " from: " + idsToFindInResource.toArray().toString());
				idsToFindInResource.remove(Long.valueOf(dm.getdataModelId()));
				helperList.add(dm);
			}
		}
		
		helperBooksHash = myDao.findAll();
		for(Long key : helperBooksHash.keySet()) {
			helperList.add(helperBooksHash.get(key));
		}
		
		if(helperList.size() > 0) {
			JSONArray booksJson= new JSONArray();
			
			helperList.forEach(book -> {
				JSONObject jsonBook = new JSONObject();
				BookPojo tmpBook = book.getContent();
				
				jsonBook.put("author", tmpBook.getAuthor());
				jsonBook.put("publisher", tmpBook.getPublisher());
				jsonBook.put("title", tmpBook.getTitle());
				jsonBook.put("date", tmpBook.getDate());
				jsonBook.put("modelID", book.getdataModelId());
				
				booksJson.put(jsonBook);
			});
			return booksJson;
        }
        return new JSONArray();
	}
	
	
	public static JSONArray serverApiRead (Long[] ids) {
		
		DataModel<BookPojo>[] output;
		ArrayList<DataModel<BookPojo>> helperList = new ArrayList<DataModel<BookPojo>>();
		DataModel<BookPojo> helperBook;
		
		List<Long> idsToFindInResource = new ArrayList<Long>();
		for(int i=0; i<ids.length; i++) {
			idsToFindInResource.add(ids[i]);
		}
		System.out.println("ids to find: " + idsToFindInResource.toString());
		
		output = myCache.getDataModels(ids);
		if(output != null) {
			System.out.println("output is not null: " + output.toString());
			for(DataModel<BookPojo> dm : output) {
				System.out.println("removing: " + dm.getdataModelId() + " from: " + idsToFindInResource.toArray().toString());
				idsToFindInResource.remove(Long.valueOf(dm.getdataModelId()));
				helperList.add(dm);
			}
		}
		System.out.println("ids to find: " + idsToFindInResource.toString());
		for(Long id : idsToFindInResource) {
			helperBook = myDao.find(id);
			if(helperBook != null) {
				helperList.add(helperBook);
			}
		}
		
		if(helperList.size() > 0) {
			JSONArray booksJson= new JSONArray();
			
			helperList.forEach(book -> {
				JSONObject jsonBook = new JSONObject();
				BookPojo tmpBook = book.getContent();
				
				jsonBook.put("author", tmpBook.getAuthor());
				jsonBook.put("publisher", tmpBook.getPublisher());
				jsonBook.put("title", tmpBook.getTitle());
				jsonBook.put("date", tmpBook.getDate());
				jsonBook.put("modelID", book.getdataModelId());
				
				booksJson.put(jsonBook);
			});
			return booksJson;
        }
        return new JSONArray();
	}
	
	
	public static boolean serverApiUpdate (DataModel<BookPojo>[] books) {
		
		serverApiDelete(books);
		serverApiCreate(books);
		return true; 
	}
	 
	
	public static boolean serverApiDelete (DataModel<BookPojo>[] books) {
		try {
			ArrayList<Long> idsList = (ArrayList<Long>) Arrays.asList(books).stream().
					map(n -> n.getdataModelId()).collect(Collectors.toList());
			
			myCache.removeDataModels(idsList.toArray(new Long[idsList.size()]));
			
			for(DataModel<BookPojo> book : books) {
				myDao.delete(book);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
