package com.bean;

public class KeyValueBean {

	
	public String key=null;
	public String value=null;
	public String getKey() {
		return key;
	}
	
	public KeyValueBean()
	{
		
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString()
	{
		return "["+key+":"+value+"]";
	}
	
	
}
