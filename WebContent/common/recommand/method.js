var serverIp = document.location.host// "127.0.0.1:8080"
var serverName = getProjectName();
// 获取项目名
function getProjectName() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(1, pathName.substr(1).indexOf('/') + 1);
	return (projectName);
}
// 提交表单
function formSubmit() {
	$("#searchName").val($("#searchNameMain").val());
	document.getElementById("searchAll").submit();
}
// 初始化
$(document)
		.ready(
				function() {
					$('#pageTag').html('');
					var mallRadio = $("[name=mallRadio][checked]").val();
					var currentPage = parseInt($('#page').val());
					var allPage = parseInt($('#allPage').val());
					// $("[name=mallRadio][checked]").each(function(){
					// alert(this.val());
					// });
					var count = parseInt($('#count').val());
					var searchName = encodeURIComponent(encodeURIComponent($(
							'#searchName').val()));
					// var searchName= $('#searchName').val();
					var length = 5;
					// alert(currentPage+".."+allPage+".."+length+".."+searchName);
					var str = "<div class=\"subnav\"><span class=\"prev-disabled\"></span><ul><li>";
					for (var i = currentPage - length; i < (currentPage
							+ length - 1); i = i + 1) {
						if (i <= 0) {
							continue;
						} else if (i > allPage) {
							break;
						} else if (i == currentPage) {
							str = str
									+ "<a color=\"#3399CC\" class=\"li0\" href=\"javascript:changePage("
									+ i + ")\">" + i + "</a>";//
						} else {
							str = str
									+ "<a class=\"current li0\" href=\"javascript:changePage("
									+ i + ")\">" + i + "</a>";//
						}
					}
					str = str
							+ "</li><li><input type=\"button\" class=\"nextClass\" onclick=\"javascript:nextPage();\" value=\"下一页\"/>"
							+ "<a class\"pageCountInfo\">" + currentPage + "/"
							+ allPage + "页</a></li>"

					str = str
							+ "<li  class=\"li2\"><a>跳转到</a><input   style=\"width:40px;\" type=\"text\" value=\"\" id =\"skipPage\">";
					str = str
							+ "</input><input type=\"button\" value=\"跳转\" onclick=\"javascript:skipPage()\"></li>";
					str = str + "</ul></div>";
					$('#pageTag').html(str);
					// 修改对应的颜色状态
					// name ="son_${pointLog}"
					$("[name^='son_']").each(function() {
						// alert($(this).attr("id"));

						var val = $(this).attr("name");
						// 获取值
						var pointLog = val.substring(4);
						if (pointLog == "-2") {
							$(this).addClass("divcss1");
						} else if (pointLog == "-1") {
							$(this).addClass("divcss2");
						} else if (pointLog == "0") {
							// $(this).addClass("divcss3");
						} else if (pointLog == "1") {
							$(this).addClass("divcss4");
						} else if (pointLog == "2") {
							$(this).addClass("divcss5");
						}
					});
					// var valn=$("modifyBox");
					// 显示所有属性
					// alert(valn.attr("checked"));
					// $("[id^=infoDivSim]").each(function() {
					// alert(this.attr("id"));
					// });
					// alert(valn.attr("checked"));
					// $("[id^=infoDivSim]").each(function() {
					// alert($(this).attr("id"));
					// });

					if ($("[name=modifyBox]").attr("checked") == "checked") {
						$("[id^=infoDivSim]").each(function() {
							// f1(null, $(this).attr("id"));
							$(this).css("display", "block");
						});
					} else {
						$("[id^=infoDivSim]").each(function() {
							// f2(null, $(this).attr("id"));
							$(this).css("display", "none");
						});
					}
					// 城市

					$("#citySelect").val($("#cityId").val());
					//alert($("#cityId").val())
					$("#citySelect").change(function() {
						$("#cityId").val($("#citySelect").val());
						//alert($("#cityId").val());
					});

					// 修改显示
					$("[name=modifyBox]")
							.click(
									function() {
										if ($("[name=modifyBox]").attr(
												"checked") == "checked") {
											$("[id^=infoDivSim]").each(
													function() {
														// f2(null,
														// $(this).attr("id"));
														$(this).css("display",
														"block");
													});
										} else {
											$("[id^=infoDivSim]").each(
													function() {
														// f1(null,
														// $(this).attr("id"));
														$(this).css("display",
														"none");
													});
										}
									});
				});

// 页面跳转
function skipPage() {
	var skipPage = $('#skipPage').val();
	if (skipPage.length <= 0) {
		return;
	}
	$("#page").val(skipPage);
	// alert($("#page").val());
	$("#searchAll").submit();
};

