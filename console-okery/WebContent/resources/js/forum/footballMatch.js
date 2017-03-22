var editMatchDialog;	//编辑赛事推荐
var addMatchDialog;		//添加比赛推荐
var jsoupMatchDialog;	//抓取比赛推荐
var matchScoreDialog;	//可视化进球
var matchKeliDialog;	//可视化进球
function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	forumConsole.init('searchTable',webContextPath+'/queryFootballMatchList.do','json',10);
	editMatchDialog = forumConsole.genDialog(webContextPath,'编辑足球推荐','800','auto','保存','关闭','editMatchForm','edit_inter_dialog',editMatch,editMatchDialogCancel);
	addMatchDialog =  forumConsole.genDialog(webContextPath,'添加足球推荐','800','auto','保存','关闭','addMatchForm','add_inter_dialog',addMatch,addMatchDialogCancel);
	jsoupMatchDialog =  forumConsole.genDialog(webContextPath,'抓取足球推荐','450','auto','抓取','关闭','jsoupMatchForm','jsoup_match_dialog',jsoupMatch,jsoupMatchDialogCancel);
	matchScoreDialog =  forumConsole.genDetailDialog(webContextPath,'可视化进球','1000','auto','关闭','matchScoreForm','match_score_dialog',matchScoreDialogCancel);
	matchKeliDialog =  forumConsole.genDetailDialog(webContextPath,'凯利指数分布图','1000','auto','关闭','matchKeliForm','match_Keli_dialog',matchKeliDialogCancel);
}

//取消可视化进球
function matchScoreDialogCancel(){
	$("#matchScoreForm").remove();
	matchScoreDialog.close();
}

//取消凯利走势图
function matchKeliDialogCancel(){
	$("#matchKeliForm").remove();
	matchKeliDialog.close();
}

//查询可视化进球
function queryMatchScoreTimeList(){
	var sendData = {};
	var start_time = $("#start_time").val();
	if(start_time){
		sendData.start_time = start_time;
	}
	var end_time = $("#end_time").val();
	if(end_time){
		sendData.end_time = end_time;
	}
	forumConsole.ajaxCall('POST', contextPath + "/queryMatchScoreTimeList.do", false, sendData, 'json', function(pie_elements){
		//element_id, title, data, plot_type
		var element_id = "pie_div";
		var title = "可视化进球";
		var data = pie_elements.data;
		var plot_type = "pie";
		var myChart = echarts.init(document.getElementById(element_id));
		var win = [];
		var win_sum = [];
		var draw = [];
		var draw_sum = [];
		var lost = [];
		var lost_sum = [];
		if(data != null && data.length > 0){
			for(var i = 0;i<data.length;i++){
				if(null != data[i].win_rate && data[i].win_rate != null && typeof(data[i].win_rate) != 'undefined'){
					if(null != data[i].win_keli && data[i].win_keli != null && typeof(data[i].win_keli) != 'undefined'){
						win[i].name = data[i].win_rate;
						win[i].value = data[i].win_keli;
						win_sum.push(win[i]);
					}
				}
				if(null != data[i].draw_rate && data[i].draw_rate != null && typeof(data[i].draw_rate) != 'undefined'){
					if(null != data[i].draw_keli && data[i].draw_keli != null && typeof(data[i].draw_keli) != 'undefined'){
						draw[i].name = data[i].draw_rate;
						draw[i].value = data[i].draw_keli;
						draw_sum.push(draw[i]);
					}
				}
				if(null != data[i].lost_rate && data[i].lost_rate != null && typeof(data[i].lost_rate) != 'undefined'){
					if(null != data[i].lost_keli && data[i].lost_keli != null && typeof(data[i].lost_keli) != 'undefined'){
						lost[i].name = data[i].lost_rate;
						lost[i].value = data[i].lost_keli;
						lost_sum.push(lost[i]);
					}
				}
			}
		}
		console.log(win_sum);
		console.log(draw_sum);
		console.log(lost_sum);
		if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
			plot_type = 'pie';
		}else if(plot_type != 'pie'){
			alert("Please confirm that plot type you choose is pie.");
			return;
		}
		var option = {
			title: {
				text: "\n"+title+"\n",
				x:'center'
			},
			tooltip: {
				trigger: 'item',
				formatter: "{a}<br/>{b}球: {c}次 (占{d}%)"
			},
			legend: {
				data:names
			},
			toolbox: {
				show : true,
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: false},
					magicType : {
						show: true,
						type: ['pie', 'funnel'],
						option: {
							funnel: {
								x: '25%',
								width: '50%',
								funnelAlign: 'center',
								max: 1548
							}
						}
					},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			series: [
				{
					name:'数据占比',
					type:'pie',
					radius: ['50%', '70%'],
					avoidLabelOverlap: false,
					label: {
						normal: {
							show: false,
							position: 'center'
						},
						emphasis: {
							show: true,
							formatter: '{b}球: {c}次',
							textStyle: {
								fontSize: '20',
								fontWeight: 'bold'
							}
						}
					},
					labelLine: {
						normal: {
							show: false
						}
					},
					data:data
				}
			]
		};
		myChart.setOption(option);
	});
}

