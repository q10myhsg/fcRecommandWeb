package com.bean;

import java.io.Serializable;
import java.util.LinkedList;

public class CategoryItemBean2  implements Serializable,Comparable<CategoryItemBean2>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业态
	 */
	private String  categoryName="";
	/**
	 * 业态id
	 */
	private int category=0;
	/**
	 * 使用状态
	 */
	private boolean used=true;
	
	/**
	 * 子类
	 */
	private LinkedList<SonBean2> recommandShop=new LinkedList<SonBean2>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public LinkedList<SonBean2> getRecommandShop() {
		return recommandShop;
	}

	public void setRecommandShop(LinkedList<SonBean2> recommandShop) {
		this.recommandShop = recommandShop;
	}

	public void addRecommandShop(SonBean2 recommandShop) {
		this.recommandShop.add(recommandShop);
	}
	
	public void addRecommandShopLimit(SonBean2 recommandShop,int count) {
		if(this.recommandShop.size()<=count)
		this.recommandShop.add(recommandShop);
	}	

	@Override
	public int compareTo(CategoryItemBean2 o) {
		// TODO Auto-generated method stub
		return Long.compare(category,o.category);
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
