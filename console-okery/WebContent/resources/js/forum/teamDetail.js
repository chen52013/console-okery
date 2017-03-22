var teamDialog;

//时间条件处理
BUI.use('bui/calendar',function(Calendar){
	var datepicker = new Calendar.DatePicker({
		trigger:'.calendar-time',
		showTime:true,
		autoRender : true
	});
});

function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	var league_name = $("#league_name").val();
	if(league_name != null && league_name != "" && typeof(league_name) != 'undefined'){
		queryLeagueList(league_name);
	}
	var home_id = $("#home_id").val();
	if(home_id != null && home_id != "" && typeof(home_id) != 'undefined'){
		queryTeamList(home_id);
	}
	var guest_id = $("#guest_id").val();
	if(guest_id != null && guest_id != "" && typeof(guest_id) != 'undefined'){
		queryTeamList(guest_id);
	}
	queryTeamDetailList();
}

//查询联赛
function queryLeagueList(league_name){
	forumConsole.ajaxCall('POST', contextPath + "/queryBasketballLeagueList.do", false, null, 'json', function(data){
		if(null == data || data == undefined){
			BUI.Message.Alert("请求失败了!",'warning');
			return;
		}
		if(data.success){
			var html = "<option value=''>全部</option>";
			for (var i = 0; i < data.data.length; i++) {
				if(league_name == data.data[i].league_name){
					html += "<option value='"+data.data[i].league_name+"' selected>"+data.data[i].league_name+"</option>";
				}else{
					html += "<option value='"+data.data[i].league_name+"'>"+data.data[i].league_name+"</option>";
				}
			}
			$("#_league_name").html(html);
		}
	});
}
//查询球队
function queryTeamList(home_id) {
	var sendData = {};
	var league_name = $("#_league_name").val();
	if(league_name != "" && league_name != null && typeof(league_name) != 'undefined'){
		sendData.league_name = league_name;
	}
	forumConsole.ajaxCall('POST', contextPath + "/queryBasketballTeamList.do", false, sendData, 'json', function(_data){
		if(null == _data || _data == undefined){
			BUI.Message.Alert("请求失败了!",'warning');
			return;
		}
		if(_data.success){
			var html = "<option value=''>全部</option>";
			for (var i = 0; i < _data.data.length; i++) {
				if(home_id == _data.data[i].guest_id){
					html += "<option value='"+_data.data[i].guest_id+"' selected>"+_data.data[i].guest_team+"</option>";
				}else{
					html += "<option value='"+_data.data[i].guest_id+"'>"+_data.data[i].guest_team+"</option>";
				}
			}
			$("#team").html(html);
		}
	});
}

