package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SonBean2  implements Serializable,Comparable<SonBean2>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id=0;
	/**
	 * 名
	 */
	private String name="";
	/**
	 * 值
	 */
	private float value=0f;
	/**
	 * 数量
	 */
	private int  count=0;
	/**
	 * 使用状态
	 */
	private boolean used=true;
	/**
	 * 点击日志
	 */
	private int pointLog=0;
	
	private String pointLogText=null;
	
	
	
	/**
	 * 属性值
	 */
	private PropertyValueBean propertyValue=new PropertyValueBean();
//	/**
//	 * 描述信息
//	 */
//	private ArrayList<ExplainBean> explain=new ArrayList<ExplainBean>();
	
	public String getPointLogText() {
		return pointLogText;
	}
	public void setPointLogText(String pointLogText) {
		this.pointLogText = pointLogText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
//	public void sortExplain()
//	{
//		Collections.sort(explain);
//	}
//	
//	
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
	
	
	
	public PropertyValueBean getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(PropertyValueBean propertyValue) {
		this.propertyValue = propertyValue;
	}
	@Override
	public int compareTo(SonBean2 o) {
		// TODO Auto-generated method stub
		SonBean2 in=(SonBean2)o;
		return Double.compare(in.getValue(),this.value);
	}
	
}
