package com.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import org.bson.types.ObjectId;

/**
 * 给 brand 推荐 mall
 * @author Administrator
 *
 */
public class ItemRecommendBean3 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * mallName
	 */
	private String brandName="";
	
	private int brandId=0;
	/**
	 * 是否使用
	 */
	private boolean used=true;
	
	private int cityid=0;
	private String cityName=null;
	private int pointLog=0;

	/**
	 * 推荐的业态对应的物品
	 */
	private LinkedList<CategoryMallBean3> categoryMall=new LinkedList<CategoryMallBean3>();
	
	/**
	 * 品牌相似度信息
	 */
	private LinkedList<SonSimBean3> similaryBrand=new LinkedList<SonSimBean3>();

	public String getBrandName() {
		return brandName;
	}
	
	public int getPointLog() {
		return pointLog;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setPointLog(int pointLog) {
		this.pointLog = pointLog;
	}



	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public LinkedList<SonSimBean3> getSimilaryBrand() {
		return similaryBrand;
	}

	public void setSimilaryBrand(LinkedList<SonSimBean3> similaryBrand) {
		this.similaryBrand = similaryBrand;
	}

	public LinkedList<CategoryMallBean3> getCategoryMall() {
		return categoryMall;
	}

	public void setCategoryMall(LinkedList<CategoryMallBean3> categoryMall) {
		this.categoryMall = categoryMall;
	}
	public void addCategoryMall(CategoryMallBean3 categoryMall) {
		this.categoryMall.add(categoryMall);
	}
	/**
	 * 添加并替换重复的
	 * @param categoryMall
	 */
	public void addCategoryMallDistinct(CategoryMallBean3 categoryMall)
	{
		boolean flag=false;
		for(int i=0;i<this.categoryMall.size();i++)
		{
			if(categoryMall.getCategory()==this.categoryMall.get(i).getCategory())
			{
				flag=true;
				this.categoryMall.set(i,categoryMall);
			}
		}
		if(!flag)
		{
			addCategoryMall(categoryMall);
		}
	}

	public void addSimilaryBrand(SonSimBean3 similary) {
		boolean flag=false;
		for(SonSimBean3 son:this.similaryBrand)
		{
			if(son.getName().equals(similary.getName()))
			{
				flag=true;
				son.setValue(son.getValue()+similary.getValue());
			}
		}
		if(!flag)
		{
			this.similaryBrand.add(similary);
		}
	}
	
	public void addSimilaryBrandDistinct(SonSimBean3 similary)
	{
		boolean flag=false;
		for(SonSimBean3 son:this.similaryBrand)
		{
			if(son.getName().equals(similary.getName()))
			{
				flag=true;
			}
		}
		if(!flag)
		{
			this.similaryBrand.add(similary);
		}
	}
	/**
	 * 重新排序
	 */
	public void sortSim(int limit)
	{
		//重新排序
		Collections.sort(similaryBrand);
		//组合
		while(similaryBrand.size()>limit)
		{
			similaryBrand.pollLast();
		}
	}
	
	public void sortItem()
	{
		Collections.sort(this.categoryMall);
	}
//	/**
//	 * 排序解释信息
//	 */
//	public void sortExplain()
//	{
//		for(SonSimBean bean:similaryBrand)
//		{
//			bean.sortExplain();
//		}
//		for(CategoryMallBean bean:categoryMall)
//		{
//			for(SonBean beanson:bean.getRecommandMall())
//			{
//				beanson.sortExplain();
//			}
//		}
//	}


//	public MongObject get_id() {
//		return _id;
//	}
//
//
//	public void set_id(MongObject _id) {
//		this._id = _id;
//	}


	public boolean isUsed() {
		return used;
	}


	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
}