/**
 * 执行下一页
 */
function nextPage() {
	var skipPage = parseInt($("#page").val()) + 1;
	$("#page").val(skipPage);
	// alert($("#page").val());
	$("#searchAll").submit();
}

/**
 * 执行下一页
 */
function beforPage() {
	var skipPage = parseInt($("#page").val()) - 1;
	if (skipPage <= 1) {
		skipPage = 1;
	}
	$("#page").val(skipPage);
	// alert($("#page").val());
	$("#searchAll").submit();
}
// 跳转页
function changePage(pag) {
	// alert(pag)
	$("#page").val(pag);
	$("#searchAll").submit();
}

// 删除数据 主
function deleteKey(id, obj) {
	// delete.do?_id=${_id.$oid}
	// alert("http://"+serverIp+"/zjCrawlerWeb/deleteKey?server=del&_id="+id);
	var htmlobj = $.ajax({
		url : "http://" + serverIp + "/" + serverName
				+ "/deleteKey?server=del&_id=" + id,
		async : false
	});
	if (htmlobj.responseText == 'true') {
		// alert("成功删除:"+name);
		var obj2 = $("#id" + id);
		obj2.html("删除成功")
		obj2.css("color", "red");
		obj2.css("background-color", "black");
		obj2.attr("href", "javascript:void(0);")
		// obj.html("删除成功");
	} else {
		var obj2 = $("#id" + id);
		obj2.html("未成功")
		obj2.css("color", "green");
	}
};
// 修改使用 状态 主
function modifyKey(id, obj, used, isMall) {
	if (used) {
		used = false;
	} else {
		used = true;
	}
	// delete.do?_id=${_id.$oid}
	// alert("http://"+serverIp+"/zjCrawlerWeb/deleteKey?server=modify&_id="+id+"&used="+used);
	var htmlobj = $.ajax({
		url : "http://" + serverIp + "/" + serverName
				+ "/deleteKey?server=modify&_id=" + id + "&used=" + used
				+ "&isMall=" + isMall,
		async : false
	});
	// alert(htmlobj.responseText);
	if (htmlobj.responseText == 'true') {
		// alert("成功修改:"+name);
		var obj2 = $("#used" + id);
		var str = obj2.attr("href");
		str = str.substr(0, str.lastIndexOf(","));
		if (used) {
			obj2.html("使用")
			obj2.css("color", "blue");
			obj2.css("background-color", "white");
		} else {
			obj2.html("不用")
			obj2.css("color", "red");
			obj2.css("background-color", "black");
		}
		obj2.attr("href", str + "," + used + ");")
		// obj.html("删除成功");
	} else {
		var obj2 = $("#id" + id);
		obj2.html("未修改")
		obj2.css("color", "green");
	}
};

// modifyOtherKey('${_id.$oid}',this,${categoryId},0,false,false,false)
function modifyOtherKey(keyId, obj, categoryId, sonId, isMall, isDelete,
		isSimilary, used) {
	if (used) {
		used = false;
	} else {
		used = true;
	}
	var temp = "http://" + serverIp + "/" + serverName
			+ "/deleteKey?server=modifyOther&_id=" + keyId + "&used=" + used
			+ "&isMall=" + isMall + "&isDelete=" + isDelete + "&isSimilary="
			+ isSimilary + "&categoryId=" + categoryId + "&sonId=" + sonId;

	// alert(temp)
	var htmlobj = $.ajax({
		url : temp,
		async : false
	});
	// alert(htmlobj.responseText);
	if (htmlobj.responseText == 'true') {
		// alert("成功修改:"+name);
		// alert("进入");
		var temp = "";
		if (isSimilary == true) {
			temp = "#sim" + keyId + "_" + isDelete + "_" + sonId;

		} else {
			if (sonId <= 0) {
				temp = "#" + keyId + "_" + categoryId + "_" + isDelete + "_";
				return false;
			} else {
				temp = "#" + keyId + "_" + categoryId + "_" + isDelete + "_"
						+ sonId;
			}
		}
		var obj2 = $(temp);
		// alert("#" + keyId + "_" + categoryId + "_" + isDelete + "_");
		var str = obj2.attr("href");
		// alert(str)
		str = str.substr(0, str.lastIndexOf(","));
		// alert(str);
		if (isDelete == true) {
			if (used) {
				obj2.html("删除")
				obj2.css("color", "block");
			} else {
				obj2.html("已删除")
				obj2.css("color", "green");
				obj2.css("background-color", "black");
			}
			// 被删除后触发不可用
			obj2.attr("href", "javascript:void(0);");
		} else {
			if (used) {
				obj2.html("使用")
				obj2.css("color", "blue");
				obj2.css("background-color", "white");
			} else {
				obj2.html("不用")
				obj2.css("color", "red");
				obj2.css("background-color", "black");
			}
			obj2.attr("href", str + "," + used + ");")
		}

		// obj.html("删除成功");
	} else {
		var obj2 = $(this);
		obj2.html("未修改")
		obj2.css("color", "green");
	}
}
// 添加
function modifyFromAdd(_id, isMall, categoryId, isCategory, isSimilary, index) {
	var obj = $("#showFrom");
	obj.css("display", "none");
	// 重置
	$("#modify_id").val(_id);
	$("#modify_isMall").val(isMall);
	$("#modify_categoryId").val(categoryId);
	$("#modify_isCategory").val(isCategory);
	$("#modify_isSimilary").val(isSimilary);
	$("#modify_index").val(index);
	$("#modify_value").val("");
	$("#modify_name").val("");
	$("#modify_sonId").val("");
	$("#modify_isAdd").val(true);
	$("#modify_pointLog").val("");
	$("#modify_pointLogText").val("");
	$("#pointLogText").val("");
	obj.css("display", "block");
	scall();
}
// 修改
/**
 * isPointLog 是否需要为修改点 pointLog 为select 标签 pointLogText 为对应的日志信息
 */
