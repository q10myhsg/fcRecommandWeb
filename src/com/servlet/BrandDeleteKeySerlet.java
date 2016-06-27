package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.control.BrandRecommendMallControl;

/**
 * 方法集的servlet
 * 
 * @author Administrator
 *
 */
public class BrandDeleteKeySerlet extends HttpServlet {

	public static Logger logger = Logger.getLogger(BrandDeleteKeySerlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BrandDeleteKeySerlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 可以直接在这里写 也可以 放在新建的 方法里写
		ServiceInit(request, response, out);
		out.flush();
		out.close();
	}

	private void ServiceInit(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out)
			throws ServletException, IOException {
		try {
			String servers = request.getParameter("server") == null ? ""
					: request.getParameter("server");
			String result = "";
			if (servers == null) {
				return;
			} else {
				if (servers.equals("getData")) {
					// String methodName= request.getParameter("methodName");
					// BrandRecommandMallControl control=new
					// BrandRecommandMallControl();
					// brandBeanList=control.getDate(page, count);
					// result=JsonUtil.toJson(bean2);
				} else if (servers.equals("del")) {
					String id = request.getParameter("_id");
					BrandRecommendMallControl control = new BrandRecommendMallControl();
					boolean flag = control.delete(id);
					result = Boolean.toString(flag);
					// System.out.println("servele:delete:" + result + "\t_id:"
					// + id);
				} else if (servers.equals("modify")) {
					String id = request.getParameter("_id");
					boolean used = Boolean.parseBoolean(request
							.getParameter("used"));
					boolean isMall = Boolean.parseBoolean(request
							.getParameter("isMall"));
					// System.out.println("used:" + used);
					BrandRecommendMallControl control = new BrandRecommendMallControl();
					boolean flag = control.modify(id, used, isMall);
					result = Boolean.toString(flag);
					// System.out.println("是否执行成功:" + flag);
				} else if (servers.equals("modifyOther")) {
					// 对某一个修改used 以及删除
					String id = request.getParameter("_id");
					boolean used = Boolean.parseBoolean(request
							.getParameter("used"));
					boolean isMall = Boolean.parseBoolean(request
							.getParameter("isMall"));
					Integer categoryId = Integer.parseInt(request
							.getParameter("categoryId"));
					if (categoryId == null) {
						categoryId = 0;
					}
					// System.out.println(request.getParameter("sonId"));
					Integer sonId = Integer.parseInt(request
							.getParameter("sonId"));
					if (sonId == null) {
						sonId = 0;
					}
					boolean isDelete = Boolean.parseBoolean(request
							.getParameter("isDelete"));
					Boolean isSimilary = Boolean.parseBoolean(request
							.getParameter("isSimilary"));
					if (isSimilary == null) {
						isSimilary = true;
					}
					Boolean isPointLog = false;
					int pointLog = 0;
					String pointLogText = null;
					try {
						String temp1 = request.getParameter("isPointLog");
						if (temp1 != null) {
							isPointLog = Boolean.parseBoolean(temp1);
						}
						temp1 = request.getParameter("pointLog");
						if (temp1 != null) {
							pointLog = Integer.parseInt(temp1);
						}
						pointLogText = request.getParameter("pointLogText");
					} catch (Exception e) {
						e.printStackTrace();
					}
					BrandRecommendMallControl control = new BrandRecommendMallControl();
					boolean flag = control.modifyOtherSon(id, used, categoryId,
							sonId, isMall, isDelete, isSimilary, isPointLog,
							pointLog, pointLogText);
					result = Boolean.toString(flag);
					// System.out.println("是否执行成功:" + flag);
				} else if (servers.equals("modifyOne")) {
					// 对某一个类做修改son的内容
					// 获取添加新的son
					// server=modifyOne&_id=&isMall=&categoryId=&sonId=&isCategory=&isSimilary=&index=&name&id=&value=&isAdd
					String _id = request.getParameter("_id");
					boolean isMall = Boolean.parseBoolean(request
							.getParameter("isMall"));
					String categoryIdString = request
							.getParameter("categoryId");
					Integer categoryId = 0;
					if (categoryIdString != null) {
						categoryId = Integer.parseInt(categoryIdString);
					}
					Integer sonId = Integer.parseInt(request
							.getParameter("sonId"));
					if (sonId == null) {
						sonId = 0;
					}
					boolean isCategory = Boolean.parseBoolean(request
							.getParameter("isCategory"));
					Boolean isSimilary = Boolean.parseBoolean(request
							.getParameter("isSimilary"));
					if (isSimilary == null) {
						isSimilary = true;
					}
					int index = Integer.parseInt(request.getParameter("index"));
					// System.out.println("first:"+request.getParameter("name"));
					String name = request.getParameter("name").replaceAll(
							"@@@", "&");
					if (name.equals("")) {
						result = "false";
					} else {
						// System.out.println("name:"+name);
						String valueString = request.getParameter("value");
						float value = 0f;
						if (valueString != null
								|| valueString.trim().length() > 0) {
							value = Float.parseFloat(valueString);
						}
						Boolean isAdd = Boolean.parseBoolean(request
								.getParameter("isAdd"));
						if (isAdd == null) {
							isAdd = true;
						}
						int pointLog = 0;
						String pointLogText = null;
						String temp1 = null;
						try {
							temp1 = request.getParameter("pointLog");
							if (temp1 != null) {
								pointLog = Integer.parseInt(temp1);
							}
							pointLogText = request.getParameter("pointLogText");
						} catch (Exception e) {
							e.printStackTrace();
						}

						BrandRecommendMallControl control = new BrandRecommendMallControl();
						boolean flag = control.modifyAddAndModifySon(_id,
								index, categoryId, isMall, isCategory,
								isSimilary, name, sonId, value, isAdd,
								pointLog, pointLogText);
						result = Boolean.toString(flag);
						// System.out.println("是否执行成功:" + flag);
					}
				}
			}
			out.print(result);
		} catch (Exception e) {
			logger.error("", e);
			out.print("");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	// public static void main(String[] args) {
	// String temp="asdf@@@@@@sldf@@@lsd";
	// System.out.println(temp.replaceAll("@@@","&"));
	// }
}
