package com.control;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.omg.PortableServer.Servant;

import com.bean.KeyValueBean;
import com.bean.KeyValueIntBean;
import com.bean.PropertyValueBean;
import com.config.ConfigData;
import com.db.MysqlConnection;
import com.servlet.InitServlet;

/**
 * mysql 控制流 在查询体系中不存在 多业态概念值存在城市概念
 * 
 * @author Administrator
 *
 */
public class MysqlControl {

	public static Object ob = new Object();

	public static int count = 1;

	/**
	 * 获取城市
	 * 
	 * @param mysql
	 * @return
	 */
	public static List<KeyValueIntBean> getCityName() {
		ArrayList<KeyValueIntBean> list = new ArrayList<KeyValueIntBean>();
		synchronized (ob) {
			if (count % 100 == 0) {
				count = 0;
			} else if (InitServlet.getCityMap() == null) {

			} else {
				for (KeyValueIntBean bean : InitServlet.getCityMap()) {
					list.add(bean);
				}
				return list;
			}
			count++;
			String temp = ConfigData.cityIdToNameSql;
			System.out.println(temp);
			ResultSet result = InitServlet.mysql.sqlSelect(temp).resultSet;
			try {
				while (result.next()) {
					KeyValueIntBean key = new KeyValueIntBean();
					key.value = result.getInt(1);
					key.key = result.getString(2);
					System.out.println(key.toString());
					list.add(key);
				}
				InitServlet.setCityMap(list);
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获取mall的属性
	 * 
	 * @param mallId
	 * @return
	 */
	public static PropertyValueBean getMallPropertyValue(int mallId, int cityId) {
		return getPropertyValue(InitServlet.mysql,
				ConfigData.mallFeatherOneSql, mallId, cityId);
	}

	/**
	 * 获取mall的属性
	 * 
	 * @param mallId
	 * @return
	 */
	public static PropertyValueBean getBrandPropertyValue(int brandId,
			int cityId) {
		return getPropertyValue(InitServlet.mysql,
				ConfigData.brandFeatherOneSql, brandId, cityId);
	}

	/**
	 * 获取mall的属性
	 * 
	 * @param mallId
	 * @return
	 */
	public static PropertyValueBean getBrandMallPropertyValue(int mallId,
			int cityId) {
		return getPropertyValue(InitServlet.mysql,
				ConfigData.brandMallFeatherOneSql, mallId, cityId);
	}

	/**
	 * 获取mall的属性
	 * 
	 * @param mallId
	 * @return
	 */
	public static PropertyValueBean getBrandBrandPropertyValue(int brandId,
			int cityId) {
		return getPropertyValue(InitServlet.mysql,
				ConfigData.brandBrandFeatherOneSql, brandId, cityId);
	}

	/**
	 * 获取属性值
	 * 
	 * @param mysql
	 * @param sql
	 * @param replace
	 * @return
	 */
	public static PropertyValueBean getPropertyValue(MysqlConnection mysql,
			String sql, int replaceId, int replaceCityId) {
		PropertyValueBean value = new PropertyValueBean();
		synchronized (ob) {
			// System.out.println(sql);
			String temp = sql.replaceAll("\\?", Integer.toString(replaceId))
					.replaceAll("@", Integer.toString(replaceCityId));
			ResultSet result = mysql.sqlSelect(temp).resultSet;
			ArrayList<KeyValueBean> list = new ArrayList<KeyValueBean>();
			try {
				if (result.next()) {
					int i = 0;
					ResultSetMetaData rsmd = result.getMetaData();
					while (true) {
						i++;
						if (i <= 3) {
							continue;
						}
						String columnName = rsmd.getColumnLabel(i);
						if (columnName == null) {
							i--;
							break;
						}
						String columnValue = result.getString(i);
						KeyValueBean key = new KeyValueBean();
						key.key = columnName;
						key.value = columnValue;
						list.add(key);
						// System.out.println(list);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			value.setProperties(list);
		}
		return value;
	}
}
