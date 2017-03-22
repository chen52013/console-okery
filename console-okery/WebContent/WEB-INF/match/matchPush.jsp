<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赛事数据推送</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">赛事推荐</li>
	</ol>
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">推荐联赛</td>
					<td><input type="text" id="inter_url" name="inter_url"/></td>
					
					<td width="10%" class="tableleft">推荐类型</td>
					<td><input type="text" id="inter_name" name="inter_name"/></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="7">
					    <security:authorize url="${request.contextPath}/queryMatchList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryMatchList();">查询</button>
					    </security:authorize>		
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	            <button type="button" class="btn btn-warning" id="addMatch"
					onclick="return toAddMatch();">添加推荐</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning" id="delMatch"
					onclick="return batchDeleteMatch();">删除推荐</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning" id="jsoupMatch"
					onclick="return batchJsoupMatch();">抓取推荐</button>
	       </div>
	</div>
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="msg_id" width="3%;"></th>
				<th w_index="match_name">推荐联赛</th>
				<th w_index="match_title">比赛标题</th>
				<th w_index="match_time">比赛时间</th>
				<!-- <th w_index="match_desc">推荐描述</th> -->
				<th w_index="push_result">推荐结果</th>
				<th w_index="match_result">比赛结果</th>
				<th w_index="is_true">是否命中</th>
				<th w_index="match_money">投注金额</th>
				<th w_index="match_summary">比赛摘要</th>
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
	return html;
}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/match.js"></script>
</body>
</html>