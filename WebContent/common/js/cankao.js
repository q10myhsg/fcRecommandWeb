var PriceChart;
var PloyVals = "";
var goods;
var cartnum = 0;
var totalprice = 0;
var ordernum = 0;// 关注单数量
var totalorderprice = 0;// 关注单总价格
var issyn = false;
function ClickSearch(a) {
	var txtValue = document.getElementById("txtSearch").value;
	if (txtValue != null && txtValue != "") {
		a.href = "./search?key=" + txtValue;
		// var key = encodeURI(txtValue);
	}
}
$(document).ready(function() {
	InitPloyVals();
	ShowIndexHtml();// 读取并显示指数信息
	MenuHD();// 右上角采购单弹窗
	LoadAttentionHtml();// 加载关注单
	LoadPurchaseHtml();// 加载采购单
});
$(function() {
	Highcharts.setOptions({
		colors : [ '#058DC7', '#ED561B', '#50B432', '#DDDF00', '#24CBE5',
				'#64E572', '#FF9655', '#FFF263', '#6AF9C4' ]
	});
	ShowPriceChart();// 价格趋势图
	ShowFavorableChart();// 口碑图
	ShowLifeCycleChart();// 生命周期图
});

// 读取并显示指数信息
function ShowIndexHtml() {
	var Stands = dwr.util.getValue("Stands");
	var condition = new QueryCondition();
	condition = {
		"stands" : Stands,
		"purchasePloy" : PloyVals
	}
	// alert(PloyVals);
	var ids = new Array();
	ids = Stands.split(',');

	var title_html = "";
	var index_html = "";
	title_html += "<table>\r\n";
	title_html += "<tbody>\r\n";
	title_html += "<tr>\r\n";
	title_html += "<td class=\"td150\"></td>\r\n";
	index_html += "<table>\r\n";
	index_html += "<tbody>\r\n";
	index_html += "<tr>\r\n";
	index_html += "<td class=\"tdindex150\">联嘉云指数:</td>\r\n";
	Search
			.getContrast(
					condition,
					{
						callback : function(data) {
							if (data != '') {
								var obj = $.parseJSON(data);
								if (obj != null) {
									if (obj != null) {
										goods = obj;
										$
												.each(
														obj,
														function(n, item) {
															if (item.standardID > 0) {
																var title = item.title;
																var name=IndexStatusToName(item.indexStatus);
																var index = parseFloat(item.uniIndex);
																var mmm=n + 1;
																var subCnt=28;
																if(obj.length<=2){
																	mmm=n+4;
																	subCnt=40;
																}
																if (item.title.length > subCnt) {
																	title = item.title
																			.substring(
																					0,
																					subCnt-3)
																			+ "...";
																}
																title_html += "<td class=\"tdtitle"
																		+ mmm
																		+ "\"><a  href=\"./info?id="
																		+ item.standardID
																		+ "\" title=\""
																		+ item.title
																		+ "\" target=\"_blank\">"
																		+ title
																		+ "</a></td>\r\n";
																if ((n + 1) < obj.length)
																	title_html += "<td class=\"tdvs\">VS</td>\r\n";
												
																if ((n + 1) < obj.length){
																	index_html += "<td class=\"tdtitle"
																		+ mmm
																		+ "\">"
																		+ index
																				.toFixed(2)
																		+ "</td>\r\n";
																	index_html += "<td class=\"tdvs\">VS</td>\r\n";
																}else{
																	index_html += "<td class=\"tdtitle"
																		+ mmm
																		+ "\">"
																		+ index
																				.toFixed(2)
																		+ "<p onmouseover=\"ShowNote(this);\" onmouseout=\"HideNote(this);\" style='width:15px;float:right;height:14px;background:url(./common/img/note.gif);'></p></td>\r\n";
																}
															}

														});
									}
								}
							}
						},
						async : false
					});

	title_html += "</tr>\r\n";
	title_html += "</tbody>\r\n";
	title_html += "</table>\r\n";
	index_html += "</tr>\r\n";
	index_html += "</tbody>\r\n";
	index_html += "</table>\r\n";
	index_html += "<div class=\"dashboard\">\r\n";
	index_html += "<table style=\"display: table;\">\r\n";
	index_html += "<tbody>\r\n";
	index_html += "<tr>\r\n";
	index_html += "<td class=\"sum_title\">属性指数</td>\r\n";
	index_html += "<td rowspan=\"2\"><div class=\"dashboard_line\"></div></td>\r\n";
	index_html += "<td class=\"sum_title\">购买指数</td>\r\n";
	index_html += "<td rowspan=\"2\"><div class=\"dashboard_line\"></div></td>\r\n";
	index_html += "<td class=\"sum_title\">稳定性指数</td>\r\n";
	index_html += "<td rowspan=\"2\"><div class=\"dashboard_line\"></div></td>\r\n";
	index_html += "<td class=\"sum_title\">趋势指数</td>\r\n";
	index_html += "<td rowspan=\"2\"><div class=\"dashboard_line\"></div></td>\r\n";
	index_html += "<td class=\"sum_title no_border\">风险指数</td>\r\n";
	index_html += "</tr>\r\n";
	var propertyIndex = "";
	var purchaseIndex = "";
	var stableIndex = "";
	var trendIndex = "";
	var riskIndex = "";
	$.each(goods,
			function(n, item) {
				if (item.standardID > 0) {
					if ((n + 1) < goods.length) {
						propertyIndex += "<span class=\"index" + (n + 1)
								+ "\">"
								+ parseFloat(item.propertyIndex).toFixed(2)
								+ "</span><br/>";
						purchaseIndex += "<span class=\"index" + (n + 1)
								+ "\">"
								+ parseFloat(item.purchaseIndex).toFixed(2)
								+ "</span><br/>";
						stableIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.stableIndex).toFixed(2)
								+ "</span><br/>";
						trendIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.trendIndex).toFixed(2)
								+ "</span><br/>";
						riskIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.riskIndex).toFixed(2)
								+ "</span><br/>";
					} else {
						propertyIndex += "<span class=\"index" + (n + 1)
								+ "\">"
								+ parseFloat(item.propertyIndex).toFixed(2)
								+ "</span>";
						purchaseIndex += "<span class=\"index" + (n + 1)
								+ "\">"
								+ parseFloat(item.purchaseIndex).toFixed(2)
								+ "</span>";
						stableIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.stableIndex).toFixed(2)
								+ "</span>";
						trendIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.trendIndex).toFixed(2)
								+ "</span>";
						riskIndex += "<span class=\"index" + (n + 1) + "\">"
								+ parseFloat(item.riskIndex).toFixed(2)
								+ "</span>";
					}

				}

			});

	index_html += "<tr>\r\n";
	index_html += "<td class=\"sum_data\">" + propertyIndex + "</td>\r\n";
	index_html += "<td class=\"sum_data\">" + purchaseIndex + "</td>\r\n";
	index_html += "<td class=\"sum_data\">" + stableIndex + "</td>\r\n";
	index_html += "<td class=\"sum_data\">" + trendIndex + "</td>\r\n";
	index_html += "<td class=\"sum_data no_border\">" + riskIndex + "</td>\r\n";
	index_html += "</tr>\r\n";
	index_html += "</tbody>\r\n";
	index_html += "</table>\r\n";
	index_html += "</div>\r\n";
	$("#div_title").html(title_html);
	$("#div_index").html(index_html);

}
// 价格趋势图
function ShowPriceChart() {
	var options = {
		chart : {
			renderTo : 'container_price',
		},
		title : {
			text : '',
			x : -20
		// center
		},
		xAxis : {
			categories : []
		},
		yAxis : {
			title : {
				text : '价格'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			crosshairs : true,
			shared : true
		},
		plotOptions : {
			spline : {
				marker : {
					radius : 4,
					lineColor : '#666666',
					lineWidth : 1
				}
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : []
	};
	var Stands = dwr.util.getValue("Stands");
	var month = "2014";
	ChartData.selectMonthStatChart(Stands, month, function(data) {
		// alert(data);
		var obj = $.parseJSON(data);
		var ids = new Array();
		ids = Stands.split(',');

		$.each(ids, function(n, id) {

			var series = {
				data : []
			};
			$.each(obj, function(i, item) {
				if (id == item.standardID) {
					options.xAxis.categories.push(item.month + "月");
					// series.name = item.standardID;
					series.data.push(parseFloat(item.avgPrice));
				}
			});
			$.each(goods, function(i, item) {
				if (id == item.standardID) {
					var title = item.title;
					if (item.title.length > 12) {
						title = item.title.substring(0, 9) + "...";
					}
					series.name = title;
				}
			});
			options.series.push(series);

		});
		var chart = new Highcharts.Chart(options);
	});

}
// 口碑图
function ShowFavorableChart() {
	var options = {
		chart : {
			renderTo : 'container_favorable',
			type : 'column'
		},
		title : {
			text : '口碑报表',
			x : -20
		// center
		},
		xAxis : {
			categories : []
		},
		yAxis : {
			title : {
				text : '数量'
			},
			min : 0
		},
		tooltip : {
			shared : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : []
	};
	var Stands = dwr.util.getValue("Stands");
	ChartData.selectFavorableChart(Stands, function(data) {
		// alert(data);
		var obj = $.parseJSON(data);
		var ids = new Array();
		ids = Stands.split(',');
		options.xAxis.categories.push("好评");
		options.xAxis.categories.push("中评");
		options.xAxis.categories.push("差评");
		$.each(ids, function(n, id) {

			var series = {
				data : []
			};
			$.each(obj, function(i, item) {
				if (id == item.standardID) {
					series.data.push(parseInt(item.favorableNum));
				}
			});
			$.each(obj, function(i, item) {
				if (id == item.standardID) {
					series.data.push(parseInt(item.averageNum));
				}
			});
			$.each(obj, function(i, item) {
				if (id == item.standardID) {
					series.data.push(parseInt(item.badNum));
				}
			});
			$.each(goods, function(i, item) {
				if (id == item.standardID) {
					var title = item.title;
					if (item.title.length > 12) {
						title = item.title.substring(0, 9) + "...";
					}
					series.name = title;
				}
			});
			options.series.push(series);

		});
		var chart = new Highcharts.Chart(options);
	});

}

// 生命週期趋势图
function ShowLifeCycleChart() {
	var options = {
		chart : {
			renderTo : 'container_lifeCycle',
		},
		title : {
			text : '生命周期趋势报表',
			x : -20
		// center
		},
		xAxis : {
			categories : []
		},
		yAxis : {
			title : {
				text : '趋势值'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			crosshairs : true,
			shared : true
		},
		plotOptions : {
			spline : {
				marker : {
					radius : 4,
					lineColor : '#666666',
					lineWidth : 1
				}
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		series : []
	};
	var Stands = dwr.util.getValue("Stands");
	var month = "2014";
	ChartData.selectLifeCycleChart(Stands, month, function(data) {
		var obj = $.parseJSON(data);
		var ids = new Array();
		ids = Stands.split(',');

		$.each(ids, function(n, id) {

			var series = {
				data : []
			};
			$.each(obj, function(i, item) {
				if (id == item.standardID) {
					options.xAxis.categories.push(item.month + "月");
					// series.name = item.standardID;
					series.data.push(parseFloat(item.lifeCycleValue));
				}
			});
			$.each(goods, function(i, item) {
				if (id == item.standardID) {
					var title = item.title;
					if (item.title.length > 12) {
						title = item.title.substring(0, 9) + "...";
					}
					series.name = title;
				}
			});
			options.series.push(series);

		});
		var chart = new Highcharts.Chart(options);
	});
	/*
	 * $('#container_man').highcharts({ chart: { type: 'pie', options3d: {
	 * enabled: true, alpha: 45 } }, title: { text: '性别图' }, subtitle: { text:
	 * '男女比例' }, plotOptions: { pie: { innerSize: 100, depth: 45 } }, series: [{
	 * name: 'Delivered amount', data: [ ['男', 80], ['女', 60] ] }] });
	 * $('#container_2').highcharts({
	 * 
	 * chart: { polar: true, type: 'line' },
	 * 
	 * title: { text: 'Budget vs spending', x: -80 },
	 * 
	 * pane: { size: '80%' },
	 * 
	 * xAxis: { categories: ['Sales', 'Marketing', 'Development', 'Customer
	 * Support', 'Information Technology', 'Administration'], tickmarkPlacement:
	 * 'on', lineWidth: 0 },
	 * 
	 * yAxis: { gridLineInterpolation: 'polygon', lineWidth: 0, min: 0 },
	 * 
	 * tooltip: { shared: true },
	 * 
	 * legend: { align: 'right', verticalAlign: 'top', y: 70, layout: 'vertical' },
	 * 
	 * series: [{ name: 'Allocated Budget', data: [43000, 19000, 60000, 35000,
	 * 17000, 10000], pointPlacement: 'on' }, { name: 'Actual Spending', data:
	 * [50000, 39000, 42000, 31000, 26000, 14000], pointPlacement: 'on' }]
	 * 
	 * });
	 */
}