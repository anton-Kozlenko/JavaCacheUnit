package com.hit.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long,DataModel<T>> {
	
	private String filePath;
    private Map <Long,DataModel<T>> myHashMap;
    private int capacity;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
	
	public DaoFileImpl(String filePath) {
		this.filePath = filePath;
	}
	
	public DaoFileImpl(String filePath, int capacity) {
		this.filePath = filePath;
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return this.capacity;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	synchronized public void delete(DataModel<T> entity) throws IllegalArgumentException {
		
		
		try {
			inStream = new ObjectInputStream(new FileInputStream(this.filePath));
            myHashMap = (HashMap<Long,DataModel<T>>) inStream.readObject();
            myHashMap.remove(entity.getdataModelId());
            inStream.close();
            System.out.println("DataModel with ID: " + entity.getdataModelId() + " deleted");
        }
        catch (FileNotFoundException e) {
            System.out.println("Dao: File not found on delete");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Dao: IOException on delete");
            e.printStackTrace();
        } 
		catch (ClassNotFoundException e) {
			System.out.println("Dao: ClassNotFoundException on delete");
			e.printStackTrace();
		}

        // Write data back to file
        try {
        	outStream = new ObjectOutputStream(new FileOutputStream(filePath));
        	outStream.writeObject(myHashMap);
        	outStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Dao: FileNotFoundException on delete, write");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Dao: IOException on delete, write");
            e.printStackTrace();
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public DataModel<T> find(Long id) throws IllegalArgumentException {
		try {
            inStream = new ObjectInputStream(new FileInputStream(filePath));
            myHashMap = (HashMap<Long, DataModel<T>>) inStream.readObject();
            inStream.close();

            if (myHashMap.containsKey(id)) {
                System.out.println("DataModel ID: " + id +  " found");
                return myHashMap.get(id);
            }
            else
                System.out.println("DataModel ID: " + id +  " not found"); {
                return null;
            }
            
        }
        catch (FileNotFoundException e) {
            System.out.println("Dao: FileNotFoundException");
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            System.out.println("Dao: IOException");
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Dao: ClassNotFoundException");
            e.printStackTrace();
            return null;
        }
	}
	
	
	@SuppressWarnings("unchecked")
	synchronized public HashMap<Long, DataModel<T>> findAll() throws IllegalArgumentException {
		try {
            inStream = new ObjectInputStream(new FileInputStream(filePath));
            myHashMap = (HashMap<Long, DataModel<T>>) inStream.readObject();
            inStream.close();

            return (HashMap<Long, DataModel<T>>) myHashMap;
        }
        catch (FileNotFoundException e) {
            System.out.println("Dao: FileNotFoundException");
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            System.out.println("Dao: IOException");
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Dao: ClassNotFoundException");
            e.printStackTrace();
            return null;
        }
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	synchronized public void save(DataModel<T> entity) {
		try {
            inStream = new ObjectInputStream(new FileInputStream(filePath));
            myHashMap = (HashMap<Long, DataModel<T>>) inStream.readObject();
            inStream.close();

            if (myHashMap.containsKey(entity.getdataModelId()) == false) {
                myHashMap.put(entity.getdataModelId(), entity);
                System.out.println("DataModel ID: " + entity.getdataModelId() +  " added");
            }
            else {
                System.out.println("DataModel ID: " + entity.getdataModelId() +  " already exists");
            }

            outStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outStream.writeObject(myHashMap);
            outStream.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("Dao: FileNotFoundException");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Dao: IOException");
            // Write data to file
            try {
            	if(myHashMap == null) {
            		myHashMap = new HashMap<Long, DataModel<T>>();
            	}
                myHashMap.put(entity.getdataModelId(), entity);
                outStream = new ObjectOutputStream(new FileOutputStream(this.filePath));
                outStream.writeObject(myHashMap);
                outStream.close();

                System.out.println("DataModel ID: " + entity.getdataModelId() +  " added");
            }
            catch (FileNotFoundException exc) {
                System.out.println("Dao: FileNotFoundException");
                exc.printStackTrace();
            }
            catch (IOException exc) {
                System.out.println("Dao: IOException");
                exc.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Dao: ClassNotFoundException");
            e.printStackTrace();
        }
	}

}
