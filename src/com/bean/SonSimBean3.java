package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SonSimBean3  implements Serializable,Comparable<SonSimBean3>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int simId=0;
	/**
	 * 名
	 */
	private String name="";
	/**
	 * 值
	 */
	private float value=0F;
	/**
	 * 使用状态
	 */
	private boolean used=true;
	
	/**
	 * 使用状态
	 */
	private int pointLog;
	
	private String pointLogText=null;

	public String getPointLogText() {
		return pointLogText;
	}
	public void setPointLogText(String pointLogText) {
		this.pointLogText = pointLogText;
	}
	public int getSimId() {
		return simId;
	}
	public void setSimId(int simId) {
		this.simId = simId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int compareTo(SonSimBean3 other)
	{
		return -Float.compare(value,other.value);
	}
	
	public int getPointLog() {
		return pointLog;
	}
	public void setPointLog(int pointLog) {
		this.pointLog = pointLog;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	
}
