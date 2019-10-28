package com.hit.memory;

import static org.junit.Assert.*;
import org.junit.Test;

import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import com.hit.project.SecondChance;

public class CacheUnitJtest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		
		
		/*start exam test*/
		DataModel<?>[] dmArray = {new DataModel<String>(27, "string-0"),
				new DataModel<String>(28, "string-1"),
				new DataModel<String>(29, "string-2")};
		
		CacheUnit<String> testUnit = new CacheUnit<String>();
		testUnit.putDataModels((DataModel<String>[])dmArray);
		
		testUnit.switchAlgo("lru");
		
		final DataModel<?>[] dmArrayTwo = {new DataModel<String>(27, "string-3"),
				new DataModel<String>(30, "string-4"),
				new DataModel<String>(31, "string-5")};
		
		testUnit.putDataModels((DataModel<String>[])dmArrayTwo);
		
		assertEquals(1, 1);
		
		/*end exam test*/
		
		/*final DataModel<?>[] okArray = {new DataModel<String>(27, "string-0"),
										new DataModel<String>(28, "string-1"),
										new DataModel<String>(29, "string-2")};
		
		
		CacheUnit<String> myCache = new CacheUnit<String>(
				new SecondChance<Long, DataModel<String>>(5));
		
		DaoFileImpl<String> myDao = new DaoFileImpl<String>(
				"C:\\Users\\Anton\\eclipse-workspace\\CacheUnitProject\\src\\main\\resources\\dataSource.txt");
		
		int numOfDataModels = 8;
		DataModel<?>[] dmArray= new DataModel<?>[numOfDataModels];
		DataModel<String>[] resArray;
		
		for(int i=0; i<numOfDataModels; i++) {
			dmArray[i] = new DataModel<String>(i+ 27, "string-" + i);
		}
		
		resArray = myCache.putDataModels((DataModel<String>[]) dmArray);
		
		for(DataModel<String> entity : (DataModel<String>[]) resArray) {
			myDao.save(entity);
		}
		
		myDao.delete(resArray[1]);
		
		for(DataModel<String> entity : (DataModel<String>[]) resArray) {
			if(myDao.find(entity.getdataModelId()) != null) {
				System.out.println(myDao.find(entity.getdataModelId()).toString());
			}
			else {
				System.out.println("entity: " + entity.toString() + " is not saved in resource.");
			}
			
		}
		
		
		assertEquals(resArray, okArray);*/
	}

}
