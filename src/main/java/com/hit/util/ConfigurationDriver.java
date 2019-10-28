package com.hit.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hit.memory.CacheUnitConfiguration;

public class ConfigurationDriver {

	public static void main(String[] args) {
		
		CacheUnitConfiguration newConfig = new CacheUnitConfiguration(5, "random");
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(
					"C:\\Users\\Anton\\eclipse-workspace\\CacheUnitProject\\src\\main\\resources\\cacheunitconfig.txt"));
            outStream.writeObject(newConfig);
            outStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
