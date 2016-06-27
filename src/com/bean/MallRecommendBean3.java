package com.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import org.bson.types.ObjectId;

/**
 * 给 mall 推荐 brand
 * @author Administrator
 *
 */
public class MallRecommendBean3 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * mallName
	 */
	private String mallName="";
	/**
	 * mall 对应的id
	 */
	private int mallId=0;
	
	private int pointLog=0;
	
	private int cityId=0;
	
	private String cityName=null;
	/**
	 * 推荐的业态对应的物品
	 */
	private LinkedList<CategoryItemBean3> categoryItem=new LinkedList<CategoryItemBean3>();
	
	/**
	 * 品牌相似度信息
	 */
	private LinkedList<SonSimBean3> similaryMall=new LinkedList<SonSimBean3>();

	/**
	 * 是否使用
	 */
	private boolean used=true;

	
	public int getMallId() {
		return mallId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setMallId(int mallId) {
		this.mallId = mallId;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	
	public int getPointLog() {
		return pointLog;
	}

	public void setPointLog(int pointLog) {
		this.pointLog = pointLog;
	}

	public LinkedList<CategoryItemBean3> getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(LinkedList<CategoryItemBean3> categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	public void setCategoryItem(int index,CategoryItemBean3 categoryItem)
	{
		this.categoryItem.set(index, categoryItem);
	}
	
	public void addCategoryItem(CategoryItemBean3 categoryItem) {
		this.categoryItem.add(categoryItem);
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public LinkedList<SonSimBean3> getSimilaryMall() {
		return similaryMall;
	}

	public void setSimilaryMall(LinkedList<SonSimBean3> similaryMall) {
		this.similaryMall = similaryMall;
	}

	public void addSimilaryMall(SonSimBean3 similary) {
		boolean flag=false;
		for(SonSimBean3 son:this.similaryMall)
		{
			if(son.getName().equals(similary.getName()))
			{
				flag=true;
				son.setValue((son.getValue()+similary.getValue())/2);
			}
		}
		if(!flag)
		{
			this.similaryMall.add(similary);
		}
	}
	/**
	 * 重新排序
	 * 相似信息
	 */
	public void sortSim()
	{
		//重新排序
		Collections.sort(similaryMall);
		//组合
	}
	/**
	 * 排序item数据
	 */
	public void sortItem()
	{
		Collections.sort(this.categoryItem);
	}
}