function modifyFromModify(this_id, _id, isMall, categoryId, isCategory,
		isSimilary, index, name, sonId, value, pointLog, pointLogText) {
	var obj = $("#showFrom");
	obj.css("display", "none");
	// 重置
	// alert(this_id.id);
	var obj2 = $("#" + this_id).attr("id");
	// alert(obj2);
	$("#html_id").val(obj2);
	$("#modify_id").val(_id);
	$("#modify_isMall").val(isMall);
	$("#modify_categoryId").val(categoryId);
	$("#modify_isCategory").val(isCategory);
	$("#modify_isSimilary").val(isSimilary);
	$("#modify_value").val(value);
	$("#modify_index").val(index);
	$("#modify_name").val(name);
	$("#modify_sonId").val(sonId);
	$("#modify_isAdd").val(false);
	$("#modify_isPointLog").val(false);
	$("#modify_pointLog").val(pointLog);
	// alert($("#logSelect option:selected").val());
	// 设置select 选项
	// alert(pointLog);
	var point = pointLog;
	// alert($("#logSelect option:selected").text());
	$("#logSelect").val(point);
	// alert($("#logSelect option:selected").text())
	$("#modify_pointLogText").val(pointLogText);
	$("#pointLogText").val(pointLogText);
	obj.css("display", "block");
	scall(thisObj);
}

// 撤消
function modifySonSkip() {
	var obj = $("#showFrom");
	obj.css("display", "none");
}

