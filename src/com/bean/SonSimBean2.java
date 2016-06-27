package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SonSimBean2  implements Serializable,Comparable<SonSimBean2>{

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
	 * 状态
	 */
	private int pointLog;
	
	private String pointLogText=null;
	
	/**
	 * 属性值
	 */
	private PropertyValueBean propertyValue=null;
	
	private boolean used=true;
	/**
	 * 描述信息
	 */
	//private ArrayList<ExplainBean> explain=new ArrayList<ExplainBean>();
	

	public PropertyValueBean getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(PropertyValueBean propertyValue) {
		this.propertyValue = propertyValue;
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
	public int compareTo(SonSimBean2 other)
	{
		return -Float.compare(value,other.getValue());
	}
	
//	public void sortExplain()
//	{
//		Collections.sort(explain);
//	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public int getPointLog() {
		return pointLog;
	}
	public void setPointLog(int pointLog) {
		this.pointLog = pointLog;
	}
	public String getPointLogText() {
		return pointLogText;
	}
	public void setPointLogText(String pointLogText) {
		this.pointLogText = pointLogText;
	}
	
	
}
