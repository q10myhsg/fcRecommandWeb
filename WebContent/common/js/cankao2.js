var PloyVals = "";
var cartnum = 0;
var totalprice = 0;
var ordernum = 0;// 关注单数量
var totalorderprice = 0;// 关注单总价格
var issyn = false;
var goods;
Highcharts.setOptions({
	colors : [ '#058DC7', '#ED561B', '#50B432', '#DDDF00', '#24CBE5',
			'#64E572', '#FF9655', '#FFF263', '#6AF9C4' ]
});
function ClickSearch(a) {
	var txtValue = document.getElementById("txtSearch").value;
	if (txtValue != null && txtValue != "") {
		a.href = "./search?key=" + txtValue;
		// var key = encodeURI(txtValue);
	}
}
$(document).ready(function() {
	InitPloyVals();
	MenuHD();
	J_CatDiv();
	LoadAttentionHtml();// 加载关注单
	LoadPurchaseHtml();// 加载采购单
	InfoTabClick();
	TabMapClick();
	ShowInfoHtml();
	ShowAnalyseHtml();

});

function ShowInfoHtml() {
	var id = dwr.util.getValue("id");
	var info = "";
	StandardControl
			.selectStandardIndexByID(
					id,
					PloyVals,
					{
						callback : function(data) {
							if (data != '') {
								var obj = $.parseJSON(data);
								if (obj != null) {
									goods = obj;
									var title = "";
									$
											.each(
													obj,
													function(i, item) {
														title = item.title;
														var index = parseFloat(item.uniIndex);
														$("#infotitle").html(
																item.title);
														$("#BrandName").html(item.brandName);
														$("#ModelName").html(item.modelName);
														$("#showbjprice").html(
																item.price);
														$("#uni2uniIndex")
																.html(FirstIndexStatusToName(item.indexStatus)+"("+index.toFixed(2)+")");
														document
																.getElementById("infoImg").src = item.image;
														$("#uniIndex")
																.html(FirstIndexStatusToName(item.indexStatus)+"("+index.toFixed(2)+")");
														$("#propertyIndex")
																.html(PropertyStatusToName(item.propertyStatus)+"<br/>("+parseFloat(item.propertyIndex).toFixed(2)+")");
														$("#purchaseIndex")
														.html(PurchaseStatusToName(item.purchaseStatus)+"<br/>("+parseFloat(item.purchaseIndex).toFixed(2)+")");
														$("#trendIndex")
														.html(TrendStatusToName(item.trendStatus)+"<br/>("+parseFloat(item.trendIndex).toFixed(2)+")");
														$("#stableIndex")
														.html(StableStatusToName(item.stableStatus)+"<br/>("+parseFloat(item.stableIndex).toFixed(2)+")");
														$("#riskIndex")
														.html(RiskStatusToName(item.riskStatus)+"<br/>("+parseFloat(item.riskIndex).toFixed(2)+")");
														$("#span_price").html(PriceToName(item.priceTrend));
														if (item.categoryInfo != null
																&& item.categoryInfo != "") {
															var html = "<p class=\"tips\">";
															var categorys = new Array();
															categorys = item.categoryInfo.split('|');
															if (categorys != null
																	&& categorys.length > 0) {

																$.each(
																				categorys,
																				function(i, cate) {
																					var cs = new Array();
																					cs = cate.split(':');
																					html += "<a href=\"./search?code="
																							+ cs[1]
																							+ "\">"
																							+ cs[0]
																							+ "</a>"
																							+ " >> ";
																				});
															}
															if (title.length > 30) {
																title = title.substring(0, 27)
																		+ "...";
															}
															html += title + "</p>";
															$("#result_title").html(html);
														}
													});
									//obj.categoryInfo = "数码产品:1000|手机通讯:10001000|手机:100010001000";
									

								}

							}
						},
						async : false
					});
}
function ShowDescHtml() {
	var id = dwr.util.getValue("id");
	InfoControl
			.selectProductDetail(
					id,
					{
						callback : function(data) {
							if (data != null && data != '' && data != ' ') {
								// document.getElementById("p_desc").innerHTML=data;
								$("#p_desc").html(data);
							} else {
								$("#p_desc")
										.html(
												"<div style='text-align:left;'><font color='red'><b>暂无描述</b></font></div>");
							}
						}
					});
}

