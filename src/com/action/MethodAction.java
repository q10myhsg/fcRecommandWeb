package com.action;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bean.ItemRecommendBean2;
import com.bean.KeyValueBean;
import com.bean.KeyValueIntBean;
import com.bean.MallRecommendBean2;
import com.control.BrandRecommendMallControl;
import com.control.MysqlControl;
import com.opensymphony.xwork2.ActionSupport;
import com.servlet.InitServlet;

public class MethodAction extends ActionSupport {

	public static Logger logger = Logger.getLogger(MethodAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 推荐的bean数据集
	 */
	private List<ItemRecommendBean2> brandBeanList = new ArrayList<ItemRecommendBean2>();
	/**
	 * 推荐的mall地数据集
	 */
	private List<MallRecommendBean2> mallBeanList = new ArrayList<MallRecommendBean2>();
	/**
	 * 城市
	 */
	private List<KeyValueIntBean> cityMap=new ArrayList<KeyValueIntBean>();
	/**
	 * json可以使 json这种格式 也可以是不同的字符串，int等类型
	 */
	private String json = "";
	
	
	private String mallRadio="";

	/**
	 * 页面
	 */
	private int page = 1;

	private int allPage = 0;
	/**
	 * 页面数量
	 */
	private int count = 5;
	/**
	 * 搜索关键词
	 */
	private String searchName = "";
	/**
	 * 查询区间
	 */
	private int count1 = 0;
	/**
	 * 
	 */
	private int count2 = 0;
	/**
	 * 比率
	 */
	private String countRate = "";
	/**
	 * 是否现实修改
	 */
	private String modifyBox="";
	/**
	 * mongo对应的id
	 */
	private String _id = "";
	/**
	 * 是否为mall
	 */
	private boolean isMall = false;

	private String mallString="false";
	
	private int cityId=0;
	/**
	 * 显示方法信息
	 * 
	 * @return
	 */
	public String getData() {
		logger.info("mallString:"+mallString+"\tmodifyBox:"+modifyBox);
		if(modifyBox.equals("modifyBox"))
		{
			modifyBox="true";
		}else{
			modifyBox="false";
		}
		if(mallRadio.equals("")||mallRadio.equals("mall"))
		{
			mallString="true";
			isMall=true;
		}else{
			mallString="false";
			isMall=false;
		}
		if (searchName.split("%").length > 3) {
			searchName = URLDecoder.decode(searchName);
		}
		logger.info("mallRadio:"+mallRadio+"isMall:"+isMall+"\t"+searchName.split("%").length + "进入" + "\tsearchName:"
				+ searchName + "\tpage:" + page + "\tcount:" + count);
		if (page <= 0) {
			page = 1;
		}
		if (isMall) {
			count = 3;
		} else {
			if (count <= 0) {
				count = 20;
			} else if (count > 100) {
				count = 100;
			}
		}
		try {
			BrandRecommendMallControl control = new BrandRecommendMallControl();
			if (isMall) {
				mallBeanList = control.getDateMall(searchName, page, count,cityId);
//				for(int i=0;i<mallBeanList.size();i++)
//				{
//					System.out.println(mallBeanList.get(i).getMallName()+"\t");
//					for(int j=0;j<mallBeanList.get(i).getCategoryItem().size();j++)
//					{
//						System.out.println(mallBeanList.get(i).getCategoryItem().get(j).getCategoryName()+"\t");
//						for(int m=0;m<mallBeanList.get(i).getCategoryItem().get(j).getRecommandShop().size();m++)
//						{
//							System.out.print(mallBeanList.get(i).getCategoryItem().get(j).getRecommandShop().get(m).getName()+"\t");
//						}
//					}
//					for(int j=0;j<mallBeanList.get(i).getSimilaryMall().size();j++)
//					{
//						ArrayList<KeyValueBean> list=mallBeanList.get(i).getSimilaryMall().get(j).getPropertyValue().getProperties();
//						for(KeyValueBean b:list)
//						{
//							System.out.println(b.toString());
//						}
//					}
//				}
				
			} else {
				brandBeanList = control.getDate(searchName, page, count,cityId);
//				for(int i=0;i<brandBeanList.size();i++)
//				{
//					System.out.println(brandBeanList.get(i).getBrandName());
//					for(SonSimBean bean:brandBeanList.get(i).getSimilaryBrand())
//					{
//						System.out.print(bean.getName()+":"+bean.getPropertyValue().getMonthlyhit()+"\t");
//					}
//				}
			}
			allPage = control.getDateCount(searchName, count);
			count1 = (page - 1) * count;
			count2 = (page) * count;
			DecimalFormat df = new DecimalFormat("#.00");
			if(allPage==0)
			{
				countRate="0.00";
			}else{
				countRate = df.format((page) / allPage);
			}
			cityMap=MysqlControl.getCityName();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

	}

	/**
	 * 删除mongo中对应的数据
	 */
	public String delete() {
		try {
			if (_id.length() > 0) {
				// BrandRecommandMallControl control=new
				// BrandRecommandMallControl();
				// control.delete(_id);
				// logger.info("删除id:"+_id);
			}
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}

	public List<ItemRecommendBean2> getBrandBeanList() {
		return brandBeanList;
	}

	public void setBrandBeanList(List<ItemRecommendBean2> brandBeanList) {
		this.brandBeanList = brandBeanList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public String getCountRate() {
		return countRate;
	}

	public void setCountRate(String countRate) {
		this.countRate = countRate;
	}

	public boolean isMall() {
		return isMall;
	}

	public void setMall(boolean isMall) {
		this.isMall = isMall;
	}

	public List<MallRecommendBean2> getMallBeanList() {
		return mallBeanList;
	}

	public void setMallBeanList(List<MallRecommendBean2> mallBeanList) {
		this.mallBeanList = mallBeanList;
	}

	public String getMallString() {
		return mallString;
	}

	public void setMallString(String mallString) {
		this.mallString = mallString;
	}

	public String getMallRadio() {
		return mallRadio;
	}

	public void setMallRadio(String mallRadio) {
		this.mallRadio = mallRadio;
	}

	public String getModifyBox() {
		return modifyBox;
	}

	public void setModifyBox(String modifyBox) {
		this.modifyBox = modifyBox;
	}

	public List<KeyValueIntBean> getCityMap() {
		return cityMap;
	}

	public void setCityMap(List<KeyValueIntBean> cityMap) {
		this.cityMap = cityMap;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	
}
