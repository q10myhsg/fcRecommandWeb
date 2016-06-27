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
public class MallRecommendBean2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MongoIdBean _id=null;
	/**
	 * mallName
	 */
	private String mallName="";
	/**
	 * mall 对应的id
	 */
	private int mallId=0;
	
	private int cityId=0;
	private String cityName=null;
	
	/**
	 * 推荐的业态对应的物品
	 */
	private LinkedList<CategoryItemBean2> categoryItem=new LinkedList<CategoryItemBean2>();
	
	/**
	 * 品牌相似度信息
	 */
	private LinkedList<SonSimBean2> similaryMall=new LinkedList<SonSimBean2>();
	
	/**
	 * 对应的属性值
	 */
	private PropertyValueBean propertyValue=null;

	/**
	 * 是否使用
	 */
	private boolean used=true;
	
	private int  poingLog=0;
	
	
	
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

	public int getPoingLog() {
		return poingLog;
	}

	public void setPoingLog(int poingLog) {
		this.poingLog = poingLog;
	}

	public MongoIdBean get_id() {
		return _id;
	}

	public void set_id(MongoIdBean _id) {
		this._id = _id;
	}

	public int getMallId() {
		return mallId;
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

	
	public LinkedList<CategoryItemBean2> getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(LinkedList<CategoryItemBean2> categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	public void setCategoryItem(int index,CategoryItemBean2 categoryItem)
	{
		this.categoryItem.set(index, categoryItem);
	}
	
	public void addCategoryItem(CategoryItemBean2 categoryItem) {
		this.categoryItem.add(categoryItem);
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public LinkedList<SonSimBean2> getSimilaryMall() {
		return similaryMall;
	}

	public void setSimilaryMall(LinkedList<SonSimBean2> similaryMall) {
		this.similaryMall = similaryMall;
	}

	public void addSimilaryMall(SonSimBean2 similary) {
		boolean flag=false;
		for(SonSimBean2 son:this.similaryMall)
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
//	/**
//	 * 排序解释信息
//	 */
//	public void sortExplain()
//	{
//		for(SonSimBean2 bean:similaryMall)
//		{
//			bean.sortExplain();
//		}
//		for(CategoryItemBean2 bean:categoryItem)
//		{
//			for(SonBean2 beanson:bean.getRecommandShop())
//			{
//				beanson.sortExplain();
//			}
//		}
//	}
	
	public PropertyValueBean getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(PropertyValueBean propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
	
}
