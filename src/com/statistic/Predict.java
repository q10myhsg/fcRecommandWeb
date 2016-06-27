package com.statistic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

import com.bean.CategoryItemBean;
import com.bean.CategoryMallBean;
import com.bean.ItemRecommendBean3;
import com.bean.SonBean3;
import com.bean.SonSimBean3;
import com.db.MongoDb;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.util.FileUtil2;
import com.util.JsonUtil;

/**
 * 临时 命中使用
 * 
 * @author Administrator
 *
 */
public class Predict {

	public String fileBrand = "e:\\brand.txt";
	public HashMap<String, Object> map = new HashMap<String, Object>();
	public MongoDb mongo = null;
	public String brand = "brandEnd20150207";
	public String mall = "mall20150205";
	public DBCollection coll = null;

	public Predict() {
		mongo = new MongoDb("192.168.1.4:27017", "recommand");
		coll = mongo.getCollection(brand);
		//map.put("explain", ExplainBean.class);
		map.put("similaryBrand", SonSimBean3.class);
		map.put("categoryMall", CategoryMallBean.class);
		map.put("recommandMall", SonBean3.class);
		//map.put("_id", MongObject.class);
		map.put("similaryMall", SonSimBean3.class);
		map.put("categoryItem", CategoryItemBean.class);
		map.put("recommandShop", SonBean3.class);
	}

	public void run() {
		FileUtil2 file = new FileUtil2(fileBrand, "utf-8", false);
		LinkedList<String> list = file.readAndClose();
		if (list == null) {
			System.out.println("文件不存在:" + fileBrand);
		}
		while (list.size() > 0) {
			String str = list.pollFirst().trim();
			String tempCom=runSearchCom(str);
			String temp = runSearch(str);
			System.out.println(str + "\t"+tempCom+"\t" + temp);
		}
	}

	/**
	 * 查询满足的数量
	 * 
	 * @param searchName
	 * @return
	 */
	public String runSearch(String searchName) {
		int counttrue = 0;
		int countfalse = 0;
		DBObject query = new BasicDBObject();
		if (!searchName.equals("")) {
			Pattern pattern = Pattern.compile("\\.*" + searchName + "\\.*");
			query.put("brandName", new BasicDBObject("$regex", pattern));
		}
		DBCursor cursor = coll.find(query, new BasicDBObject());
		//ArrayList<ItemRecommandBean> result = new ArrayList<ItemRecommandBean>();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if (obj == null) {
				continue;
			}
		//	System.out.println(obj.toString());
			// 修改过结构
			ItemRecommendBean3 bean = (ItemRecommendBean3) JsonUtil
					.getDtoFromJsonObjStr(obj.toString(),
							ItemRecommendBean3.class, map);
			if (bean.isUsed()) {
				counttrue++;
			}else{
				countfalse++;
			}
		//	result.add(bean);
		}
		return counttrue+"\t"+countfalse;
	}
	/**
	 * 完全匹配
	 * @param searchName
	 * @return
	 */
	public String runSearchCom(String searchName) {
		int counttrue = 0;
		int countfalse = 0;
		DBObject query = new BasicDBObject();
		if (!searchName.equals("")) {
			
			query.put("brandName",searchName);
		}
		DBCursor cursor = coll.find(query, new BasicDBObject());
		//ArrayList<ItemRecommandBean> result = new ArrayList<ItemRecommandBean>();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if (obj == null) {
				continue;
			}
		//	System.out.println(obj.toString());
			// 修改过结构
			ItemRecommendBean3 bean = (ItemRecommendBean3) JsonUtil
					.getDtoFromJsonObjStr(obj.toString(),
							ItemRecommendBean3.class, map);
			if (bean.isUsed()) {
				counttrue++;
			}else{
				countfalse++;
			}
		//	result.add(bean);
		}
		return counttrue+"\t"+countfalse;
	}
	
	public static void main(String[] args) {
		Predict pre=new Predict();
		pre.run();
	}
}
