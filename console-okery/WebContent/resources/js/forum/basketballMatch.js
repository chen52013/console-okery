var editBasketballMatchDialog;
var pkBasketballMatchDialog;

var editBasketballMatchForm;

//查询表单渲染
BUI.use(['bui/form'],function(Form){
	editBasketballMatchForm = new Form.HForm({
		srcNode : "#editBasketballMatchForm",
	});
	editBasketballMatchForm.render();
});

//时间条件处理
BUI.use('bui/calendar',function(Calendar){
	var datepicker = new Calendar.DatePicker({
		trigger:'.calendar-time',
		showTime:true,
		autoRender : true
	});
});
//初始化方法
function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	forumConsole.init('searchTable',webContextPath+'/queryBasketballMatchList.do','json',10);
	editBasketballMatchDialog = forumConsole.genDialog(webContextPath,'编辑篮球赛事推荐','800','auto','保存','关闭','editBasketballMatchForm','editBasketballMatchDialog',editBasketballMatch,editBasketballMatchDialogCancel);
	pkBasketballMatchDialog = forumConsole.genDetailDialog(webContextPath,'篮球各项数据对比','800','auto','关闭','pkBasketballMatchForm','pkBasketballMatchDialog',pkBasketballMatchDialogCancel);
}
//主队
function home_team(record, rowIndex, colIndex, options){
	var html = '';
	html = '<a href="#" onclick="toGoHomeTeamDetail(\''+ rowIndex + '\');">'+record.home_team+'</a>';
	html += '&nbsp;&nbsp;&nbsp;';
	return html;
}
//客队
function guest_team(record, rowIndex, colIndex, options){
	var html = '';
	html = '<a href="#" onclick="toGoGuestTeamDetail(\''+ rowIndex + '\');">'+record.guest_team+'</a>';
	html += '&nbsp;&nbsp;&nbsp;';
	return html;
}
//比赛得分
function match_score(record, rowIndex, colIndex, options){
	var home_score = record.home_score;
	var guest_score = record.guest_score;
	if(home_score && guest_score){
		if(home_score > guest_score){
			return "<font color='red'>"+home_score+"</font>-"+guest_score;
		}else{
			return home_score+"-<font color='red'>"+guest_score+"</font>";
		}
	}
}
//预测是否命中
function guess(record, rowIndex, colIndex, options){
	var guess = record.guess;
	if(guess.indexOf("命中") != -1){
		return "<color style='color:red;'>"+guess+"</color>";
	}else{
		return guess;
	}
}

//查看主队走势图
function toGoHomeTeamDetail(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	window.location.href=contextPath+"/toGoTeamDetail.do?home_id="+obj.home_id+"&home_team="+obj.home_team+"&league_name="+obj.league_name;
}

//查看客队走势图
function toGoGuestTeamDetail(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	window.location.href=contextPath+"/toGoTeamDetail.do?guest_id="+obj.guest_id+"&guest_team="+obj.guest_team+"&league_name="+obj.league_name;
}