//查询球队走势图
function queryTeamDetailList(count){
	var sendData = {};
	var start_time = $("#start_time").val();
	if(start_time){
		sendData.start_time = start_time;
	}
	var end_time = $("#end_time").val();
	if(end_time){
		sendData.end_time = end_time;
	}
	var team = $("#team option:selected").val();
	if(team){
		sendData.home_id = team;
	}
	var league_name = $("#_league_name").val();
	if(league_name){
		sendData.league_name = league_name;
	}
	if(!isNaN(count) && count > 0){
		sendData.count = count;
	}
	var title = league_name;
	var home_team = $("#_home_team").val();
	if(home_team != "" && home_team != null && typeof(home_team) != 'undefined'){
		title = home_team;
	}
	var guest_team = $("#_guest_team").val();
	if(guest_team != "" && guest_team != null && typeof(guest_team) != 'undefined'){
		title = guest_team;
	}
	var team_name = $("#team option:selected").text();
	if(team_name != "" && team_name != null && typeof(team_name) != 'undefined'){
		title = team_name;
	}

	forumConsole.ajaxCall('POST', contextPath + "/queryTeamDetailList.do", false, sendData, 'json', function(pie_elements){
		// echartsUtil.plot_bar_or_line('pie_div', '球队走势图', ' ', plot_elements.data, 'period_name','line', {match_score:'比赛得分',outcome:'比赛胜负',let_points:'让分胜负',big_small_score:'大小分'},true);
		var data = pie_elements.data;
		var count_num = data.length;
		var out_count = 0;			//胜场
		var let_count = 0;			//让胜场
		var big_count = 0;			//大分场次
		var score_count = 0;		//球队得分超过100分场次
		var _score = 0;				//球队总得分
		var _avg_score = 0;			//球队总得分
		var sum_score_count = 0;	//比赛总分超过200分场次
		var _sum_score = 0;			//比赛总得分
		var _avg_sum_score = 0;		//比赛总得分
		for(var j = 0;j<count_num;j++){
			if(data[j].match_score != null && data[j].match_score != "" && typeof(data[j].match_score) != 'undefined'){
				_score += parseInt(data[j].match_score);
			}
			if(data[j].result_score != null && data[j].result_score != "" && typeof(data[j].result_score) != 'undefined'){
				_sum_score += parseInt(data[j].result_score);
			}
		}
		if(!isNaN(count_num) && count_num > 0){
			_avg_score = (_score / count_num).toFixed(2);
			_avg_sum_score = (_sum_score / count_num).toFixed(2);
		}
		for(var i = 0;i<count_num;i++){
			if(data[i].let_points == "胜"){
				let_count++;
			}
			if(data[i].out_come == "胜"){
				out_count++;
			}
			if(data[i].big_small_score == "200" || data[i].big_small_score == 200){
				big_count++;
			}
			if(data[i].match_score > _avg_score){
				score_count++;
			}
			if(data[i].result_score > _avg_sum_score){
				sum_score_count++;
			}
		}
		var out_precent = 0;			//胜场
		var let_precent = 0;			//让胜场
		var big_precent = 0;			//大球场
		var score_precent = 0;			//球队得分超过平均分场次
		var result_score_precent = 0;	//总得分超过平均总得分场次
		if(!isNaN(count_num) && count_num > 0){
			if(!isNaN(out_count) && out_count > 0){
				out_precent = (out_count/count_num*100).toFixed(2);
			}
			if(!isNaN(let_count) && let_count > 0){
				let_precent = (let_count/count_num*100).toFixed(2);
			}
			if(!isNaN(big_count) && big_count > 0){
				big_precent = (big_count/count_num*100).toFixed(2);
			}
			if(!isNaN(big_count) && score_count > 0){
				score_precent = (score_count/count_num*100).toFixed(2);
			}
			if(!isNaN(big_count) && sum_score_count > 0){
				result_score_precent = (sum_score_count/count_num*100).toFixed(2);
			}
		}
		var html = ""+count_num+"场比赛中,"+title+"总共胜<font color='red'><b>"+out_count+"</b></font>/"+count_num+"场,负"+(count_num-out_count)+"/"+count_num+"场,胜率<font color='red'><b>"+out_precent
			+"</b></font>%,让胜<font color='red'><b>"+let_count+"</b></font>/"+count_num+"场，让胜胜率<font color='red'><b>"+let_precent+"</b></font>%,大分<font color='red'><b>"+big_count+"</b></font>/"+count_num+"场,小分"
			+(count_num-big_count)+"/"+count_num+"场，大分率<font color='red'><b>"+big_precent+"</b></font>%,球队得分超过"+_avg_score+"分有<font color='red'><b>"+score_count+"</b></font>/"+count_num
			+"场(<font color='red'><b>"+score_precent+"</b>%</font>),比赛总分超过"+_avg_sum_score+"分有<font color='red'><b>"+sum_score_count+"</b></font>/"+count_num+"场(<font color='red'><b>"+result_score_precent+"</b>%</font>)";
		$("#result").html(html);
		var x_axis_col_name = 'match_time';
		var plot_type = 'line';
		title += ' [球队走势图]';
		var features_to_show = {match_score:'球队得分',result_score:'比赛总分',out_com_num:'比赛胜负',let_points_num:'让分胜负',big_small_score:'大小分'};
		var myChart = echarts.init(document.getElementById('pie_div'));
		var x_axis_arr = [];
		var x_axis = {};
		x_axis.type = 'category';
		x_axis.data = [];
		x_axis_arr[0] = x_axis;

		var y_series_map = {};
		var legend_data = [];
		var y_series = [];

		if(plot_type == null || plot_type == '' || typeof(plot_type) == 'undefined'){
			plot_type = 'line';
		}else if(plot_type != 'bar' && plot_type != 'line'){
			alert("Please confirm that plot type you choose is bar or line.");
			return;
		}

		data.forEach(function(r){
			for(var col_name in r){
				if(col_name == x_axis_col_name){
					x_axis.data.push(r[x_axis_col_name]);
				}else if(features_to_show.hasOwnProperty(col_name)){
					if(y_series_map[col_name] == null){
						y_series_map[col_name] = {};
						y_series_map[col_name].name = features_to_show[col_name];
						y_series_map[col_name].type = plot_type;
						y_series_map[col_name].data = [];
						y_series_map[col_name].data.push(r[col_name] == null || r[col_name] == '' || typeof(r[col_name]) == 'undefined'? 0:r[col_name]);
						y_series_map[col_name].markLine = {
							data : [
								{type : 'average', name: '平均值'}
							]
						};
						y_series_map[col_name].markPoint = {
							data : [
								{type : 'max', name: '最大值'},
								{type : 'min', name: '最小值'}
							]
						};
						console.log(r[col_name]);
					}else{
						y_series_map[col_name].data.push((r[col_name] == null || r[col_name] == '' || typeof(r[col_name]) == 'undefined') ? 0 : r[col_name]);
					}
				}
			}
		});

		for(var col_name in data[0]){
			if(col_name == x_axis_col_name){
			}else if(features_to_show.hasOwnProperty(col_name)){
				legend_data.push(features_to_show[col_name]);
				y_series.push(y_series_map[col_name]);
			}
		};
		myChart.showLoading({text: '正在努力的读取数据中...'});
		var option = {
			title : {
				text:title
				// subtext: (subtitle == null || subtitle == '' || typeof(subtitle) == 'undefined') ? '': subtitle
			},
			tooltip : {
				trigger: 'axis'
			},
			dataZoom: {
				show: true,
				start : 0
			},
			legend: {
				data:legend_data
			},
			toolbox: {
				show : true,
				orient: 'vertical',
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : x_axis,
			yAxis : [
				{
					type : 'value'
				}
			],
			series : y_series
		};
		myChart.setOption(option);
		myChart.hideLoading();
	});
}

function returnBasketballListPage(nick_name,user_mobile){
	window.location.href=contextPath+"/toBasketballMatch.do";
}