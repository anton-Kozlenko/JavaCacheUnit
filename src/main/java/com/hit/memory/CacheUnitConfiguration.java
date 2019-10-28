package com.hit.memory;

import java.io.Serializable;

public class CacheUnitConfiguration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5804526762355721358L;
	private int capacity;
	private String algoName;
	
	public CacheUnitConfiguration(int capacity, String algoName) {
		this.capacity = capacity;
		this.algoName = algoName;
	}
	
	public void setCapacity(int newCapacity) {
		this.capacity = newCapacity;
	}
	
	public void setAlgorithm(String newAlgoName) {
		this.algoName = newAlgoName;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public String getAlgo() {
		return this.algoName;
	}
	
}
