package com.bean;

public class KeyValueIntBean {

	
	public String key=null;
	public int value=0;
	public String getKey() {
		return key;
	}
	
	public KeyValueIntBean()
	{
		
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString()
	{
		return "["+key+":"+value+"]";
	}
	
	
}