function toPkBasketballMatch(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	var guest_id = obj.guest_id;
	pkBasketballMatchDialog.show();
	$("#pkBasketballMatchDialog").load(contextPath+"/toPkBasketballMatch.do",
		function(){
			$("#pk_match_id").val(obj.match_id);
			$("#pk_match_time").html(obj.match_time);
			$("#pk_home_team").html(obj.home_team);
			$("#pk_guest_team").html(obj.guest_team);

			var home_avg_score = 0;
			var guest_avg_score = 0;

			//根据主队id 比赛时间 联赛名称 查询主队数据
			var otherParams = {
				'team_id':obj.home_id,
				'match_time':obj.match_time,
				'league_name':obj.league_name
			};
			forumConsole.ajaxCall('POST', contextPath + "/queryBasketballMatchDetailList.do", false, otherParams, 'json', function(data){
				if(null == data || data == undefined){
					BUI.Message.Alert("请求失败了!",'warning');
					return;
				}
				if(data.success){
					var sData = data.data;
					if(sData != null){
						home_avg_score = sData.avg_score;
						var lost_count = sData.lost_count;
						var win_count = sData.win_count;
						$("#home_lost_count").html(lost_count+" ("+(lost_count/(lost_count+win_count)*100).toFixed(2)+"%) 负");
						$("#home_win_count").html(win_count+" ("+(win_count/(lost_count+win_count)*100).toFixed(2)+"%) 胜");
						$("#home_win_count").css("width",(win_count/(lost_count+win_count)*100).toFixed(2)+"%");
						$("#home_lost_count").css("width",(lost_count/(lost_count+win_count)*100).toFixed(2)+"%");

						// $("#home_win_precent").html(win_count+" ("+(win_count/(lost_count+win_count)*100).toFixed(2)+"%) 胜");
						// $("#home_lost_precent").html(lost_count+" ("+(lost_count/(lost_count+win_count)*100).toFixed(2)+"%) 负");
						// $("#home_win_precent").css("width",(win_count/(lost_count+win_count)*100).toFixed(2)+"%");
						// $("#home_lost_precent").css("width",(lost_count/(lost_count+win_count)*100).toFixed(2)+"%");


						var let_lost_count = sData.let_lost_count;
						var let_win_count = sData.let_win_count;
						$("#home_let_lost_precent").html(let_lost_count+" ("+(let_lost_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%) 输盘");
						$("#home_let_win_precent").html(let_win_count+" ("+(let_win_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%) 赢盘");
						$("#home_let_lost_precent").css("width",(let_lost_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%");
						$("#home_let_win_precent").css("width",(let_win_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%");

						var big_count = sData.big_count;
						var small_count = sData.small_count;
						$("#home_small_precent").html(small_count+" ("+(small_count/(small_count+big_count)*100).toFixed(2)+"%) 小分");
						$("#home_big_precent").html(big_count+" ("+(big_count/(small_count+big_count)*100).toFixed(2)+"%) 大分");
						$("#home_small_precent").css("width",(small_count/(small_count+big_count)*100).toFixed(2)+"%");
						$("#home_big_precent").css("width",(big_count/(small_count+big_count)*100).toFixed(2)+"%");


						$("#pk_guest_team").html(obj.guest_team);
					}
				}
			});

			//根据主队id 比赛时间 联赛名称 查询主队数据
			var otherParams = {
				'team_id':obj.guest_id,
				'match_time':obj.match_time,
				'league_name':obj.league_name
			};
			forumConsole.ajaxCall('POST', contextPath + "/queryBasketballMatchDetailList.do", false, otherParams, 'json', function(data){
				if(null == data || data == undefined){
					BUI.Message.Alert("请求失败了!",'warning');
					return;
				}
				if(data.success){
					var sData = data.data;
					if(sData != null){
						guest_avg_score = sData.avg_score;
						var lost_count = sData.lost_count;
						var win_count = sData.win_count;
						$("#guest_lost_count").html(lost_count+" ("+(win_count/(lost_count+win_count)*100).toFixed(2)+"%) 负");
						$("#guest_win_count").html(win_count+" ("+(win_count/(lost_count+win_count)*100).toFixed(2)+"%) 胜");
						$("#guest_win_count").css("width",(win_count/(lost_count+win_count)*100).toFixed(2)+"%");
						$("#guest_lost_count").css("width",(lost_count/(lost_count+win_count)*100).toFixed(2)+"%");

						$("#guest_win_precent").html(win_count+" ("+(win_count/(lost_count+win_count)*100).toFixed(2)+"%) 胜");
						$("#guest_lost_precent").html(lost_count+" ("+(lost_count/(lost_count+win_count)*100).toFixed(2)+"%) 负");
						$("#guest_win_precent").css("width",(win_count/(lost_count+win_count)*100).toFixed(2)+"%");
						$("#guest_lost_precent").css("width",(lost_count/(lost_count+win_count)*100).toFixed(2)+"%");


						var let_lost_count = sData.let_lost_count;
						var let_win_count = sData.let_win_count;
						$("#guest_let_lost_precent").html(let_lost_count+" ("+(let_lost_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%) 输盘");
						$("#guest_let_win_precent").html(let_win_count+" ("+(let_win_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%) 赢盘");
						$("#guest_let_lost_precent").css("width",(let_lost_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%");
						$("#guest_let_win_precent").css("width",(let_win_count/(let_lost_count+let_win_count)*100).toFixed(2)+"%");

						var big_count = sData.big_count;
						var small_count = sData.small_count;
						$("#guest_small_precent").html(small_count+" ("+(small_count/(small_count+big_count)*100).toFixed(2)+"%) 小分");
						$("#guest_big_precent").html(big_count+" ("+(big_count/(small_count+big_count)*100).toFixed(2)+"%) 大分");
						$("#guest_small_precent").css("width",(small_count/(small_count+big_count)*100).toFixed(2)+"%");
						$("#guest_big_precent").css("width",(big_count/(small_count+big_count)*100).toFixed(2)+"%");


						$("#pk_guest_team").html(obj.guest_team);
					}
				}
			});

			$("#avg_home_score").html(home_avg_score);
			$("#avg_guest_score").html(guest_avg_score);
			$("#avg_guest_score").css("width",(guest_avg_score/(guest_avg_score+home_avg_score)*100).toFixed(2)+"%");
			$("#avg_home_score").css("width",(home_avg_score/(guest_avg_score+home_avg_score)*100).toFixed(2)+"%");


			//根据客队id 比赛时间 联赛名称 查询主队数据
			var otherParams = {
				'guest_id':obj.guest_id,
				'home_id':obj.home_id,
				'match_time':obj.match_time,
				'league_name':obj.league_name,
				'is_true':'true'
			};
			forumConsole.ajaxCall('POST', contextPath + "/queryBasketballMatchDetailList.do", false, otherParams, 'json', function(data){
				if(null == data || data == undefined){
					BUI.Message.Alert("请求失败了!",'warning');
					return;
				}
				if(data.success){
					var sData = data.data;
					if(sData != null){
						var h_win_count = sData.h_win_count;
						var h_lost_count = sData.h_lost_count;
						var g_win_count = sData.g_win_count;
						var g_lost_count = sData.g_lost_count;

						$("#h_lost_count").html(h_lost_count+" ("+(h_lost_count/(h_lost_count+h_win_count)*100).toFixed(2)+"%) 负");
						$("#h_win_count").html(h_win_count+" ("+(h_win_count/(h_lost_count+h_win_count)*100).toFixed(2)+"%) 胜");
						$("#h_win_count").css("width",(h_win_count/(h_lost_count+h_win_count)*100).toFixed(2)+"%");
						$("#h_lost_count").css("width",(h_lost_count/(h_lost_count+h_win_count)*100).toFixed(2)+"%");

						$("#g_lost_count").html(g_lost_count+" ("+(g_lost_count/(g_lost_count+g_win_count)*100).toFixed(2)+"%) 负");
						$("#g_win_count").html(g_win_count+" ("+(g_win_count/(g_lost_count+g_win_count)*100).toFixed(2)+"%) 胜");
						$("#g_win_count").css("width",(g_win_count/(g_lost_count+g_win_count)*100).toFixed(2)+"%");
						$("#g_lost_count").css("width",(g_lost_count/(g_lost_count+g_win_count)*100).toFixed(2)+"%");

						var h_let_win_count = sData.h_let_win_count;
						var h_let_lost_count = sData.h_let_lost_count;
						var g_let_win_count = sData.g_let_win_count;
						var g_let_lost_count = sData.g_let_lost_count;

						$("#h_let_lost_count").html(h_let_lost_count+" ("+(h_let_lost_count/(h_let_lost_count+h_let_win_count)*100).toFixed(2)+"%) 负");
						$("#h_let_win_count").html(h_let_win_count+" ("+(h_let_win_count/(h_let_lost_count+h_let_win_count)*100).toFixed(2)+"%) 胜");
						$("#h_let_win_count").css("width",(h_let_win_count/(h_let_lost_count+h_let_win_count)*100).toFixed(2)+"%");
						$("#h_let_lost_count").css("width",(h_let_lost_count/(h_let_lost_count+h_let_win_count)*100).toFixed(2)+"%");

						$("#g_let_lost_count").html(g_let_lost_count+" ("+(g_let_win_count/(g_let_lost_count+g_let_win_count)*100).toFixed(2)+"%) 负");
						$("#g_let_win_count").html(g_let_win_count+" ("+(g_let_win_count/(g_let_lost_count+g_let_win_count)*100).toFixed(2)+"%) 胜");
						$("#g_let_win_count").css("width",(g_let_win_count/(g_let_lost_count+g_let_win_count)*100).toFixed(2)+"%");
						$("#g_let_lost_count").css("width",(g_let_lost_count/(g_let_lost_count+g_let_win_count)*100).toFixed(2)+"%");

						var big_count = sData.big_count;
						var small_count = sData.small_count;
						$("#hg_small_count").html(small_count+" ("+(small_count/(small_count+big_count)*100).toFixed(2)+"%) 小分");
						$("#hg_big_count").html(big_count+" ("+(big_count/(small_count+big_count)*100).toFixed(2)+"%) 大分");
						$("#hg_big_count").css("width",(big_count/(small_count+big_count)*100).toFixed(2)+"%");
						$("#hg_small_count").css("width",(small_count/(small_count+big_count)*100).toFixed(2)+"%");
					}
				}
			});
		}
	);
}

//跳转到编辑篮球比赛推荐页面
function toEditBasketballMatch(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	editBasketballMatchDialog.show();
	$("#editBasketballMatchDialog").load(contextPath+"/toEditBasketballMatch.do",
		function(){
			$("#edit_match_id").val(obj.match_id);
			$("#edit_match_num").val(obj.match_num);
			$("#edit_match_time").val(obj.match_time);
			$("#edit_match_status").val(obj.match_status);
			$("#edit_home_score").val(obj.home_score);
			$("#edit_guest_score").val(obj.guest_score);
			$("#edit_home_team").val(obj.home_team);
			$("#edit_guest_team").val(obj.guest_team);
			$("#edit_guess").val(obj.guess);
			$("#edit_win").val(obj.win);
			$("#edit_lost").val(obj.lost);
			$("#edit_outcome").val(obj.outcome);
			$("#edit_outcome_odds").val(obj.outcome_odds);
			$("#edit_let_points").val(obj.let_points);
			$("#edit_let_points_odds").val(obj.let_points_odds);
			$("#edit_score_diff").val(obj.score_diff);
			$("#edit_score_diff_odds").val(obj.score_diff_odds);
			$("#edit_big_small_score").val(obj.big_small_score);
			$("#edit_big_small_score_odds").val(obj.big_small_score_odds);

			forumConsole.ajaxCall('POST', contextPath + "/queryBasketballLeagueList.do", false, null, 'json', function(data){
				if(null == data || data == undefined){
					BUI.Message.Alert("请求失败了!",'warning');
					return;
				}
				if(data.success){
					var html = "<option value=''>全部</option>";
					for (var i = 0; i < data.data.length; i++) {
						if(obj.league_name == data.data[i].league_name){
							html += "<option value='"+data.data[i].league_name+"' selected>"+data.data[i].league_name+"</option>";
						}else{
							html += "<option value='"+data.data[i].league_name+"'>"+data.data[i].league_name+"</option>";
						}
					}
					$("#edit_league_name").html(html);
				}
			});
		}
	);
}

function editBasketballMatch(){
	if(!editBasketballMatchForm.isValid()){//表单数据验证
		return;
	}
	$("#editBasketballMatchForm").ajaxSubmit({
		type: "post",
		url: contextPath+ "/editBasketballMatch.do",
		success: function (data){
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
			$("#editBasketballMatchForm").remove();
			editBasketballMatchDialog.close();
			queryBasketballMatchList();
		},
		error: function (){
			BUI.Message.Alert("请求出错了!",'error');
		}
	});
}

function editBasketballMatchDialogCancel(){
	$("#editBasketballMatchForm").remove();
	editBasketballMatchDialog.close();
}

function pkBasketballMatchDialogCancel(){
	$("#pkBasketballMatchForm").remove();
	pkBasketballMatchDialog.close();
}

function queryBasketballMatchList(){
	forumConsole.refreshGrid('searchForm');
}
