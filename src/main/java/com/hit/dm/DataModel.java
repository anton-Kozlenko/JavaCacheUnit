package com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable{
	
	
	private static final long serialVersionUID = -2410178036067428098L;
	private T content;
	private long id;
	
	public DataModel(long id, T content) {
		this.setContent(content);
		this.setDataModelId(id);
	}
	
	public void setContent(T content) {
		this.content = content;
	}
	
	public void setDataModelId(long id) {
		this.id = id;
	}
	
	public T getContent() {
		return this.content;
	}
	
	public long getdataModelId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "DataModel ID: " + this.id + ", Content: " + this.content.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModel<?> other = (DataModel<?>) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		return true;
	} 

}
