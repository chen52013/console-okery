<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>足球赛事推荐</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/resources/js/echarts/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/echarts/echartsUtil.js"></script>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">足球赛事推荐</li>
	</ol>
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">比赛ID</td>
					<td><input type="text" id="match_id" name="match_id"/></td>

					<td width="10%" class="tableleft">比赛编号</td>
					<td><input type="text" id="match_num" name="match_num"/></td>

					<td width="10%" class="tableleft">比赛进球</td>
					<td><input type="text" id="sum_score" name="sum_score"/></td>

					<td width="10%" class="tableleft">比赛日期</td>
					<td><input type="text" id="match_time" name="match_time" value="" class="calendar calendar-time" data-rules="{date:true}"/></td>
				</tr>
				<tr>
					<td width="10%" class="tableleft">比赛状态</td>
					<td>
						<select id="item_status" name="item_status" >
							<option value="">全部</option>
							<option value="未开始">未开始</option>
							<option value="完">完</option>
						</select>
					</td>

					<td width="10%" class="tableleft">联赛名称</td>
					<td><input type="text" id="league_name" name="league_name"/></td>

					<td width="10%" class="tableleft">主队名称</td>
					<td><input type="text" id="home_team" name="home_team"/></td>

					<td width="10%" class="tableleft">客队名称</td>
					<td><input type="text" id="guest_team" name="guest_team"/></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="7">
					    <security:authorize url="${request.contextPath}/queryFootballMatchList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryFootballMatchList();">查询</button>
					    </security:authorize>		
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	            <button type="button" class="btn btn-warning" id="addMatch"
					onclick="return toAddMatch();">添加足球推荐</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning" id="delMatch"
					onclick="return batchDeleteMatch();">删除足球推荐</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning" id="jsoupMatch"
					onclick="return batchJsoupMatch();">抓取足球推荐</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning" id="matchScore"
					onclick="return toMatchScore();">可视化总进球</button>
	       </div>
	</div>
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="match_id" width="3%;"></th>
				<th w_index="match_id">比赛id</th>
				<th w_index="match_num">赛事编号</th>
				<th w_index="match_time">比赛时间</th>
				<th w_index="match_status">比赛状态</th>
				<th w_index="home_team">主队名</th>
				<th w_index="guest_team">客队名</th>
				<th w_index="league_name">联赛名</th>
				<th w_index="match_score">比赛比分</th>
				<th w_index="sum_score">总进球</th>
				<th w_render="guess">球探预测</th>
				<th w_render="first_result">凯利预测</th>
				<th w_index="last_result">比赛结果</th>
				<th w_index="win">胜</th>
				<th w_index="draw">平</th>
				<th w_index="lost">负</th>
				<th w_render="operate">操作</th>
			</tr>
		</table>
	</div>
<script type="text/javascript">
var contextPath = '<%=request.getContextPath()%>';
$(function () {
	init(contextPath);
})
function operate(record, rowIndex, colIndex, options){
	var html = '<a href="#" onclick="toSendEmail('+ rowIndex + ');">推送</a>';
	html += "&nbsp;&nbsp;&nbsp;&nbsp";
	html +='<a href="#" onclick="toMatchKeli('+ rowIndex + ');">凯利指数</a>';
	return html;
}

function first_result(record, rowIndex, colIndex, options){
	var first_result = record.first_result;
	var last_result = record.last_result;
	if(first_result != null && "" != first_result && typeof(first_result) != 'undefined'){
		if(first_result.indexOf(last_result) != -1){
			return "<color style='color:red;'>"+first_result+"</color>";
		}else{
			return first_result;
		}
	}
}

function guess(record, rowIndex, colIndex, options){
	var guess = record.guess;
	if(guess != null && "" != guess && typeof(guess) != 'undefined'){
		if(guess.indexOf("命中") != -1){
			return "<color style='color:red;'>"+guess+"</color>";
		}else{
			return guess;
		}
	}else {
		return "暂无预测";
	}
}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/footballMatch.js"></script>
</body>
</html>