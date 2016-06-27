package com.control;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.bean.CategoryItemBean;
import com.bean.CategoryItemBean2;
import com.bean.CategoryItemBean3;
import com.bean.CategoryMallBean;
import com.bean.CategoryMallBean2;
import com.bean.CategoryMallBean3;
import com.bean.ItemRecommendBean2;
import com.bean.ItemRecommendBean3;
import com.bean.MallRecommendBean2;
import com.bean.MallRecommendBean3;
import com.bean.SonBean2;
import com.bean.SonBean3;
import com.bean.SonSimBean2;
import com.bean.SonSimBean3;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.servlet.InitServlet;
import com.util.JsonUtil;

/**
 * 操纵方法
 * 
 * @author Administrator
 *
 */
public class BrandRecommendMallControl {

	/**
	 * 日志
	 */
	public static Logger logger = Logger
			.getLogger(BrandRecommendMallControl.class);

	private static final long serialVersionUID = 1L;

	public String collection = "";
	public boolean isMall = false;

	/**
	 * 从mongo中获取品牌推荐信息
	 * 
	 * @param searchName
	 *            搜索名
	 * 
	 * @param page
	 *            第几页
	 * @param count
	 *            每一页的数量
	 * @return
	 */
	public List<ItemRecommendBean2> getDate(String searchName, int page,
			int count,int cityId) {
		StringBuffer sb = new StringBuffer();
		sb.append("getData\ttime:").append(new Date()).append("isMall:false\t")
				.append("searchName:").append(searchName).append("\tpage:")
				.append(page).append("\tcount:").append(count).append("\tcityId:").append(cityId);
		logger.info(sb.toString());
		List<ItemRecommendBean2> result = new ArrayList<ItemRecommendBean2>();
		if (page <= 0) {
			return result;
		}
		collection = InitServlet.collectionName;
		DBCollection coll = InitServlet.mongo
				.getCollection(InitServlet.collectionName);
		DBObject query = new BasicDBObject();
		if (!searchName.equals("")) {
			Pattern pattern = Pattern.compile("\\.*" + searchName + "\\.*");
			query.put("brandName", new BasicDBObject("$regex", pattern));
		}
		if(cityId!=0)
		{
			query.put("cityId",cityId);
		}
		DBCursor cursor = coll.find(query, new BasicDBObject())
				.skip((page - 1) * count).limit(count);
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if (obj == null) {
				continue;
			}
			// 修改过结构
			ItemRecommendBean2 bean = (ItemRecommendBean2) JsonUtil
					.getDtoFromJsonObjStr(obj.toString(),
							ItemRecommendBean2.class, InitServlet.map2);
			// 设置值
			bean.setPropertyValue(MysqlControl.getBrandBrandPropertyValue(bean.getBrandId(),bean.getCityId()));
			for(CategoryMallBean2 categoryItem:bean.getCategoryMall())
			{
				for(SonBean2 item:categoryItem.getRecommandMall())
				{
					item.setPropertyValue(MysqlControl.getBrandMallPropertyValue(item.getId(),bean.getCityId()));
				}
			}
			for (SonSimBean2 cate : bean.getSimilaryBrand()) {
				cate.setPropertyValue(MysqlControl.getBrandBrandPropertyValue(cate.getSimId(),bean.getCityId()));
			}
			result.add(bean);
		}
		return result;
	}

	/**
	 * 从mongo中获取mall推荐信息
	 * 
	 * @param searchName
	 *            搜索名
	 * 
	 * @param page
	 *            第几页
	 * @param count
	 *            每一页的数量
	 * @return
	 */
	public List<MallRecommendBean2> getDateMall(String searchName, int page,
			int count,int cityId) {
		StringBuffer sb = new StringBuffer();
		sb.append("getData\ttime:").append(new Date())
				.append("\tisMall:true\t").append("searchName:")
				.append(searchName).append("\tpage:").append(page)
				.append("\tcount:").append(count).append("\tcityId:").append(cityId);
		logger.info(sb.toString());
		List<MallRecommendBean2> result = new ArrayList<MallRecommendBean2>();
		if (page <= 0) {
			return result;
		}
		collection = InitServlet.collectionMallName;
		isMall = true;
		DBCollection coll = InitServlet.mongo
				.getCollection(InitServlet.collectionMallName);
		DBObject query = new BasicDBObject();
		if (!searchName.equals("")) {
			Pattern pattern = Pattern.compile("\\.*" + searchName + "\\.*");
			query.put("mallName", new BasicDBObject("$regex", pattern));
		}
		if(cityId!=0)
		{
			query.put("cityId",cityId);
		}
		DBCursor cursor = coll.find(query, new BasicDBObject())
				.skip((page - 1) * count).limit(count);
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if (obj == null) {
				continue;
			}
			// 修改过结构
			MallRecommendBean2 bean = (MallRecommendBean2) JsonUtil
					.getDtoFromJsonObjStr(obj.toString(),
							MallRecommendBean2.class, InitServlet.map2);
			// 设置值
			bean.setPropertyValue(MysqlControl.getMallPropertyValue(bean.getMallId(),bean.getCityId()));
			for(CategoryItemBean2 categoryItem:bean.getCategoryItem())
			{
				for(SonBean2 item:categoryItem.getRecommandShop())
				{
					item.setPropertyValue(MysqlControl.getBrandPropertyValue(item.getId(),bean.getCityId()));
				}
			}
			for (SonSimBean2 cate : bean.getSimilaryMall()) {
				cate.setPropertyValue(MysqlControl.getMallPropertyValue(cate.getSimId(),bean.getCityId()));
			}
			result.add(bean);
		}
		return result;
	}

	/**
	 * 删除mongo中的key
	 * 
	 * @param _id
	 * @return
	 */
	public boolean delete(String _id) {
		try {
			if (_id.length() <= 0) {
				return false;
			}
			DBCollection coll = InitServlet.mongo
					.getCollection(InitServlet.collectionName);
			DBObject query = new BasicDBObject();
			// // // System.out.println("_id:" + _id);
			query.put("_id", new ObjectId(_id));
			WriteResult wr = coll.remove(query);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 修改每一条数据的使用状态
	 * 
	 * @param _id
	 * @param used
	 * @param isMall
	 * @return
	 */
	public boolean modify(String _id, boolean used, boolean isMall) {
		StringBuffer sb = new StringBuffer();
		sb.append("modify\ttime:").append(new Date()).append("\t_id:")
				.append(_id).append("\tused:").append(used).append("\tisMall:")
				.append(isMall);
		logger.info(sb.toString());
		if (_id.length() <= 0) {
			return false;
		}
		try {
			if (isMall) {
				collection = InitServlet.collectionMallName;
			} else {
				collection = InitServlet.collectionName;
			}
			DBCollection coll = InitServlet.mongo.getCollection(collection);
			DBObject query = new BasicDBObject();
			// // // System.out.println("修改_id:" + _id + "\tused:" + used);
			query.put("_id", new ObjectId(_id));
			DBObject bojQuery = (DBObject) coll.findOne(query);
			if (bojQuery == null) {
				return false;
			}
			bojQuery.removeField("_id");
			String strTemp = null;
			if (isMall) {
				MallRecommendBean3 bean = (MallRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								MallRecommendBean3.class, InitServlet.map3);
				bean.setUsed(used);
				strTemp = JsonUtil.getJsonStr(bean);
			} else {
				ItemRecommendBean3 bean = (ItemRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								ItemRecommendBean3.class, InitServlet.map3);
				bean.setUsed(used);
				strTemp = JsonUtil.getJsonStr(bean);
			}
			if (strTemp == null) {
				return false;
			}
			DBObject dbObject = (DBObject) JSON.parse(strTemp);
			WriteResult wr = coll.update(query, dbObject, true, false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改每一条数据的子内容
	 * 
	 * @param _id
	 *            mongoid
	 * @param used
	 *            是否使用
	 * @param category
	 *            分类
	 * @param catgorySonBean
	 *            为分类下对应的id <=0则为不使用
	 * @param isMall
	 *            是否为mall
	 * @param isDelete
	 *            是否删除
	 * @param isSimilary
	 *            是否为修改 相似的组
	 * @param isPointLog
	 *            是否是修改点击日志信息
	 * 
	 * @param pointLog
	 *            点击日志值
	 * @param poingLogText
	 *            为对应的描述日志
	 * @return
	 */
	public boolean modifyOtherSon(String _id, boolean used, int categoryId,
			int sonId, boolean isMall, boolean isDelete, boolean isSimilary,
			boolean isPointLog, int pointLog, String pointLogText) {
		if (_id.length() <= 0) {
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("modifyOtherSon\ttime:").append(new Date()).append("\t")
				.append("_id:").append(_id).append("\tused:").append(used)
				.append("\tcategoryId:").append(categoryId).append("\tsonId:")
				.append(sonId).append("\tisMall:").append(isMall)
				.append("\tisDelete:").append(isDelete).append("\tisSimilary:")
				.append(isSimilary).append("\tisPointLog:").append(isPointLog)
				.append("\tpoingLog:").append(pointLog)
				.append("\tpointLogText:").append(pointLogText);
		logger.info(sb.toString());
		try {
			if (isMall) {
				collection = InitServlet.collectionMallName;
			} else {
				collection = InitServlet.collectionName;
			}
			DBCollection coll = InitServlet.mongo.getCollection(collection);
			DBObject query = new BasicDBObject();
			// // // System.out.println("修改_id:" + _id + "\tused:" + used);
			query.put("_id", new ObjectId(_id));
			DBObject bojQuery = (DBObject) coll.findOne(query);
			if (bojQuery == null) {
				return false;
			}
			bojQuery.removeField("_id");
			String strTemp = null;
			// // System.out.println(bojQuery.toString());
			if (isMall) {
				MallRecommendBean3 bean = (MallRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								MallRecommendBean3.class, InitServlet.map3);
				if (isSimilary) {
					Iterator<SonSimBean3> iter = bean.getSimilaryMall()
							.iterator();
					while (iter.hasNext()) {
						SonSimBean3 son = iter.next();
						if (son.getSimId() == sonId) {
							if (isPointLog) {
								// System.out.println(son.getName() + "\t调整:"
								// + pointLog);
								son.setPointLog(pointLog);
								if (pointLogText != null) {
									son.setPointLogText(pointLogText);
								}
							} else {
								// System.out.println(son.getName() + "\t调整:"
								// + used);
								if (isDelete) {
									// 修改对应的数据
									iter.remove();
								} else {
									son.setUsed(used);
								}
							}
							break;
						}
					}
					//strTemp = JsonUtil.getJsonStr(bean);
				} else {
					if (sonId <= 0) {
						Iterator<CategoryItemBean3> iterator = bean
								.getCategoryItem().iterator();
						while (iterator.hasNext()) {
							CategoryItemBean3 cate = iterator.next();
							// // //
							// System.out.println("修改业态:"+cate.getCategoryName()+"\t"+used);
							if (cate.getCategory() == categoryId) {
								if (isDelete) {
									iterator.remove();
								} else {
									cate.setUsed(used);
								}
								break;
							}
						}
					} else {
						boolean flag = false;
						for (CategoryItemBean3 categoryBean : bean
								.getCategoryItem()) {
							// // //
							// System.out.println(categoryBean.getCategory());
							if (categoryBean.getCategory() == categoryId) {
								flag = true;
								Iterator<SonBean3> sonBean = categoryBean
										.getRecommandShop().iterator();
								while (sonBean.hasNext()) {
									SonBean3 b = sonBean.next();
									// System.out.print("删除:" + b.getName() +
									// "\t"
									// + b.getId());
									if (b.getId() == sonId) {
										if (isPointLog) {
											// // //
											// System.out.println(b.getName()
											// + "\t调整:" + pointLog);
											b.setPointLog(pointLog);
											if (pointLogText != null) {
												b.setPointLogText(pointLogText);
											}
										} else {
											if (isDelete) {
												// 修改对应的数据
												sonBean.remove();
											} else {
												b.setUsed(used);
											}
										}
										break;
									}
								}
							}
							if (flag) {
								break;
							}
						}
					}
				}
				strTemp = JsonUtil.getJsonStr(bean);
				// // // System.out.println(strTemp);
			} else {
				ItemRecommendBean3 bean = (ItemRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								ItemRecommendBean3.class, InitServlet.map3);
				if (isSimilary) {
					Iterator<SonSimBean3> iter = bean.getSimilaryBrand()
							.iterator();
					while (iter.hasNext()) {
						SonSimBean3 simBean = iter.next();
						if (simBean.getSimId() == sonId) {
							if (isPointLog) {
								// // // System.out.println(simBean.getName()
								// + "\t调整:" + pointLog);
								simBean.setPointLog(pointLog);
								if (pointLogText != null) {
									simBean.setPointLogText(pointLogText);
								}
							} else {
								if (isDelete) {
									// 修改对应的数据
									iter.remove();
								} else {
									simBean.setUsed(used);
								}
							}
							break;
						}
					}
				} else {
					if (sonId <= 0) {
						Iterator<CategoryMallBean3> iterator = bean
								.getCategoryMall().iterator();
						while (iterator.hasNext()) {
							CategoryMallBean3 cate = iterator.next();
							// // //
							// System.out.println("修改业态:"+cate.getCategoryName()+"\t"+used);
							if (cate.getCategory() == categoryId) {
								if (isDelete) {
									iterator.remove();
								} else {
									cate.setUsed(used);
								}
								break;
							}
						}
					} else {
						boolean flag = false;
						for (CategoryMallBean3 categoryBean : bean
								.getCategoryMall()) {
							if (categoryBean.getCategory() == categoryId) {
								flag = true;
								Iterator<SonBean3> sonBean = categoryBean
										.getRecommandMall().iterator();
								while (sonBean.hasNext()) {
									SonBean3 b = sonBean.next();
									if (b.getId() == sonId) {
										if (isPointLog) {
											// // //
											// System.out.println(b.getName()
											// + "\t调整:" + pointLog);
											b.setPointLog(pointLog);
											if (pointLogText != null) {
												b.setPointLogText(pointLogText);
											}
										} else {
											if (isDelete) {
												// 修改对应的数据
												sonBean.remove();
											} else {
												b.setUsed(used);
											}
										}
										break;
									}
								}
							}
							if (flag) {
								break;
							}
						}
					}
				}
				strTemp = JsonUtil.getJsonStr(bean);
			}
			DBObject dbObject = (DBObject) JSON.parse(strTemp);
			WriteResult wr = coll.update(query, dbObject, true, false);
			//System.out.println();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加一个属性
	 * 
	 * @param _id
	 * @param used
	 * @param categoryId
	 *            分类categoryId
	 * @param index位置
	 * @param isMall
	 * @param isCategory
	 *            是否为新增category 如果isCategory=false && isSimilary=false 则为修改子类
	 * @param isSimilary
	 * @param name
	 *            为名字
	 * @param id
	 *            id
	 * @param value
	 *            为值
	 * @return
	 */
	public boolean modifyAddAndModifySon(String _id, int index, int categoryId,
			boolean isMall, boolean isCategory, boolean isSimilary,
			String name, int id, float value, boolean isAdd, int pointLog, String pointLogText) {
		if (_id.length() <= 0) {
			return false;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("modifyOtherSon\ttime:").append(new Date()).append("\t_id:")
				.append(_id).append("\tindex:").append(index)
				.append("\tisMall:").append(isMall).append("\tisCategory:")
				.append(isCategory).append("\tisSimilary:").append(isSimilary)
				.append("\tname:").append(name).append("id:").append(id)
				.append("\tvalue:").append(value).append("\tisAdd:")
				.append(isAdd);
		logger.info(sb.toString());
		try {
			if (isMall) {
				collection = InitServlet.collectionMallName;
			} else {
				collection = InitServlet.collectionName;
			}
			DBCollection coll = InitServlet.mongo.getCollection(collection);
			DBObject query = new BasicDBObject();
			query.put("_id", new ObjectId(_id));
			DBObject bojQuery = (DBObject) coll.findOne(query);
			if (bojQuery == null) {
				return false;
			}
			bojQuery.removeField("_id");
			String strTemp = null;
			// // System.out.println(bojQuery.toString());
			if (isMall) {
				MallRecommendBean3 bean = (MallRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								MallRecommendBean3.class, InitServlet.map3);
				if (isSimilary) {
					SonSimBean3 son = null;
					if (isAdd) {
						son = new SonSimBean3();
					} else {
						son = bean.getSimilaryMall().get(index);
					}
					son.setName(name);
					son.setSimId(id);
					son.setValue(value);
					son.setPointLog(pointLog);
					son.setPointLogText(pointLogText);
					if (isAdd) {
						bean.getSimilaryMall().set(index, son);
					} else {
						bean.getSimilaryMall().add(index, son);
					}
				} else {
					// 如果为 添加分类
					if (isCategory) {
						Iterator<CategoryItemBean3> iterator = bean
								.getCategoryItem().iterator();
						boolean flag = false;
						if (isAdd) {
							while (iterator.hasNext()) {// 需要判断分类是否已经存在
								CategoryItemBean3 cate = iterator.next();
								// // //
								// System.out.println("修改业态:"+cate.getCategoryName()+"\t"+used);
								if (cate.getCategory() == id) {
									flag = true;
									break;
								}
							}
						}
						if (!flag) {
							CategoryItemBean3 cate = null;
							if (isAdd) {
								cate = new CategoryItemBean3();
							} else {
								cate = bean.getCategoryItem().get(index);
							}
							cate.setCategory(id);
							cate.setCategoryName(name);
							if (isAdd) {
								bean.getCategoryItem().add(index, cate);
							} else {
								bean.getCategoryItem().set(index, cate);
							}
						}
					} else {
						// 为添加子类
						boolean flag = false;
						for (CategoryItemBean3 categoryBean : bean
								.getCategoryItem()) {
							// //
							// System.out.println(categoryBean.getCategory());
							if (categoryBean.getCategory() == categoryId) {
								flag = true;
								Iterator<SonBean3> sonBean = categoryBean
										.getRecommandShop().iterator();
								boolean flag1 = false;
								while (sonBean.hasNext()) {
									SonBean3 b = sonBean.next();
									// System.out.print("删除:" + b.getName() +
									// "\t"
									// + b.getId());
									if (b.getId() == id) {
										flag1 = true;
										break;
									}
								}
								if (!flag1 && isAdd) {
									// 如果不存在则添加
									SonBean3 son = new SonBean3();
									son.setId(id);
									son.setName(name);
									son.setPointLog(pointLog);
									son.setPointLogText(pointLogText);
									categoryBean.getRecommandShop().add(index,
											son);
								} else if (!isAdd) {
									SonBean3 son = categoryBean
											.getRecommandShop().get(index);
									son.setId(id);
									son.setName(name);
									son.setPointLog(pointLog);
									son.setPointLogText(pointLogText);
									categoryBean.getRecommandShop().set(index,
											son);
								}
							}
							if (!flag) {
								continue;
							} else {
								break;
							}
						}
					}
				}
				strTemp = JsonUtil.getJsonStr(bean);
				// // // System.out.println(strTemp);
			} else {
				ItemRecommendBean3 bean = (ItemRecommendBean3) JsonUtil
						.getDtoFromJsonObjStr(bojQuery.toString(),
								ItemRecommendBean3.class, InitServlet.map3);
				if (isSimilary) {
					SonSimBean3 son = null;
					if (isAdd) {
						son = new SonSimBean3();
					} else {
						son = bean.getSimilaryBrand().get(index);
					}
					son.setName(name);
					son.setSimId(id);
					son.setValue(value);
					son.setPointLog(pointLog);
					son.setPointLogText(pointLogText);
					if (isAdd) {
						bean.getSimilaryBrand().add(index, son);
					} else {

						bean.getSimilaryBrand().set(index, son);
					}
				} else {
					if (isCategory) {
						Iterator<CategoryMallBean3> iterator = bean
								.getCategoryMall().iterator();
						boolean flag = false;
						if (isAdd) {
							while (iterator.hasNext()) {// 需要判断分类是否已经存在
								CategoryMallBean3 cate = iterator.next();
								// // //
								// System.out.println("修改业态:"+cate.getCategoryName()+"\t"+used);
								if (cate.getCategory() == id) {
									flag = true;
									break;
								}
							}
						}
						if (!flag) {
							CategoryMallBean3 cate = null;
							if (isAdd) {
								cate = new CategoryMallBean3();
							} else {
								cate = bean.getCategoryMall().get(index);
							}
							cate.setCategory(id);
							cate.setCategoryName(name);
							if (isAdd) {
								bean.getCategoryMall().add(index, cate);
							} else {
								bean.getCategoryMall().set(index, cate);
							}
						}
					} else {
						boolean flag = false;
						for (CategoryMallBean3 categoryBean : bean
								.getCategoryMall()) {
							if (categoryBean.getCategory() == categoryId) {
								flag = true;
								Iterator<SonBean3> sonBean = categoryBean
										.getRecommandMall().iterator();
								boolean flag1 = false;
								while (sonBean.hasNext()) {
									SonBean3 b = sonBean.next();
									// System.out.print("删除:" + b.getName() +
									// "\t"
									// + b.getId());
									if (b.getName() == name) {
										flag1 = true;
										break;
									}
								}
								if (!flag1 && isAdd) {
									// 如果不存在则添加
									SonBean3 son = new SonBean3();
									son.setId(id);
									son.setName(name);
									son.setPointLog(pointLog);
									son.setPointLogText(pointLogText);
									categoryBean.getRecommandMall().add(index,
											son);
								} else if (!isAdd) {
									SonBean3 son = categoryBean
											.getRecommandMall().get(index);
									son.setId(id);
									son.setName(name);
									son.setPointLog(pointLog);
									son.setPointLogText(pointLogText);
									categoryBean.getRecommandMall().set(index,
											son);
								}
							}
							if (flag) {
								continue;
							} else {
								break;
							}
						}
					}
				}
				strTemp = JsonUtil.getJsonStr(bean);
			}
			DBObject dbObject = (DBObject) JSON.parse(strTemp);
			WriteResult wr = coll.update(query, dbObject, true, false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从mongo中获取品牌推荐信息
	 * 
	 * @param searchName
	 *            搜索名
	 * @param count
	 *            每一页的数量
	 * @return 总共有几页
	 */
	public int getDateCount(String searchName, int count) {

		DBCollection coll = InitServlet.mongo.getCollection(collection);
		DBObject query = new BasicDBObject();
		if (!searchName.equals("")) {
			Pattern pattern = Pattern.compile("\\.*" + searchName + "\\.*");
			query.put("brandName", new BasicDBObject("$regex", pattern));
		}
		int cursor = coll.find(query, new BasicDBObject()).count();
		if (count <= 0) {
			return cursor;
		}
		return (cursor / count) + 1;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// InitServlet init = new InitServlet();
		// init.init();
		// BrandRecommandMallControl control = new BrandRecommandMallControl();
		// List<ItemRecommandBean> list = control.getDate("时间",1, 2);
		// for (ItemRecommandBean re : list) {
		// // // System.out.println(JsonUtil.getJsonStr(re));
		// }
		// control.delete("54c6f22b3655ee91d156c35c");
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("explain", ExplainBean.class);
		// map.put("similaryBrand", SonSimBean2.class);
		// map.put("categoryMall", CategoryMallBean2.class);
		// map.put("recommandMall", SonBean2.class);
		// map.put("similaryMall", SonSimBean2.class);
		// map.put("categoryItem", CategoryItemBean2.class);
		// map.put("recommandShop", SonBean2.class);
		// MongoDb mongo = new MongoDb("192.168.1.4:27017", "recommand");
		// DBCollection coll = mongo.getCollectons("mall20150205");
		// DBObject query = new BasicDBObject();
		// // // // System.out.println("修改_id:" + _id + "\tused:" + used);
		// query.put("_id", new ObjectId("54d42a5c8bece30234c43d2a"));
		//
		// DBObject bojQuery = (DBObject) coll.findOne(query);
		//
		// bojQuery.removeField("_id");
		// String strTemp = null;
		// MallRecommandBean2 bean = (MallRecommandBean2) JsonUtil
		// .getDtoFromJsonObjStr(bojQuery.toString(),
		// MallRecommandBean2.class, map);
		// // // System.out.println(JsonUtil.getJsonStr(bean));
		String url = "http://localhost:8080/zjCrawlerWeb/deleteKey?server=modifyOne&_id=54d42a5c8bece30234c43d34&isMall=true&categoryId=10&sonId=10&isCategory=true&isSimilary=false&index=0&name=%E7%BE%8E%E9%A3%9F&value=0&isAdd=false";
		String temp = "%E7%BE%8E%E9%A3%9F%E6%88%91";
		// // System.out.println(URLDecoder.decode(url, "utf-8"));
	}
}