function modifySon() {
	// server=modifyOne&_id=&isMall=&categoryId=&sonId=&isCategory=&isSimilary=&index=&name&value=&isAdd
	// alert("进入");
	var _id = $("#modify_id").val();
	// alert("1:" + _id);
	var isMall = $("#modify_isMall").val();
	// alert("2:" + isMall);
	var categoryId = $("#modify_categoryId").val();
	// alert("3:" + categoryId);
	var isCategory = $("#modify_isCategory").val();
	// alert("4:" + isCategory);
	var isSimilary = $("#modify_isSimilary").val();
	// alert("5:" + isSimilary);
	var index = $("#modify_index").val();
	// alert("6:" + index);
	var name = $("#modify_name").val();
	// alert("7:" + name);
	var sonId = $("#modify_sonId").val();
	// alert("8:" + sonId);
	var value = $("#modify_value").val();
	var isAdd = $("#modify_isAdd").val();
	// 获取日志状态
	var temp1 = $("#logSelect").val();
	var temp2 = $("#modify_pointLog").val();
	var isPointLog = false;
	// alert(isPointLog);
	if (temp1 != temp2) {
		isPointLog = true;
	}
	// alert(isPointLog);
	var pointLog = temp1;
	var pointLogText = $("#pointLogText").val();
	// alert(pointLogText);
	var pointLogText2 = $("#modify_pointLogText").val();
	var name1 = name.replace(/&/g, "@@@")
	// alert(pointLogText2);
	var temp = "&_id=" + _id + "&isMall=" + isMall + "&categoryId="
			+ categoryId + "&sonId=" + sonId + "&isCategory=" + isCategory
			+ "&isSimilary=" + isSimilary + "&index=" + index + "&name="
			+ name1 + "&value=" + value + "&isAdd=" + isAdd + "&pointLog="
			+ pointLog;
	// alert(temp);
	if (pointLogText != pointLogText2) {
		isPointLog = true;
		temp = temp + "&pointLogText=" + pointLogText;
	}
	if (isAdd) {
		temp = "http://" + serverIp + "/" + serverName
				+ "/deleteKey?server=modifyOne" + temp;
	} else {
		temp = temp = "http://" + serverIp + "/" + serverName
				+ "/deleteKey?server=modifyOther" + temp;
	}
	temp = temp + "&isPointLog=" + isPointLog;
	// alert(encodeURI(temp))
	// alert(temp);
	var htmlobj = $.ajax({
		url : encodeURI(temp),
		async : false
	});
	// alert(htmlobj.responseText);
	if (htmlobj.responseText == 'true') {
		$("#modify_pointLog").val(temp1);
		// ${_id.$oid}_${category}_${id}
		var idString = _id + "_" + categoryId + "_" + sonId;
		if (isSimilary == "true") {
			idString = _id + "__" + sonId;
		}
		$("#" + idString).attr("name", "son_" + pointLog);
		$("#" + idString).removeClass("divcss1");
		$("#" + idString).removeClass("divcss2");
		$("#" + idString).removeClass("divcss3");
		$("#" + idString).removeClass("divcss4");
		$("#" + idString).removeClass("divcss5");
		// 修改颜色
		if (pointLog == -2) {
			$("#" + idString).addClass("divcss1");
		} else if (pointLog == -1) {
			$("#" + idString).addClass("divcss2");
		} else if (pointLog == 0) {
			$("#" + idString).addClass("divcss3");
		} else if (pointLog == 1) {
			$("#" + idString).addClass("divcss4");
		} else if (pointLog == 2) {
			$("#" + idString).addClass("divcss5");
		}
		// 修改方法字段
		var te = $("#" + $("#html_id").val()).attr("href");
		// alert(te.substring(0,te.length));
		var vaTemp = te.substring(0, te.lastIndexOf("'", te.length - 4));
		var va = vaTemp.lastIndexOf(",");
		// alert(te.substring(0,va));
		$("#" + $("#html_id").val()).attr(
				"href",
				te.substring(0, va) + "," + pointLog + ",'" + pointLogText
						+ "');");
		// alert("修改成功");
	} else {
		alert("修改失败")
	}
	modifySonSkip();
}

function f1(brandName) {
	var tt = document.getElementById("infoDiv" + brandName);
	if (tt != null) {
		tt.style.display = "block";
		// tt.innerHTML="infoDiv show ";
	}
};

function f2(brandName) {
	var cc = document.getElementById("infoDiv" + brandName);
	if (cc != null) {
		cc.style.display = "none";
	}
};

/**
 * 鼠标划过 事件
 * 
 * @param brandName
 * @param namePara
 */
function f1(brandName, namePara) {
	$("[id^=infoDivSim" + namePara + "]").each(function() {
		if ($(this) != null) {
			$(this).css("display", "block");
		}
	});
};
/**
 * ' 鼠标移开 事件
 * 
 * @param brandName
 * @param namePara
 */
function f2(brandName, namePara) {
	$("[id^=infoDivSim" + namePara + "]").each(function() {
		if ($(this) != null) {
			$(this).css("display", "none");
		}
	});
};

/**
 * 调整
 * 
 * @param obj
 * @param objVal
 */
function changeElement(obj, objVal) {
	// alert(objVal);
	var regS = new RegExp("['\"]", "gi");
	var temp = objVal.replace(regS, "");
	// alert(temp);
	$(obj).val(temp);
}

function sc1(DivId) {
	var Div = document.getElementById(DivId);
	var iWidth = document.documentElement.clientWidth;
	var te = document.documentElement.scrollTop ? document.documentElement.scrollTop
			: document.body.scrollTop;
	var iHeight = te; // ;document.documentElement.clientHeight + te * 2;
	var t = 254; // div 高
	var r = 400; // div 宽
	Div.style.top = (iHeight + t) + "px";
	Div.style.left = (iWidth - r) / 2 + "px";
}

function scall() {

	sc1("showFrom");
}
window.onscroll = scall;
window.onresize = scall;
window.onload = scall;

// onMouseOver="f1(this.id,'${_id.$oid}')"
// onmouseout="f2(this.id,'${_id.$oid}')"
