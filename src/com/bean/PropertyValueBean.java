package com.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class PropertyValueBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<KeyValueBean> properties=null;

	public ArrayList<KeyValueBean> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<KeyValueBean> properties) {
		this.properties = properties;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
