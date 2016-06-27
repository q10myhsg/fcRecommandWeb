package com.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import test.clearn.DataIndexStatic;

import com.bean.CategoryItemBean;
import com.bean.CategoryItemBean2;
import com.bean.CategoryItemBean3;
import com.bean.CategoryMallBean;
import com.bean.CategoryMallBean2;
import com.bean.CategoryMallBean3;
import com.bean.KeyValueIntBean;
import com.bean.PropertyValueBean;
import com.bean.SonBean;
import com.bean.SonBean2;
import com.bean.SonBean3;
import com.bean.SonSimBean;
import com.bean.SonSimBean2;
import com.bean.SonSimBean3;
import com.db.MongoDb;
import com.db.MysqlConnection;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标准推荐结构
	 */
	public static HashMap<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 从数据库中获取的数据
	 * struct结构
	 */
	public static HashMap<String, Object> map2 = new HashMap<String, Object>();

	/**
	 * 带属性参数的数据mongo 结构
	 */
	public static HashMap<String, Object> map3 = new HashMap<String, Object>();

	/**
	 * 品牌对应的数据 key brandId
	 */
	public static HashMap<Integer, PropertyValueBean> brandMap = new HashMap<Integer, PropertyValueBean>();

	/**
	 * 购物中心对应的数据
	 */
	public static HashMap<Integer, PropertyValueBean> mallMap = new HashMap<Integer, PropertyValueBean>();
	
	/**
	 * 城市
	 */
	public static List<KeyValueIntBean> cityMap=null;
	
	public static void setCityMap(List<KeyValueIntBean> city)
	{
		cityMap=city;
	}
	public static List<KeyValueIntBean> getCityMap()
	{
		return cityMap;
	}

	public static String mongoIp = "";
	
	public static int	mongoPort=27017;
	
	public static String mongoDb="";
	/**
	 *  mongo 用户
	 */
	public static String mongoUser=null;
	/**
	 * mongo pwd
	 */
	public static String mongoPwd=null;
	
	
	public static String mysqlIp="";
	
	public static int mysqlPort=3306;
	
	public static String mysqlDatabase="";
	
	public static String mysqlUser=null;
	
	public static String mysqlPwd=null;
	
	public String configFile =null;
			

	public static String collectionName = "";// "brand";
	// //;//"brandRecommandExplain";
	public static String collectionMallName = "";

	public static String path = "";
	// public String[] brandOthers = null;
	private Logger log = Logger.getLogger(InitServlet.class);
	public static MongoDb mongo = null;

	
	public static MysqlConnection mysql=null;
	
	/**
	 * 获取mall属性sql
	 */
	public static String getMallPropertySql=null;
	
	public static int mallPropertyCount=-1;
	/**
	 * 获取brand属性sql
	 */
	public static String getBrandPropertySql=null;
	
	public static int brandPropertyCount=-1;
	/**
	 * 获取给brand推荐对应的mall的属性
	 */
	public static String getBrandmallPropertySql=null;
	public static int brandMallPropertyCount=-1;
	/**
	 * 获取brand推荐的对应的bran的属性
	 */
	public static String getBrandBrandPropertySql=null;
	public static int brandBrandPropertyCount=-1;
	
	public void init() {
		try {
			log.info("调用初始化程序");
			String temp = this.getClass().getResource("/").getPath();
			log.info(temp);
			if (temp.indexOf("WEB-INF") <= 0) {
				// 则为resin
				path = temp.substring(0, temp.lastIndexOf("/"))
						+ "/webapps/fcRecommendWeb";
			} else {
				path = temp.substring(0, temp.indexOf("WEB-INF"));
			}

			configFile=path+ "/build/config.properties";
			log.info("服务初始化");
			map2.put("similaryBrand", SonSimBean2.class);
			map2.put("categoryMall", CategoryMallBean2.class);
			map2.put("recommandMall", SonBean2.class);
			map2.put("similaryMall", SonSimBean2.class);
			map2.put("categoryItem", CategoryItemBean2.class);
			map2.put("recommandShop", SonBean2.class);
			map2.put("_id",ObjectId.class);

			map3.put("similaryBrand", SonSimBean3.class);
			map3.put("categoryMall", CategoryMallBean3.class);
			map3.put("recommandMall", SonBean3.class);
			map3.put("similaryMall", SonSimBean3.class);
			map3.put("categoryItem", CategoryItemBean3.class);
			map3.put("recommandShop", SonBean3.class);
			
			
			map.put("similaryBrand", SonSimBean.class);
			map.put("categoryMall", CategoryMallBean.class);
			map.put("recommandMall", SonBean.class);
			map.put("similaryMall", SonSimBean.class);
			map.put("categoryItem", CategoryItemBean.class);
			map.put("recommandShop", SonBean.class);
			
			// map2.put("similaryBrand", SonSimBean2.class);
			// map2.put("categoryMall", CategoryMallBean.class);
			// map2.put("recommandMall", SonBean2.class);
			// map2.put("similaryMall", SonSimBean2.class);
			// map2.put("categoryItem", CategoryItemBean.class);
			// map2.put("recommandShop", SonBean2.class);
			// 读取配置文件
			readConfig();
			if(mongoUser==null)
			{
				System.out.println(mongoIp+"\t"+mongoPort+"\t"+mongoDb);
				mongo = new MongoDb(mongoIp,mongoPort,mongoDb);
			}else{
				mongo=new MongoDb(mongoIp,mongoPort,mongoDb,mongoUser,mongoPwd);
			}
			mysql=new MysqlConnection(mysqlIp,mysqlPort,mysqlDatabase,mysqlUser,mysqlPwd);
			log.info("mongo初始化成功");
			readFile();
			log.info("读取文件成功");
			log.info("读取城市");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		// TaskMain main=new TaskMain();
	}

	public void readConfig() {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					configFile));
			// InputStream in =
			// getClass().getResourceAsStream("/IcisReport.properties");
			try {
				props.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(props.toString());
		collectionName = props.getProperty("brand");
		collectionMallName = props.getProperty("mall");
		mongoIp=props.getProperty("mongoIp");
		String mongo_port=props.getProperty("mongoPort");
		if(mongo_port!=null && mongo_port.equals(""))
		{
			mongoPort=Integer.parseInt(mongo_port);
		}
		mongoDb=props.getProperty("mongoDb");
		mongoUser=props.getProperty("mongoUser");
		mongoPwd=props.getProperty("mongoPwd");
		mysqlIp=props.getProperty("mysqlIp");
		String mysql_port=props.getProperty("mysqlPort");
		if(mysql_port!=null&&mysql_port.equals(""))
		{
			mysqlPort=Integer.parseInt(mysql_port);
		}
		mysqlDatabase=props.getProperty("mysqlDatabase");
		mysqlUser=props.getProperty("mysqlUser");
		mysqlPwd=props.getProperty("mysqlPwd");
		if(mysqlUser==null||mysqlUser.equals(""))
		{
			mysqlUser=null;
		}
		if(mysqlPwd==null||mysqlPwd.equals(""))
		{
			mysqlPwd=null;
		}
		if(mongoUser==null||mongoUser.equals(""))
		{
			mongoUser=null;
		}
		if(mysqlPwd==null||mysqlPwd.equals(""))
		{
			mongoPwd=null;
		}
	}

	public void readFile() {
		// 读取品牌文件
	}

	public void destory() {
		log.info("终止服务");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public static void main(String[] args) {
		// System.out.println(InitServlet.class.getClassLoader().getResource("").getPath());
		// System.exit(1);
		String str = "麦子甲艺		17558	5	1	11	78		0	0	5";

		String str2 = "BEER ALL 啤酒圈		17557	242	186	898	10760	82	40	26	5		82";
		String[] strList2 = str2.split("\t");
		String[] strList = str.split("\t");
		System.out.println();
		System.out.println(Arrays.toString(strList));
		System.out.println(Arrays.toString(strList2));
		System.out.println(str.split("\t").length);
		float[] tempVal = new float[4];
		if (!strList[DataIndexStatic.monthlyHits].equals("")) {
			tempVal[0] = (Float
					.parseFloat(strList[DataIndexStatic.monthlyHits]));
		}
		if (strList.length >= DataIndexStatic.score + 1) {
			tempVal[1] = (Float.parseFloat(strList[DataIndexStatic.score]));
		}
		if (!strList[DataIndexStatic.popularity].equals("")) {
			tempVal[2] = (Float.parseFloat(strList[DataIndexStatic.popularity]));
		}
		if (!strList[DataIndexStatic.avgPrice].equals("")) {
			// System.out.println(brandName+"\t"+strList[DataIndexStatic.avgPrice]);
			tempVal[3] = (Float.parseFloat(strList[DataIndexStatic.avgPrice]));
		}
		System.out.println(Arrays.toString(tempVal));
	}
}
