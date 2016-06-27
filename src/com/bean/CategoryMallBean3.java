package com.bean;

import java.io.Serializable;
import java.util.LinkedList;

public class CategoryMallBean3 implements Serializable,Comparable<CategoryMallBean3>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业态id
	 */
	private int  category=0;
	/**
	 * 分类名
	 */
	private String categoryName="";
	
	/**
	 * 子类
	 */
	private LinkedList<SonBean3> recommandMall=new LinkedList<SonBean3>();
	
	private boolean used=true;
	
	public LinkedList<SonBean3> getRecommandMall() {
		return recommandMall;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setRecommandMall(LinkedList<SonBean3> recommandMall) {
		this.recommandMall = recommandMall;
	}
	
	public void addRecommandMall(SonBean3 recommandMall) {
		this.recommandMall.add(recommandMall);
	}
	
	public void addRecommandMallDistinct(SonBean3 recommandMall) {
		
		boolean flag=false;
		for(SonBean3 son:this.recommandMall)
		{
			if(son.getName().equals(recommandMall.getName()))
			{
				flag=true;
			}
		}
		if(!flag)
		{
			this.recommandMall.add(recommandMall);
		}
	}

	@Override
	public int compareTo(CategoryMallBean3 o) {
		return Long.compare(category,o.category);
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
	
}
