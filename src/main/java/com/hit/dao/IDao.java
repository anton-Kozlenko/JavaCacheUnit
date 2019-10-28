package com.hit.dao;

import java.io.Serializable;

public interface IDao <ID extends Serializable, T>{
	
	public void delete(T entity) throws IllegalArgumentException;
	public T find(ID id) throws IllegalArgumentException;
	public void save(T entity);

}
