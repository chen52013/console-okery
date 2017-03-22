<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>篮球赛事推荐</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/resources/js/echarts/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/echarts/echartsUtil.js"></script>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">篮球赛事推荐</li>
	</ol>
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">比赛编号</td>
					<td><input type="text" id="match_num" name="match_num"/></td>
					<td width="10%" class="tableleft">比赛时间</td>
					<td>
						<input type="text" id="start_time" name="start_time" value="" class="calendar calendar-time" data-rules="{date:true}"/>
						&nbsp;至&nbsp;
						<input type="text" id="end_time" name="end_time" value="" class="calendar calendar-time" data-rules="{date:true}"/>
					</td>
					<td width="10%" class="tableleft">比赛状态</td>
					<td>
						<select id="match_status" name="match_status">
							<option value="完">完</option>
							<option value="未开始">未开始</option>
							<option value="取消">取消</option>
							<option value="第一节">第一节</option>
							<option value="第二节">第二节</option>
							<option value="第三节">第三节</option>
							<option value="第四节">第四节</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft">球队名称</td>
					<td><input type="text" id="team_name" name="team_name"/></td>
					<td width="10%" class="tableleft">联赛名称</td>
					<td>
						<select id="_league_name" name="league_name">

						</select>
					</td>
					<td colspan="7">
						<security:authorize url="${request.contextPath}/queryBasketballMatchList.do">
							<button type="button" class="btn btn-success"
									onclick="return queryBasketballMatchList();">查询</button>
						</security:authorize>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="match_id" width="3%;"></th>
				<th w_index="match_num">比赛编号</th>
				<th w_index="match_time">比赛时间</th>
				<th w_index="match_status">比赛状态</th>
				<th w_render="match_score">比赛比分</th>
				<th w_render="home_team">主队名</th>
				<th w_render="pk_render">数据对比</th>
				<th w_render="guest_team">客队名</th>
				<th w_index="league_name">联赛名</th>
				<th w_render="guess">预测</th>
				<th w_index="win">胜</th>
				<th w_index="lost">负</th>
				<th w_render="operate">操作</th>
			</tr>
		</table>
	</div>
<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	$(function () {
		init(contextPath);
		//加载篮球联赛下拉列表
		forumConsole.ajaxCall('POST', contextPath + "/queryBasketballLeagueList.do", false, null, 'json', function(data){
			if(null == data || data == undefined){
				BUI.Message.Alert("请求失败了!",'warning');
				return;
			}
			if(data.success){
				var html = "<option value=''>全部</option>";
				for (var i = 0; i < data.data.length; i++) {
					html += "<option value='"+data.data[i].league_name+"'>"+data.data[i].league_name+"</option>";
				}
				$("#_league_name").html(html);
			}
		});
	})

	//编辑
	function operate(record, rowIndex, colIndex, options) {
		var html = '';
		html += '<a href="#" onclick="toEditBasketballMatch('+ rowIndex + ');">编辑</a>';
		return html;
	}

	function pk_render(record, rowIndex, colIndex, options){
		var html = '';
		html += '<a href="#" onclick="toPkBasketballMatch('+ rowIndex + ');">PK</a>';
		return html;
	}

</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/basketballMatch.js"></script>

</body>
</html>