function ShowAnalyseHtml() {
	ShowPriceChart();// 价格趋势图
	ShowFavorableChart();// 口碑图
	ShowLifeCycleChart();// 生命周期图
	ShowMapCharts();// 地图
	ShowCrowdProportionChart();
}

function ShowProviderHtml() {
	var id = dwr.util.getValue("id");
	StandardProductControl
			.selectStandardProductByID(
					id,
					{
						callback : function(data) {
							if (data != '') {
								var obj = $.parseJSON(data);
								if (obj == null) {
								}
								if (obj != null && obj.length > 0) {
									var html = "";
									$
											.each(
													obj,
													function(i, bean) {
														var evenItem = " first-item";
														if ((i + 1) % 2 == 0) {
															evenItem = " double_item";
														}
														html += "<div id=\"list"
																+ bean.shopsID
																+ "\">\r\n";
														html += "<div class=\"J_ItemBody item-body clearfix item-normal "
																+ evenItem
																+ "\">\r\n";
														html += "<ul class=\"item-content clearfix\">\r\n";
														html += "<li class=\"flowtd flowtd-item\">\r\n";
														html += "<div class=\"td-inner\">\r\n";
														html += "<div class=\"item-info\">\r\n";
														html += "<a href=\""
																+ bean.shopsUrl
																+ "\" target=\"_blank\" class=\"item-title J_MakePoint\">"
																+ bean.shopsName
																+ "</a>\r\n";
														html += "</div></div></li>\r\n";
														html += "<li class=\"flowtd flowtd-price\"><div class=\"td-inner\">"
																+ bean.location
																+ "</div></li>\r\n";
														html += "<li class=\"flowtd flowtd-amount\"><div class=\"td-inner\">"
																+ bean.telephone
																+ "</div></li>\r\n";
														html += "<li class=\"flowtd flowtd-op\">"
																+ bean.score/10
																+ "</li>\r\n";

														html += "</ul>\r\n";
														html += "</div>\r\n";
														html += "</div>\r\n";
													});
									$("#flowList").html(html);

								} else {
									$("#J_FlowList")
											.html(
													"<div style='text-align:left;'><font color='red'><b>暂无</b></font></div>");
								}

							}
						},
						async : false
					});
}
// 价格趋势图
function ShowPriceChart() {
	var options = {
		chart : {
			renderTo : 'container_price',
			zoomType : 'xy'
		},
		title : {
			text : '',
			x : -20
		// center
		},
		xAxis : {
			categories : []
		},
		yAxis : [ {
			title : {
				text : '价格'
			},
			plotLines : [ {
				value : 0,
				width : 1
			} ]
		}, {
			title : {
				text : '销量'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				align : 'right'
			} ],
			opposite : true
		} ],
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
	var id = dwr.util.getValue("id");
	var month = "2014";
	ChartData.selectMonthStatChart(id, month, function(data) {

		var obj = $.parseJSON(data);
		var series = [ {
			type : 'column',
			name : '销量',
			yAxis : 1,
			data : []
		}, {
			data : [],
			name : '价格',
			type : 'spline'

		} ];
		$.each(obj, function(i, item) {
			if (id == item.standardID) {
				options.xAxis.categories.push(item.month + "月");
				series[0].data.push(parseFloat(item.sumSales));
				series[1].data.push(parseFloat(item.avgPrice));
			}
		});
		options.series.push(series[0]);
		options.series.push(series[1]);

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
	var Stands = dwr.util.getValue("id");
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
			series.name = "口碑";
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
	var Stands = dwr.util.getValue("id");
	var month = "2014";
	var one=0;
	var two=0;
	ChartData.selectLifeCycleChart(Stands, month, { callback :function(data) {
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
					if(i==obj.length-2){
						one=parseInt(item.lifeCycleState);
					}
					if(i==obj.length-1){
						two=parseInt(item.lifeCycleState);
					}
				}
			});
			series.name = "趋势";
			options.series.push(series);

		});
		var chart = new Highcharts.Chart(options);
	},
	async : false
	});
	var html=LifeCycleToName(one,two);
	$("#span_lifeCycle").html(html);
}
function ShowCrowdProportionChart() {

	var id = dwr.util.getValue("id");
	ChartData
			.selectCrowdChartByStandardID(
					id,
					function(data) {
						if (data != null && data != "") {
							var obj = $.parseJSON(data);
							if (obj != null && obj.length > 0) {
								var options = {
									chart : {
										renderTo : 'container_man',
										type : 'pie',
										options3d : {
											enabled : true,
											alpha : 45
										}
									},
									title : {
										text : '性别报表',
										x : -20
									// center
									},
									tooltip : {
										shared : true,
										pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
									},
									plotOptions : {
										pie : {
											innerSize : 100,
											depth : 45

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
								var optionsAge = {
									chart : {
										renderTo : 'container_age',
										polar : true,
										type : 'line'
									},
									title : {
										text : '年龄报表',
										x : -20
									// center
									},
									xAxis : {
										categories : [ '50-59', '40-49',
												'35-39', '30-34', '25-29',
												'18-24' ],
										tickmarkPlacement : 'on',
										lineWidth : 0
									},
									yAxis : {
										gridLineInterpolation : 'polygon',
										lineWidth : 0,
										min : 0
									},
									tooltip : {
										shared : true
									},
									legend : {
										layout : 'vertical',
										align : 'right',
										verticalAlign : 'middle',
										borderWidth : 0
									},
									series : []
								};
								var series = {
									data : [ [], [] ]
								};
								var seriesAge = {
									data : []
								};
								$
										.each(
												obj,
												function(i, item) {
													if (id == item.standardID) {
														// series.name =
														// item.standardID;
														series.data[0]
																.push("男");
														series.data[0]
																.push(parseFloat(item.man));
														series.data[1]
																.push("女");
														series.data[1]
																.push(parseFloat(item.women));
														seriesAge.data
																.push(parseFloat(item.fifty));
														seriesAge.data
																.push(parseFloat(item.forty));
														seriesAge.data
																.push(parseFloat(item.thirtyFive));
														seriesAge.data
																.push(parseFloat(item.thirty));
														seriesAge.data
																.push(parseFloat(item.twentyFive));
														seriesAge.data
																.push(parseFloat(item.eighteen));
													}
												});
								series.name = "数量";
								seriesAge.name = "人群";
								options.series.push(series);
								optionsAge.series.push(seriesAge);
								var chart = new Highcharts.Chart(options);
								var chartAge = new Highcharts.Chart(optionsAge);
							} else {
								$("#container_man")
										.html(
												"<div style='text-align:center;'><font color='red'><b>暂无数据</b></font></div>");
								$("#container_age")
										.html(
												"<div style='text-align:center;'><font color='red'><b>暂无数据</b></font></div>");
							}

						}
					});
}
function closeBox(){
	$("#provinceBox").css("display", "none");
}
function selectPloyOrder(){
	$("#provinceBox").css("display", "none");
	window.open("./order");
}
function AddShoppingCarBeforeAction() {
	var userId = $("#userID").val();
	var id = $("#id").val();
	var count = $("#BuyNumber").val();
	var userPurchase = new PurchaseProduct();
	userPurchase.orderID = 1;
	userPurchase.standNo = "LJYDD0005";
	userPurchase.userID = userId;
	userPurchase.standardID = id;
	userPurchase.amount = count;
	UserPurchase.insertPurchaseProduct(userPurchase, {
		callback : function(data) {
			if (data != null || data != '') {
				$("#provinceBox").css("display", "block");
				$('#provinceBox').delay(5000).hide(0);
				LoadPurchaseHtml();
			}
		}
	});
}