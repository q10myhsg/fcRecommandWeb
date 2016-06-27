package com.bean;

import java.io.Serializable;

public class MongoIdBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1465544129971705588L;
	public String $oid="";

	public String get$oid() {
		return $oid;
	}

	public void set$oid(String $oid) {
		this.$oid = $oid;
	}
	
}
