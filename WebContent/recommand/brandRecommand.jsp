<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>推荐数据</title>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="../common/js/jquery.min.js"></script>
<script type="text/javascript" src="../common/js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../common/recommand/method.js"></script>
<link href="./common/img/favicon.ico" rel="icon" type="image/x-icon" />
<link href="../common/css/recommand.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	
</script>
<style type="text/css">
</style>
</head>
<body>
	<div class="bodyBack">
		<div>
			<s:if test="%{mallString == 'true'}">mall推荐</s:if>
			<s:else>brand推荐</s:else>
		</div>
		<div class="search">
			<form action="./getData.do" method="post" id="searchAll">
				<div class="i-search-before">
					<input type="hidden" id="page" name="page" value="${page}" /> <input
						type="hidden" id="allPage" value="${allPage}" />
					 <input
						type="hidden" id="isMall" value="${mallString}" />
					<input
						type="hidden" name="cityId" id="cityId" value="${cityId}" />
					<div
						style="border: 1px; width: 50%; float: left; margin: 0px auto;">
						<!-- <input id="mallString" name="mallString" style="width: 100%" value="${mallString}" /> -->
						<div style="width: 13%; float: left;">
							<an style="font-size:12px;font-weight:bold;">mall</a>
						</div>
						<div style="width: 13%; float: left;">
							<s:if test="%{mallString == 'true'}">
								<input type="radio" name="mallRadio" value="mall"
									checked="checked" />
							</s:if>
							<s:else>
								<input type="radio" style="float: left;" name="mallRadio"
									value="mall" />
							</s:else>
						</div>
						<div style="width: 12%; float: left;">
							<a style="font-size: 12px; font-weight: bold;">brand</a>
						</div>
						<div style="width: 12% float:left;">
							<s:if test="%{mallString == 'true'}">
								<input type="radio" name="mallRadio" value="brand" />
							</s:if>
							<s:else>
								<input type="radio" name="mallRadio" value="brand"
									checked="checked" />
							</s:else>
						</div>
						<div style="width: 12%; float: left;">
							<a style="font-size: 12px; font-weight: bold;">修改</a>
						</div>
						<div style="width: 12%; float: left;">
							<s:if test="%{modifyBox == 'true'}">
								<input type="checkbox" name="modifyBox" value="modifyBox"
									checked="checked" />
							</s:if>
							<s:else>
								<input type="checkbox" name="modifyBox" value="modifyBox" />
							</s:else>
						</div>
						<div style="width:10%;float:left;">
							<select id="citySelect" size="1">
							<s:iterator value="cityMap" id="innerCityMap">
							<option value="${value}">${key}</option>
							</s:iterator>
							</select>
						</div>
					</div>
					<div
						style="font-size: 14px; width: 15%; float: left; margin: 0px auto;">
						<a style="font-size: 12px; font-weight: bold;">页显示数</a>
					</div>
					<div
						style="border: 1px; width: 5%; float: left; margin: 0px auto;">
						<input id="count" name="count" style="width: 100%"
							value="${count}" />
					</div>
					<div
						style="font-size: 14px; width: 10%; float: left; margin: 0px auto;">
						<a style="font-size: 12px; font-weight: bold;"> 搜索词</a>
					</div>
					<div
						style="border: 1px; width: 16%; float: left; margin: 0px auto;">
						<input id="searchName" maxlength="26" style="width: 100%"
							name="searchName" value="${searchName}" />
					</div>
				</div>
			</form>

			<div class="i-search">
				<form action="./getData.do" method="post" id="search">

					<input class="search-bar" maxlength="26" value=""
						id="searchNameMain" name="searchName" /> <input
						id="searchSubmitButton" value="搜索" class="search-submit"
						type="button" onclick="formSubmit()" />
				</form>
			</div>

		</div>
		<a>数量区间:<em style="color: red">${count1}</em>-<em
			style="color: red">${count2}</em> 比率:<em style="color: red">${countRate}</em></a>
		<br></br>
		<table border="1" width="100%" class="main">
			<tr>
				<th width="20%" class="infoTitle"><a><b><s:if
								test="isMall">mall信息</s:if> <s:else>品牌信息</s:else></b></a></th>
				<th width="80%" class="infoTitle"><a><b>分类推荐</b></a></th>
			</tr>
			<s:if test="%{mallString == 'true'}">
				<s:iterator value="mallBeanList" id="outer">
					<tr>
						<table border="1">
							<col width="3%" />
							<col width="3%" />
							<col width="4%" />
							<col width="10%" />
							<col width="80%" />
							<td><a
								href="javascript:deleteKey('${_id.$oid}',this);"
								id="id${_id.$oid}">删除</a></td>
							<s:if test="used==true">
								<td><a
									href="javascript:modifyKey('${_id.$oid}',this,${used},true);"
									id="used${_id.$oid}">使用</a></td>
							</s:if>
							<s:else>
								<td><a
									href="javascript:modifyKey('${_id.$oid}',this,${used},true);"
									style="background-color: black; color: red;"
									id="used${_id.$oid}">不用</a></td>

							</s:else>
							<td>
								<span>${cityName}</span>
							</td>
							<td>
								<div style="">
									<div class="one">
										<a
											href="http://www.dianping.com/search/keyword/2/0_${mallName}"
											target="_Blank" id="${mallId}" color="color"><em>${mallName}</em></a>
									</div>
									<div id="infoDivSim${_id.$oid}" class="two">
										<s:iterator value="propertyValue.properties"
											id="inOuterSimilaryProperty" status="iosp">
											<p align="left">${key}:${value}</p>
										</s:iterator>
									</div>
								</div>
							</td>
							<td>
								<table border="1">
									<col width="15%" />
									<s:iterator value="categoryItem" id="outerCategory" status="cs">
										<tr>
											<td>
												<div style="width: 100%; float: left;">
													<s:if test="%{modifyBox == 'true'}">
														<div style="width: 100%; height: 30%">
															<div class="usedClass2">
																<a
																	href="javascript:modifyFromAdd('${_id.$oid}',true,${category},true,false,${cs.index});"
																	id="${_id.$oid}_${category}_true_">添加</a>
															</div>
															<div class="usedClass2">
																<a
																	href="javascript:modifyFromModify('${_id.$oid}_${category}_true__','${_id.$oid}',true,${category},true,false,${cs.index},'${categoryName}',${category},0);"
																	id="${_id.$oid}_${category}_true__">修改</a>
															</div>
														</div>
														<div style="width: 100%; height: 30%">
															<div class="usedClass2">
																<a
																	href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,true,true,false,true);"
																	id="${_id.$oid}_${category}_true_">删除</a>
															</div>
															<div class="usedClass2">
																<s:if test="used==true">
																	<a
																		href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,true,false,false,true);"
																		id="${_id.$oid}_${category}_false_">使用</a>
																</s:if>
																<s:else>
																	<a
																		href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,true,false,false,false);"
																		style="background-color: black; color: red;"
																		id="${_id.$oid}_${category}_false_">不用</a>
																</s:else>
															</div>
														</div>
													</s:if>
													<a>${categoryName}</a>
												</div>
											</td>
											<s:iterator value="recommandShop" id="brand" status="ist">
												<s:if test="#ist.index lt 10">
													<td width="7%;" id="${_id.$oid}_${category}_${id}"
														name="son_${pointLog}">
														<div style="width: 100%">
															<div class="one">
																<s:if test="%{modifyBox == 'true'}">
																	<div style="width: 100%;">
																		<div style="width: 100%; height: 40%">
																			<div class="usedClass2">
																				<a
																					href="javascript:modifyFromAdd('${_id.$oid}',true,${category},false,false,${ist.index});"
																					id="${_id.$oid}_${category}_true_${ist.index}">添加</a>
																			</div>
																			<div class="usedClass2">
																				<a
																					href="javascript:modifyFromModify('${_id.$oid}_${category}_true_${ist.index}_','${_id.$oid}',true,${category},false,false,${ist.index},'${name}',${id},${value},${pointLog},'${pointLogText}');"
																					id="${_id.$oid}_${category}_true_${ist.index}_">修改</a>
																			</div>
																		</div>
																		<div style="width: 100%; height: 40%;">
																			<div class="usedClass2">
																				<a
																					href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},true,true,false,true);"
																					color="color2"
																					id="${_id.$oid}_${category}_true_${id}">删除</a>
																			</div>
																			<div class="usedClass2">
																				<s:if test="used==true">
																					<a
																						href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},true,false,false,true);"
																						color="color2"
																						id="${_id.$oid}_${category}_false_${id}">使用</a>
																				</s:if>
																				<s:else>
																					<a
																						href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},true,false,false,false);"
																						color="color2"
																						style="background-color: black; color: red;"
																						id="${_id.$oid}_${category}_false_${id}">不用</a>
																				</s:else>
																			</div>
																		</div>
																	</div>
																</s:if>
																<a
																	href="http://www.dianping.com/search/keyword/2/0_${name}"
																	target="_Blank" id="${id}" color="color"><em>${name}</em></a>
															</div>
															<div id="infoDivSim${_id.$oid}" class="two">
																<s:iterator value="propertyValue.properties"
																	id="inOuterSimilaryProperty" status="iosp">
																	<p align="left">${key}:${value}</p>
																</s:iterator>
															</div>
														</div>
												</s:if>
											</s:iterator>
										</tr>
									</s:iterator>
								</table>
							</td>
							<tr>
								<table>
									<tr>
										<td style="width: 10%;"><em>相似的mall:</em></td>
										<s:iterator value="similaryMall" id="outerSimilary"
											status="st">
											<s:if test="#st.index lt 10">
												<td class="simitd" id="${_id.$oid}__${simId}"
													name="son_${pointLog}">
													<div style="width: 100%">
														<div class="one">
															<s:if test="%{modifyBox == 'true'}">
																<div>
																	<div style="width: 100%; height: 30%">
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyFromAdd('${_id.$oid}',true,0,false,true,${st.index});"
																				id="${_id.$oid}_${category}_fasle_${st.index}">添加</a>
																		</div>
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyFromModify('${_id.$oid}_${category}_false_${st.index}_','${_id.$oid}',true,0,false,true,${st.index},'${name}',${simId},${value},${pointLog},'${pointLogText}');"
																				id="${_id.$oid}_${category}_false_${st.index}_">修改</a>
																		</div>
																	</div>
																	<div style="width: 100%; height: 30%">
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},true,true,true,true);"
																				color="color2" id="sim${_id.$oid}_true_${simId}">删除</a>
																		</div>
																		<div class="usedClass2">
																			<s:if test="used==true">
																				<a
																					href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},true,false,true,true);"
																					color="color2" id="sim${_id.$oid}_false_${simId}">使用</a>
																			</s:if>
																			<s:else>
																				<a
																					href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},true,false,true,false);"
																					color="color2"
																					style="background-color: black; color: red;"
																					id="sim${_id.$oid}_false_${simId}">不用</a>
																			</s:else>
																		</div>
																	</div>
																</div>
															</s:if>
															<a class="colorSim"
																href="http://www.dianping.com/search/keyword/2/0_${name}"
																target="_Blank" id="${id}">${name}</a>
														</div>
														<div id="infoDivSim${_id.$oid}_${id}" class="two">
															<s:iterator value="propertyValue.properties"
																id="outerSimilaryProperty" status="osp">
																<p align="left">${key}:${value}</p>
															</s:iterator>
														</div>
													</div>
												</td>
											</s:if>
										</s:iterator>
									</tr>
								</table>
							</tr>
						</table>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<s:iterator value="brandBeanList" id="outer">
					<tr>
						<table border="1" width="100%">
							<col width="5%" />
							<col width="5%" />
							<col width="10%" />
							<col width="80%" />
							<td><a
								href="javascript:deleteKey('${_id.$oid}',this);"
								id="id${_id.$oid}"><br> <br> <br>删除<br>
									<br> <br> <br></a></td>
							<s:if test="used==true">
								<td><a
									href="javascript:modifyKey('${_id.$oid}',this,${used},false);"
									id="used${_id.$oid}">使用</a></td>
							</s:if>
							<s:else>
								<td><a
									href="javascript:modifyKey('${_id.$oid}',this,${used},false);"
									style="background-color: black; color: red;"
									id="used${_id.$oid}">不用</a></td>

							</s:else>
							<td>
								<span>${cityName}</span>
							</td>
							<td>
								<div style="">
									<div class="one">
										<a
											href="http://www.dianping.com/search/keyword/2/0_${brandName}"
											target="_Blank" id="${brandId}" color="color"><em>${brandName}</em></a>
										<!-- onMouseOver="f1(this.id)"
									onmouseout="f2(this.id)" -->
									</div>
									<div id="infoDivSim${_id.$oid}" class="two">
										<s:iterator value="propertyValue.properties"
											id="inOuterSimilaryProperty" status="iosp">
											<p align="left">${key}:${value}</p>
										</s:iterator>
									</div>
								</div>
							</td>
							<td>
								<table border="1">
									<col width="15%" />
									<s:iterator value="categoryMall" id="outerCategory">
										<tr>
											<td>
												<div style="width: 100%; float: left;">
													<s:if test="%{modifyBox == 'true'}">
														<div style="width: 100%; height: 30%">
															<div class="usedClass2">
																<a
																	href="javascript:modifyFromAdd('${_id.$oid}',false,${category},true,false,${cs.index});"
																	id="${_id.$oid}_${category}_true_">添加</a>
															</div>
															<div class="usedClass2">
																<a
																	href="javascript:modifyFromModify('${_id.$oid}_${category}_true__','${_id.$oid}',false,${category},true,false,${cs.index},'${categoryName}',${category},0,${pointLog},'${pointLogText}');"
																	id="${_id.$oid}_${category}_true__">修改</a>
															</div>
														</div>
														<div style="width: 100%; height: 30%">
															<div class="usedClass2">
																<a
																	href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,false,true,false,true);"
																	color="color2" id="${_id.$oid}_${category}_true_">删除</a>
															</div>
															<div class="usedClass2">
																<s:if test="used==true">
																	<a
																		href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,false,false,false,true);"
																		color="color2" id="${_id.$oid}_${category}_false_">使用</a>
																</s:if>
																<s:else>
																	<a
																		href="javascript:modifyOtherKey('${_id.$oid}',this,${category},0,false,false,false,false);"
																		color="color2"
																		style="background-color: black; color: red;"
																		id="${_id.$oid}_${category}_false_">不用</a>
																</s:else>
															</div>
														</div>
													</s:if>
													<a color="color2">${categoryName}</a>
												</div>
											</td>
											<s:iterator value="recommandMall" id="mall" status="ist">
												<s:if test="#ist.index lt 10">
													<td width="7%;" id="${_id.$oid}_${category}_${id}"
														name="son_${pointLog}">
														<div style="width: 100%">
															<div class="one">
																<s:if test="%{modifyBox == 'true'}">
																	<div style="width: 100%; height: 40%">
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyFromAdd('${_id.$oid}',false,${category},false,false,${ist.index});"
																				id="${_id.$oid}_${category}_true_${ist.index}">添加</a>
																		</div>
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyFromModify('${_id.$oid}_${category}_true_${ist.index}_','${_id.$oid}',false,${category},false,false,${ist.index},'${name}',${id},${value},${pointLog},'${pointLogText}');"
																				id="${_id.$oid}_${category}_true_${ist.index}_">修改</a>
																		</div>
																	</div>
																	<div style="width: 100%; height: 40%;">
																		<div class="usedClass2">
																			<a
																				href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},false,true,false,true);"
																				color="color2"
																				id="${_id.$oid}_${category}_true_${id}">删除</a>
																		</div>
																		<div class="usedClass2">
																			<s:if test="used==true">
																				<a
																					href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},false,false,false,true);"
																					color="color2"
																					id="${_id.$oid}_${category}_false_${id}">使用</a>
																			</s:if>
																			<s:else>
																				<a
																					href="javascript:modifyOtherKey('${_id.$oid}',this,${category},${id},false,false,false,false);"
																					color="color2"
																					style="background-color: black; color: red;"
																					id="${_id.$oid}_${category}_false_${id}">不用</a>
																			</s:else>
																		</div>
																	</div>
																</s:if>

																<a
																	href="http://www.dianping.com/search/keyword/2/0_${name}"
																	target="_Blank" id="${id}" color="color"><em>${name}</em></a>
															</div>
															<div id="infoDivSim${_id.$oid}" class="two">
																<s:iterator value="propertyValue.properties"
																	id="inOuterSimilaryProperty" status="iosp">
																	<p align="left">${key}:${value}</p>
																</s:iterator>
															</div>
														</div>
												</s:if>
											</s:iterator>
										</tr>
									</s:iterator>
								</table>
							</td>
							<tr>
								<table width="100%">
									<td style="float: left"><em>相似的brand:</em></td>
									<s:iterator value="similaryBrand" id="outerSimilary"
										status="st">
										<s:if test="#st.index lt 10">
											<td class="simitd" id="${_id.$oid}__${simId}"
												name="son_${pointLog}">
												<div style="width: 100%">
													<div class="one">
														<s:if test="%{modifyBox == 'true'}">
															<div style="width: 100%; height: 30%">
																<div class="usedClass2">
																	<a
																		href="javascript:modifyFromAdd('${_id.$oid}',false,0,false,true,${st.index});"
																		id="${_id.$oid}_${category}_fasle_${st.index}">添加</a>
																</div>
																<div class="usedClass2">
																	<a
																		href="javascript:modifyFromModify('${_id.$oid}_${category}_false_${st.index}_','${_id.$oid}',false,0,false,true,${st.index},'${name}',${simId},${value},${pointLog},'${pointLogText}');"
																		id="${_id.$oid}_${category}_false_${st.index}_">修改</a>
																</div>
															</div>
															<div style="width: 100%; height: 30%">
																<div class="usedClass2">
																	<a
																		href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},false,true,true,true);"
																		id="sim${_id.$oid}_true_${simId}">删除</a>
																</div>
																<div class="usedClass2">
																	<s:if test="used==true">
																		<a
																			href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},false,false,true,true);"
																			id="sim${_id.$oid}_false_${simId}">使用</a>
																	</s:if>
																	<s:else>
																		<a
																			href="javascript:modifyOtherKey('${_id.$oid}',this,0,${simId},false,false,true,false);"
																			style="background-color: black; color: red;"
																			id="sim${_id.$oid}_false_${simId}">不用</a>
																	</s:else>
																</div>
															</div>
														</s:if>
														<a class="colorSim"
															href="http://www.dianping.com/search/keyword/2/0_${name}"
															id="${id}" target="_Blank">${name}</a>
													</div>
													<div id="infoDivSim${_id.$oid}_${id}" class="two">
														<s:iterator value="propertyValue.properties"
															id="outerSimilaryProperty" status="osp">
															<p align="left">${key}:${value}</p>
														</s:iterator>
													</div>
												</div>
											</td>
										</s:if>
									</s:iterator>
								</table>
							</tr>
						</table>
					</tr>
				</s:iterator>
			</s:else>
		</table>

		<br>
		<div class="pagin fr" id="pageTag"></div>
		<div id="showFrom" class="mainForm">
			<div>
				<div style="width: 100%; height: 10%; float: left;"></div>
				<input type="hidden" id="html_id" value="" /> <input type="hidden"
					id="modify_id" value="" /><input type="hidden" id="modify_id"
					value="" /><input type="hidden" id="modify_isMall" value="" /><input
					type="hidden" id="modify_categoryId" value="" /><input
					type="hidden" id="modify_isCategory" value="" /><input
					type="hidden" id="modify_isSimilary" value="" /><input
					type="hidden" id="modify_index" value="" /> <input type="hidden"
					id="modify_isAdd" value="" /><input type="hidden"
					id="modify_isPointLog" value="" /><input type="hidden"
					id="modify_PointLog" value="" />
				<div style="width: 100%; height: 13%; float: left;">
					<div style="width: 40%; float: left;"></div>
					<div style="width: 40%; float: left;">
						<a>name:</a>
					</div>
					<div style="width: 40%; float: left;">
						<input id="modify_name" style="width: 100%;" value="" />
					</div>
				</div>
				<div style="width: 100%; height: 13%; float: left;">
					<div style="width: 40%; float: left;"></div>
					<div style="width: 40%; float: left;">
						<a>id:</a>
					</div>
					<div style="width: 40%; float: left;">
						<input id="modify_sonId" style="width: 100%;" value="" />
					</div>
				</div>
				<div style="width: 100%; height: 13%; float: left;">
					<div style="width: 40%; float: left;"></div>
					<div style="width: 40%; float: left;">
						<a>value:</a>
					</div>
					<div style="width: 40%; float: left;">
						<input id="modify_value" style="width: 100%;" value="" />
					</div>
				</div>
				<div style="width: 100%; height: 13%; float: left;">
					<div style="width: 40%; float: left;"></div>
					<div style="width: 40%; float: left;">
						<a>状态:</a>
					</div>
					<div style="width: 40%; float: left;">
						<select id="logSelect">
							<option value="-2">很差</option>
							<option value="-1">较差</option>
							<option value="0">一般</option>
							<option value="1">较好</option>
							<option value="2">很好</option>
						</select>
					</div>
				</div>
				<div style="width: 100%; float: left;">
					<div style="width: 40%; float: left;"></div>
					<div style="width: 40%; float: left;">
						<a>备注:</a>
					</div>
					<div style="width: 40%; float: left;">
						<input type="hidden" id="modify_pointLogText" value="" />
						<textarea rows="1" cols="40" id="pointLogText" style="width: 100%"
							value="" onkeyup="changeElement(this,this.value)"></textarea>
					</div>
				</div>
				<div style="width: 100%; height: 13%; float: left;">
					<div style="wdith: 10%; height: 13%; overflow: hidden;"></div>
					<div
						style="width: 50%; float: left; text-align: center; margin: 0 auto;">
						<input type="button" style="width: 60%;" value="撤消"
							onclick="javascript:modifySonSkip();" />
					</div>

					<div style="width: 50%; float: left; text-align: center">
						<input type="button" style="width: 60%;" value="提交"
							onclick="javascript:modifySon();" />
					</div>
				</div>
			</div>
		</div>
		<div class="pageEnd">
			<!-- <img src="../img/background1.jpg"/> -->
		</div>
	</div>

</body>
</html>
