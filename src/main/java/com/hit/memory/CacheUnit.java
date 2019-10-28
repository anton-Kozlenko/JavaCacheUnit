package com.hit.memory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.hit.dm.DataModel;
import com.hit.project.IAlgoCache;
import com.hit.project.LRUAlgoCacheImpl;
import com.hit.project.RandomAlgoCacheImpl;
import com.hit.project.SecondChance;

public class CacheUnit<T> {
	
	private IAlgoCache<Long, DataModel<T>> algo;
    private ArrayList<Long> idsArray= new ArrayList<>();
    private HashMap<String, IAlgoCache<Long, DataModel<T>>> algorithmsHash = new HashMap<>(); // added in exam

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
		this.algo = algo;
        //this.idsArray = new ArrayList<>();
	}
	
	public CacheUnit() {
		ObjectInputStream outStream;
		try {
			outStream = new ObjectInputStream(new FileInputStream(
					"C:\\Users\\Anton\\eclipse-workspace\\CacheUnitProject\\src\\main\\resources\\cacheunitconfig.txt"));
			CacheUnitConfiguration cacheConfig = (CacheUnitConfiguration)outStream.readObject();
			createAlgo(cacheConfig.getCapacity());
			this.algo = algorithmsHash.get(cacheConfig.getAlgo());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createAlgo(int capacity) {
		//HashMap<String, IAlgoCache<Long, String>> algorithmsHash = new HashMap<>();
		
		this.algorithmsHash.put("lru", new LRUAlgoCacheImpl<Long, DataModel<T>>(capacity));
		this.algorithmsHash.put("random", new RandomAlgoCacheImpl<Long, DataModel<T>>(capacity));
		this.algorithmsHash.put("secondChance", new SecondChance<Long, DataModel<T>>(capacity));
	}
	
	public void switchAlgo(String algoName) {
		this.algo = algorithmsHash.get(algoName);
	}
	
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModelsAll() {
		ArrayList<DataModel<T>> listOfDataModels = new ArrayList<DataModel<T>>();
		for (Long id = (long) 0; id < 92233; id++) {
            if (algo.getElement(id) == null) {
            	// System.out.println("DataModel ID: " + id + " was not found in Cache");
            }   
            else {
            	listOfDataModels.add(algo.getElement(id));
                System.out.println("DataModel ID: " + id + " found in Cache");
            }
        }
        if(listOfDataModels.size() > 0) {
        	DataModel<T>[] arrayOfDataModels = (DataModel<T>[]) new DataModel<?>[listOfDataModels.size()];
        	for(int i=0; i<listOfDataModels.size(); i++ ) {
        		arrayOfDataModels[i] = listOfDataModels.get(i);
        	}
        	return arrayOfDataModels;
        }
        return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(Long[] ids) {
	
		ArrayList<DataModel<T>> listOfDataModels = new ArrayList<DataModel<T>>();
        
        for (Long id : ids) {
            if (algo.getElement(id) == null)
                System.out.println("DataModel ID: " + id + " was not found in Cache");
            else {
            	listOfDataModels.add(algo.getElement(id));
                System.out.println("DataModel ID: " + id + " found in Cache");
            }
        }
        if(listOfDataModels.size() > 0) {
        	DataModel<T>[] arrayOfDataModels = (DataModel<T>[]) new DataModel<?>[listOfDataModels.size()];
        	for(int i=0; i<listOfDataModels.size(); i++ ) {
        		arrayOfDataModels[i] = listOfDataModels.get(i);
        	}
        	return arrayOfDataModels;
        }
        return null;
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
		
		ArrayList<DataModel<T>> listOfDataModels = new ArrayList<DataModel<T>>();
        DataModel<T> dataModelReturned;
        
        for (DataModel<T> dataModel : dataModels) {
            dataModelReturned = algo.putElement(dataModel.getdataModelId(), dataModel);
            System.out.println("DataModel ID: " + dataModel.getdataModelId() + " Added to Cache");
            
            if (!idsArray.contains(dataModel.getdataModelId())) {
				 idsArray.add(dataModel.getdataModelId()); 
			}
            
            if (dataModelReturned != null) {
            	listOfDataModels.add(dataModelReturned);
                idsArray.remove(dataModelReturned.getdataModelId());
            }
			 
        }
        DataModel<T>[] returnArray = (DataModel<T>[]) new DataModel<?>[listOfDataModels.size()];
        for(int i=0 ; i<listOfDataModels.size(); i++) {
        	returnArray[i] = listOfDataModels.get(i);
        }
        return listOfDataModels.isEmpty() ? null : returnArray;
	}
	
	
	public void removeDataModels(Long[] ids) {
		for (Long id : ids) {
            try {
                algo.removeElement(id);
                if (idsArray.remove(id)) {
                    System.out.println("DataModel ID: " + id + " removed from Cache");
                }
                else {
                    System.out.println("DataModel ID: " + id + " was not found in Cache");
                }
            }
            catch (Exception e) {
                System.out.println("DataModel ID: " + id + " was not found in Cache");
                continue;
            }
        }
	}
}