//查询凯利分布走势图
function queryMatchKeliList(){
	var sendData = {};
	var match_id = $("#match_id").val();
	if(match_id){
		sendData.match_id = match_id;
	}
	forumConsole.ajaxCall('POST', contextPath + "/queryMatchKeliList.do", false, sendData, 'json', function(pie_elements){
		//element_id, title, data, plot_type
		var element_id = "pie_div";
		var title = "凯利分布走势图";
		var data = pie_elements.data;
		var plot_type = "pie";
		var myChart = echarts.init(document.getElementById(element_id));
		var names = [];
		if(data != null && data.length > 0){
			for(var i = 0;i<data.length;i++){
				if(null != data[i].sum_score && data[i].sum_score >= 0 && typeof(data[i].sum_score) != 'undefined'){
					names.push(data[i].sum_score);
					if(data[i].sum_score == null || data[i].sum_score == ""){
						data[i].name = 0;
					}else{
						data[i].name = data[i].sum_score;
					}
					data[i].name = data[i].sum_score;
					data[i].value = data[i].score_count;
				}
			}
		}
		var option = {
			title : {
				text: title,
				subtext: match_id
			},
			grid: {
				left: '3%',
				right: '7%',
				bottom: '3%',
				containLabel: true
			},
			tooltip : {
				trigger: 'axis',
				showDelay : 0,
				formatter : function (params) {
					if (params.value.length > 1) {
						return params.seriesName + ' :<br/>'
							+ params.value[0] + '% '
							+ params.value[1] + '% ';
					}
					else {
						return params.seriesName + ' :<br/>'
							+ params.name + ' : '
							+ params.value + '% ';
					}
				},
				axisPointer:{
					show: true,
					type : 'cross',
					lineStyle: {
						type : 'dashed',
						width : 1
					}
				}
			},
			toolbox: {
				feature: {
					dataZoom: {},
					brush: {
						type: ['rect', 'polygon', 'clear']
					}
				}
			},
			brush: {
			},
			legend: {
				data: ['胜','平','负'],
				left: 'center'
			},
			xAxis : [
				{
					type : 'value',
					scale:true,
					axisLabel : {
						formatter: '{value} '
					},
					splitLine: {
						show: false
					}
				}
			],
			yAxis : [
				{
					type : 'value',
					scale:true,
					axisLabel : {
						formatter: '{value} '
					},
					splitLine: {
						show: false
					}
				}
			],
			series : [
				{
					name:'胜',
					type:'scatter',
					data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
						[170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
						[172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
						[147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
						[159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
						[174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
						[154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
						[162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
						[168.9, 62.3], [163.8, 58.5], [167.6, 54.5], [160.0, 50.2], [161.3, 60.3],
						[167.6, 58.3], [165.1, 56.2], [160.0, 50.2], [170.0, 72.9], [157.5, 59.8],
						[167.6, 61.0], [160.7, 69.1], [163.2, 55.9], [152.4, 46.5], [157.5, 54.3],
						[168.3, 54.8], [180.3, 60.7], [165.5, 60.0], [165.0, 62.0], [164.5, 60.3],
						[156.0, 52.7], [160.0, 74.3], [163.0, 62.0], [165.7, 73.1], [161.0, 80.0],
						[162.0, 54.7], [166.0, 53.2], [174.0, 75.7], [172.7, 61.1], [167.6, 55.7],
						[151.1, 48.7], [164.5, 52.3], [163.5, 50.0], [152.0, 59.3], [169.0, 62.5],
						[164.0, 55.7], [161.2, 54.8], [155.0, 45.9], [170.0, 70.6], [176.2, 67.2],
						[170.0, 69.4], [162.5, 58.2], [170.3, 64.8], [164.1, 71.6], [169.5, 52.8],
						[163.2, 59.8], [154.5, 49.0], [159.8, 50.0], [173.2, 69.2], [170.0, 55.9],
						[161.4, 63.4], [169.0, 58.2], [166.2, 58.6], [159.4, 45.7], [162.5, 52.2],
						[159.0, 48.6], [162.8, 57.8], [159.0, 55.6], [179.8, 66.8], [162.9, 59.4],
						[161.0, 53.6], [151.1, 73.2], [168.2, 53.4], [168.9, 69.0], [173.2, 58.4],
						[171.8, 56.2], [178.0, 70.6], [164.3, 59.8], [163.0, 72.0], [168.5, 65.2],
						[166.8, 56.6], [172.7, 105.2], [163.5, 51.8], [169.4, 63.4], [167.8, 59.0],
						[159.5, 47.6], [167.6, 63.0], [161.2, 55.2], [160.0, 45.0], [163.2, 54.0],
						[162.2, 50.2], [161.3, 60.2], [149.5, 44.8], [157.5, 58.8], [163.2, 56.4],
						[172.7, 62.0], [155.0, 49.2], [156.5, 67.2], [164.0, 53.8], [160.9, 54.4],
						[162.8, 58.0], [167.0, 59.8], [160.0, 54.8], [160.0, 43.2], [168.9, 60.5],
						[158.2, 46.4], [156.0, 64.4], [160.0, 48.8], [167.1, 62.2], [158.0, 55.5],
						[167.6, 57.8], [156.0, 54.6], [162.1, 59.2], [173.4, 52.7], [159.8, 53.2],
						[170.5, 64.5], [159.2, 51.8], [157.5, 56.0], [161.3, 63.6], [162.6, 63.2],
						[160.0, 59.5], [168.9, 56.8], [165.1, 64.1], [162.6, 50.0], [165.1, 72.3],
						[166.4, 55.0], [160.0, 55.9], [152.4, 60.4], [170.2, 69.1], [162.6, 84.5],
						[170.2, 55.9], [158.8, 55.5], [172.7, 69.5], [167.6, 76.4], [162.6, 61.4],
						[167.6, 65.9], [156.2, 58.6], [175.2, 66.8], [172.1, 56.6], [162.6, 58.6],
						[160.0, 55.9], [165.1, 59.1], [182.9, 81.8], [166.4, 70.7], [165.1, 56.8],
						[177.8, 60.0], [165.1, 58.2], [175.3, 72.7], [154.9, 54.1], [158.8, 49.1],
						[172.7, 75.9], [168.9, 55.0], [161.3, 57.3], [167.6, 55.0], [165.1, 65.5],
						[175.3, 65.5], [157.5, 48.6], [163.8, 58.6], [167.6, 63.6], [165.1, 55.2],
						[165.1, 62.7], [168.9, 56.6], [162.6, 53.9], [164.5, 63.2], [176.5, 73.6],
						[168.9, 62.0], [175.3, 63.6], [159.4, 53.2], [160.0, 53.4], [170.2, 55.0],
						[162.6, 70.5], [167.6, 54.5], [162.6, 54.5], [160.7, 55.9], [160.0, 59.0],
						[157.5, 63.6], [162.6, 54.5], [152.4, 47.3], [170.2, 67.7], [165.1, 80.9],
						[172.7, 70.5], [165.1, 60.9], [170.2, 63.6], [170.2, 54.5], [170.2, 59.1],
						[161.3, 70.5], [167.6, 52.7], [167.6, 62.7], [165.1, 86.3], [162.6, 66.4],
						[152.4, 67.3], [168.9, 63.0], [170.2, 73.6], [175.2, 62.3], [175.2, 57.7],
						[160.0, 55.4], [165.1, 104.1], [174.0, 55.5], [170.2, 77.3], [160.0, 80.5],
						[167.6, 64.5], [167.6, 72.3], [167.6, 61.4], [154.9, 58.2], [162.6, 81.8],
						[175.3, 63.6], [171.4, 53.4], [157.5, 54.5], [165.1, 53.6], [160.0, 60.0],
						[174.0, 73.6], [162.6, 61.4], [174.0, 55.5], [162.6, 63.6], [161.3, 60.9],
						[156.2, 60.0], [149.9, 46.8], [169.5, 57.3], [160.0, 64.1], [175.3, 63.6],
						[169.5, 67.3], [160.0, 75.5], [172.7, 68.2], [162.6, 61.4], [157.5, 76.8],
						[176.5, 71.8], [164.4, 55.5], [160.7, 48.6], [174.0, 66.4], [163.8, 67.3]
					],
					markArea: {
						silent: true,
						itemStyle: {
							normal: {
								color: 'transparent',
								borderWidth: 1,
								borderType: 'dashed'
							}
						},
						data: [[{
							name: '胜分布区间',
							xAxis: 'min',
							yAxis: 'min'
						}, {
							xAxis: 'max',
							yAxis: 'max'
						}]]
					},
					markPoint : {
						data : [
							{type : 'max', name: '最大值'},
							{type : 'min', name: '最小值'}
						]
					},
					markLine : {
						lineStyle: {
							normal: {
								type: 'solid'
							}
						},
						data : [
							{type : 'average', name: '平均值'},
							{ xAxis: 160 }
						]
					}
				},
				{
					name:'平',
					type:'scatter',
					data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
						[181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
						[180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
						[184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
						[176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
						[178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
						[183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
						[170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
						[186.7, 87.8], [171.4, 84.7], [172.7, 73.4], [175.3, 72.1], [180.3, 82.6],
						[182.9, 88.7], [188.0, 84.1], [177.2, 94.1], [172.1, 74.9], [167.0, 59.1],
						[169.5, 75.6], [174.0, 86.2], [172.7, 75.3], [182.2, 87.1], [164.1, 55.2],
						[163.0, 57.0], [171.5, 61.4], [184.2, 76.8], [174.0, 86.8], [174.0, 72.2],
						[177.0, 71.6], [186.0, 84.8], [167.0, 68.2], [171.8, 66.1], [182.0, 72.0],
						[167.0, 64.6], [177.8, 74.8], [164.5, 70.0], [192.0, 101.6], [175.5, 63.2],
						[171.2, 79.1], [181.6, 78.9], [167.4, 67.7], [181.1, 66.0], [177.0, 68.2],
						[174.5, 63.9], [177.5, 72.0], [170.5, 56.8], [182.4, 74.5], [197.1, 90.9],
						[180.1, 93.0], [175.5, 80.9], [180.6, 72.7], [184.4, 68.0], [175.5, 70.9],
						[180.6, 72.5], [177.0, 72.5], [177.1, 83.4], [181.6, 75.5], [176.5, 73.0],
						[175.0, 70.2], [174.0, 73.4], [165.1, 70.5], [177.0, 68.9], [192.0, 102.3],
						[176.5, 68.4], [169.4, 65.9], [182.1, 75.7], [179.8, 84.5], [175.3, 87.7],
						[184.9, 86.4], [177.3, 73.2], [167.4, 53.9], [178.1, 72.0], [168.9, 55.5],
						[157.2, 58.4], [180.3, 83.2], [170.2, 72.7], [177.8, 64.1], [172.7, 72.3],
						[165.1, 65.0], [186.7, 86.4], [165.1, 65.0], [174.0, 88.6], [175.3, 84.1],
						[185.4, 66.8], [177.8, 75.5], [180.3, 93.2], [180.3, 82.7], [177.8, 58.0],
						[177.8, 79.5], [177.8, 78.6], [177.8, 71.8], [177.8, 116.4], [163.8, 72.2],
						[188.0, 83.6], [198.1, 85.5], [175.3, 90.9], [166.4, 85.9], [190.5, 89.1],
						[166.4, 75.0], [177.8, 77.7], [179.7, 86.4], [172.7, 90.9], [190.5, 73.6],
						[185.4, 76.4], [168.9, 69.1], [167.6, 84.5], [175.3, 64.5], [170.2, 69.1],
						[190.5, 108.6], [177.8, 86.4], [190.5, 80.9], [177.8, 87.7], [184.2, 94.5],
						[176.5, 80.2], [177.8, 72.0], [180.3, 71.4], [171.4, 72.7], [172.7, 84.1],
						[172.7, 76.8], [177.8, 63.6], [177.8, 80.9], [182.9, 80.9], [170.2, 85.5],
						[167.6, 68.6], [175.3, 67.7], [165.1, 66.4], [185.4, 102.3], [181.6, 70.5],
						[172.7, 95.9], [190.5, 84.1], [179.1, 87.3], [175.3, 71.8], [170.2, 65.9],
						[193.0, 95.9], [171.4, 91.4], [177.8, 81.8], [177.8, 96.8], [167.6, 69.1],
						[167.6, 82.7], [180.3, 75.5], [182.9, 79.5], [176.5, 73.6], [186.7, 91.8],
						[188.0, 84.1], [188.0, 85.9], [177.8, 81.8], [174.0, 82.5], [177.8, 80.5],
						[171.4, 70.0], [185.4, 81.8], [185.4, 84.1], [188.0, 90.5], [188.0, 91.4],
						[182.9, 89.1], [176.5, 85.0], [175.3, 69.1], [175.3, 73.6], [188.0, 80.5],
						[188.0, 82.7], [175.3, 86.4], [170.5, 67.7], [179.1, 92.7], [177.8, 93.6],
						[175.3, 70.9], [182.9, 75.0], [170.8, 93.2], [188.0, 93.2], [180.3, 77.7],
						[177.8, 61.4], [185.4, 94.1], [168.9, 75.0], [185.4, 83.6], [180.3, 85.5],
						[174.0, 73.9], [167.6, 66.8], [182.9, 87.3], [160.0, 72.3], [180.3, 88.6],
						[167.6, 75.5], [186.7, 101.4], [175.3, 91.1], [175.3, 67.3], [175.9, 77.7],
						[175.3, 81.8], [179.1, 75.5], [181.6, 84.5], [177.8, 76.6], [182.9, 85.0],
						[177.8, 102.5], [184.2, 77.3], [179.1, 71.8], [176.5, 87.9], [188.0, 94.3],
						[174.0, 70.9], [167.6, 64.5], [170.2, 77.3], [167.6, 72.3], [188.0, 87.3],
						[174.0, 80.0], [176.5, 82.3], [180.3, 73.6], [167.6, 74.1], [188.0, 85.9],
						[180.3, 73.2], [167.6, 76.3], [183.0, 65.9], [183.0, 90.9], [179.1, 89.1],
						[170.2, 62.3], [177.8, 82.7], [179.1, 79.1], [190.5, 98.2], [177.8, 84.1],
						[180.3, 83.2], [180.3, 83.2]
					],
					markArea: {
						silent: true,
						itemStyle: {
							normal: {
								color: 'transparent',
								borderWidth: 1,
								borderType: 'dashed'
							}
						},
						data: [[{
							name: '平分布区间',
							xAxis: 'min',
							yAxis: 'min'
						}, {
							xAxis: 'max',
							yAxis: 'max'
						}]]
					},
					markPoint : {
						data : [
							{type : 'max', name: '最大值'},
							{type : 'min', name: '最小值'}
						]
					},
					markLine : {
						lineStyle: {
							normal: {
								type: 'solid'
							}
						},
						data : [
							{type : 'average', name: '平均值'},
							{ xAxis: 170 }
						]
					}
				},
				{
					name:'负',
					type:'scatter',
					data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
						[181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
						[180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
						[184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
						[176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
						[178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
						[183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
						[170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
						[186.7, 87.8], [171.4, 84.7], [172.7, 73.4], [175.3, 72.1], [180.3, 82.6],
						[182.9, 88.7], [188.0, 84.1], [177.2, 94.1], [172.1, 74.9], [167.0, 59.1],
						[169.5, 75.6], [174.0, 86.2], [172.7, 75.3], [182.2, 87.1], [164.1, 55.2],
						[163.0, 57.0], [171.5, 61.4], [184.2, 76.8], [174.0, 86.8], [174.0, 72.2],
						[177.0, 71.6], [186.0, 84.8], [167.0, 68.2], [171.8, 66.1], [182.0, 72.0],
						[167.0, 64.6], [177.8, 74.8], [164.5, 70.0], [192.0, 101.6], [175.5, 63.2],
						[171.2, 79.1], [181.6, 78.9], [167.4, 67.7], [181.1, 66.0], [177.0, 68.2],
						[174.5, 63.9], [177.5, 72.0], [170.5, 56.8], [182.4, 74.5], [197.1, 90.9],
						[180.1, 93.0], [175.5, 80.9], [180.6, 72.7], [184.4, 68.0], [175.5, 70.9],
						[180.6, 72.5], [177.0, 72.5], [177.1, 83.4], [181.6, 75.5], [176.5, 73.0],
						[175.0, 70.2], [174.0, 73.4], [165.1, 70.5], [177.0, 68.9], [192.0, 102.3],
						[176.5, 68.4], [169.4, 65.9], [182.1, 75.7], [179.8, 84.5], [175.3, 87.7],
						[184.9, 86.4], [177.3, 73.2], [167.4, 53.9], [178.1, 72.0], [168.9, 55.5],
						[157.2, 58.4], [180.3, 83.2], [170.2, 72.7], [177.8, 64.1], [172.7, 72.3],
						[165.1, 65.0], [186.7, 86.4], [165.1, 65.0], [174.0, 88.6], [175.3, 84.1],
						[185.4, 66.8], [177.8, 75.5], [180.3, 93.2], [180.3, 82.7], [177.8, 58.0],
						[177.8, 79.5], [177.8, 78.6], [177.8, 71.8], [177.8, 116.4], [163.8, 72.2],
						[188.0, 83.6], [198.1, 85.5], [175.3, 90.9], [166.4, 85.9], [190.5, 89.1],
						[166.4, 75.0], [177.8, 77.7], [179.7, 86.4], [172.7, 90.9], [190.5, 73.6],
						[185.4, 76.4], [168.9, 69.1], [167.6, 84.5], [175.3, 64.5], [170.2, 69.1],
						[190.5, 108.6], [177.8, 86.4], [190.5, 80.9], [177.8, 87.7], [184.2, 94.5],
						[176.5, 80.2], [177.8, 72.0], [180.3, 71.4], [171.4, 72.7], [172.7, 84.1],
						[172.7, 76.8], [177.8, 63.6], [177.8, 80.9], [182.9, 80.9], [170.2, 85.5],
						[167.6, 68.6], [175.3, 67.7], [165.1, 66.4], [185.4, 102.3], [181.6, 70.5],
						[172.7, 95.9], [190.5, 84.1], [179.1, 87.3], [175.3, 71.8], [170.2, 65.9],
						[193.0, 95.9], [171.4, 91.4], [177.8, 81.8], [177.8, 96.8], [167.6, 69.1],
						[167.6, 82.7], [180.3, 75.5], [182.9, 79.5], [176.5, 73.6], [186.7, 91.8],
						[188.0, 84.1], [188.0, 85.9], [177.8, 81.8], [174.0, 82.5], [177.8, 80.5],
						[171.4, 70.0], [185.4, 81.8], [185.4, 84.1], [188.0, 90.5], [188.0, 91.4],
						[182.9, 89.1], [176.5, 85.0], [175.3, 69.1], [175.3, 73.6], [188.0, 80.5],
						[188.0, 82.7], [175.3, 86.4], [170.5, 67.7], [179.1, 92.7], [177.8, 93.6],
						[175.3, 70.9], [182.9, 75.0], [170.8, 93.2], [188.0, 93.2], [180.3, 77.7],
						[177.8, 61.4], [185.4, 94.1], [168.9, 75.0], [185.4, 83.6], [180.3, 85.5],
						[174.0, 73.9], [167.6, 66.8], [182.9, 87.3], [160.0, 72.3], [180.3, 88.6],
						[167.6, 75.5], [186.7, 101.4], [175.3, 91.1], [175.3, 67.3], [175.9, 77.7],
						[175.3, 81.8], [179.1, 75.5], [181.6, 84.5], [177.8, 76.6], [182.9, 85.0],
						[177.8, 102.5], [184.2, 77.3], [179.1, 71.8], [176.5, 87.9], [188.0, 94.3],
						[174.0, 70.9], [167.6, 64.5], [170.2, 77.3], [167.6, 72.3], [188.0, 87.3],
						[174.0, 80.0], [176.5, 82.3], [180.3, 73.6], [167.6, 74.1], [188.0, 85.9],
						[180.3, 73.2], [167.6, 76.3], [183.0, 65.9], [183.0, 90.9], [179.1, 89.1],
						[170.2, 62.3], [177.8, 82.7], [179.1, 79.1], [190.5, 98.2], [177.8, 84.1],
						[180.3, 83.2], [180.3, 83.2]
					],
					markArea: {
						silent: true,
						itemStyle: {
							normal: {
								color: 'transparent',
								borderWidth: 1,
								borderType: 'dashed'
							}
						},
						data: [[{
							name: '负分布区间',
							xAxis: 'min',
							yAxis: 'min'
						}, {
							xAxis: 'max',
							yAxis: 'max'
						}]]
					},
					markPoint : {
						data : [
							{type : 'max', name: '最大值'},
							{type : 'min', name: '最小值'}
						]
					},
					markLine : {
						lineStyle: {
							normal: {
								type: 'solid'
							}
						},
						data : [
							{type : 'average', name: '平均值'},
							{ xAxis: 170 }
						]
					}
				}
			]
		};
	});
}

//跳转可视化进球
function toMatchScore(){
	matchScoreDialog.show();
	$("#match_score_dialog").load(contextPath+"/toMatchScore.do",function(){
		queryMatchScoreTimeList();
	});
}

//跳转凯利分布图
function toMatchKeli(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	matchKeliDialog.show();
	$("#match_Keli_dialog").load(contextPath+"/toMatchKeli.do",function(){
		$("#match_id").val(obj.match_id);
		queryMatchKeliList();
	});
}

function editMatch(){
	if(!editMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#editMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/editFootballMatch.do",
        success: editSuccessCallBack,
        error: editErrorCallBack
    });
}

function editSuccessCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	$("#editMatchForm").remove();
	editMatchDialog.close();
	queryFootballMatchList();
}

function editErrorCallBack(){
	 alert("请求出错了!");
}

function editMatchDialogCancel(){
	$("#editMatchForm").remove();
	editMatchDialog.close();
}

function addMatch(){
	if(!addMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#addMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/addFootballMatch.do",
        success: successCallBack,
        error: errorCallBack
    });
    $("#addMatchForm").remove();
	addMatchDialog.close();
	queryFootballMatchList();
}

function jsoupMatch(){
	if(!jsoupMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#jsoupMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/jsoupMatch.do",
        success: successCallBack,
        error: errorCallBack
    });
    $("#jsoupMatchForm").remove();
	jsoupMatchDialog.close();
	queryMatchList();
}

function batchJsoupMatch(){
	jsoupMatchDialog.show();
	$("#jsoup_match_dialog").load(contextPath+"/toJsoupMatch.do");
}

function toSendEmail(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	var sendData = {
		'match_name':obj.match_name,
		'match_title':obj.match_title,
		'match_time':obj.match_time,
		'match_desc':obj.match_desc
	};
	var operateMsg = '确定要推送'+ obj.home_team+' VS '+ obj.away_team+' 的比赛推荐吗';
	BUI.Message.Confirm(operateMsg, function(){
		forumConsole.ajaxCall('POST',contextPath+"/sendEmail.do",true,sendData,'json',successCallBack,errorCallBack);
	},'question');
}

function successCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	
}

function queryFootballMatchList(){
    forumConsole.refreshGrid('searchForm');
}

function completeCallBack(){}

function errorCallBack(){
	 alert("请求出错了!");
}

function addMatchDialogCancel(){
	$("#addMatchForm").remove();
	addMatchDialog.close();inter
}

function jsoupMatchDialogCancel(){
	$("#jsoupMatchForm").remove();
	jsoupMatchDialog.close();
}

function toAddFootballMatch(){
	addMatchDialog.show();
	$("#add_match_dialog").load(contextPath+"/toAddFootballMatch.do");
}

function toEditMatch(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	editMatchDialog.show();
	$("#edit_privilege_dialog").load(contextPath+"/toEditMatch.do?inter_id="+obj.inter_id,
		function(){
	
		}
	);
}

function batchDeleteMatch(){
	var objArray = gridObj.getCheckedRowsRecords();
	if(objArray.length == 0){
		BUI.Message.Alert('请选择需要删除的记录!','warning');
		return false;
	}
	var inter_ids ="";
	for(var i=0; i<objArray.length; i++){
		inter_ids += objArray[i].inter_id;
		if(i!= objArray.length - 1){
			inter_ids += ",";
		}
	}
	
	deleteMatch(inter_ids);
}

function deleteMatch(inter_ids){
	BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
		forumConsole.ajaxCall("POST",contextPath+"/deleteFootballMatch.do",true,{'inter_ids':inter_ids},"json",successCallBack,completeCallBack,errorCallBack);
    },'question');
}
