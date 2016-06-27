package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SonBean3  implements Serializable,Comparable<SonBean3>{

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
	 * 是否使用
	 */
	private boolean used=true;
	/**
	 * 点击状态
	 */
	private int pointLog=0;
	
	private String pointLogText=null;
	
	public String getPointLogText() {
		return pointLogText;
	}
	public void setPointLogText(String pointLogText) {
		this.pointLogText = pointLogText;
	}
//	public ArrayList<ExplainBean> getExplain() {
//		return explain;
//	}
//	public void setExplain(ArrayList<ExplainBean> explain) {
//		this.explain = explain;
//	}
//	public void addExplain(ExplainBean explain)
//	{
//		boolean flag=false;
//		for(int i=0;i<this.explain.size();i++)
//		{
//			if(this.explain.get(i).getText().equals(explain.getText()))
//			{
//				flag=true;
//			}
//		}
//		if(!flag)
//		{
//			this.explain.add(explain);
//		}
//	}
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
	@Override
	public int compareTo(SonBean3 o) {
		// TODO Auto-generated method stub
		SonBean3 in=(SonBean3)o;
		return Double.compare(in.getValue(),this.value);
	}
	
}
