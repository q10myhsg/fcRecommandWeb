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
public class ItemRecommendBean2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MongoIdBean _id=null;

	/**
	 * mallName
	 */
	private String brandName="";
	
	private int brandId=0;
	/**
	 * 是否使用
	 */
	private boolean used=true;
	
	private int cityId=0;
	private String cityName=null;
	
	private int poingLog=0;
	
	/**
	 * 对应的属性值
	 */
	private PropertyValueBean propertyValue=null;
	/**
	 * 推荐的业态对应的物品
	 */
	private LinkedList<CategoryMallBean2> categoryMall=new LinkedList<CategoryMallBean2>();
	
	/**
	 * 品牌相似度信息
	 */
	private LinkedList<SonSimBean2> similaryBrand=new LinkedList<SonSimBean2>();

	
	
	public MongoIdBean get_id() {
		return _id;
	}


	public void set_id(MongoIdBean _id) {
		this._id = _id;
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


	
	public String getBrandName() {
		return brandName;
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




	public int getPoingLog() {
		return poingLog;
	}


	public void setPoingLog(int poingLog) {
		this.poingLog = poingLog;
	}


	public LinkedList<SonSimBean2> getSimilaryBrand() {
		return similaryBrand;
	}




	public void setSimilaryBrand(LinkedList<SonSimBean2> similaryBrand) {
		this.similaryBrand = similaryBrand;
	}




	public LinkedList<CategoryMallBean2> getCategoryMall() {
		return categoryMall;
	}

	public void setCategoryMall(LinkedList<CategoryMallBean2> categoryMall) {
		this.categoryMall = categoryMall;
	}
	public void addCategoryMall(CategoryMallBean2 categoryMall) {
		this.categoryMall.add(categoryMall);
	}
	/**
	 * 添加并替换重复的
	 * @param categoryMall
	 */
	public void addCategoryMallDistinct(CategoryMallBean2 categoryMall)
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

	public void addSimilaryBrand(SonSimBean2 similary) {
		boolean flag=false;
		for(SonSimBean2 son:this.similaryBrand)
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
	
	public void addSimilaryBrandDistinct(SonSimBean2 similary)
	{
		boolean flag=false;
		for(SonSimBean2 son:this.similaryBrand)
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
//		for(SonSimBean2 bean:similaryBrand)
//		{
//			bean.sortExplain();
//		}
//		for(CategoryMallBean2 bean:categoryMall)
//		{
//			for(SonBean2 beanson:bean.getRecommandMall())
//			{
//				beanson.sortExplain();
//			}
//		}
//	}



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
	
	
}
