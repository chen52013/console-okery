<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>五杀页面</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">网站名称</li>
	</ol>
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">网站名称</td>
					<td><input type="text" id="inter_name" name="inter_name"/></td>
					<td width="10%" class="tableleft">网站地址</td>
					<td><input type="text" id="inter_url" name="inter_url"/></td>
					<td width="10%" class="tableleft">所属分类</td>
					<td><input type="text" id="channel_name" name="channel_name"/></td>
					<td width="10%" class="tableleft">状态</td>
					<td><input type="text" id="inter-status" name="inter-status"/></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="7">
					    <security:authorize url="${request.contextPath}/queryInterList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryInterList();">查询</button>
					    </security:authorize>		
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	       		<security:authorize url="toAddInter.do">
	            <button type="button" class="btn btn-warning" id="addInter"
					onclick="return toAddInter();">添加收藏</button>
				</security:authorize>
				&nbsp;&nbsp;&nbsp;
				<security:authorize url="deleteInter.do">
				<button type="button" class="btn btn-warning" id="delInter"
					onclick="return batchDeleteInter();">删除收藏</button>
				</security:authorize>
	       </div>
	</div>
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="inter_id" width="3%;"></th>
				<th w_index="inter_name">网站名称</th>
				<th w_index="channel_name">所属分类</th>
				<th w_index="inter_url">网站地址</th>
				<th w_index="inter_desc">网站描述</th>
				<th w_index="inter_crt_time">创建时间</th>
				<th w_index="inter_mod_time">编辑时间</th>
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
	var html = '<a href="#" onclick="toGoJsoup('+ rowIndex  + ');">抓取</a>';
	html += '&nbsp;&nbsp;&nbsp;';
	html += '<a href="#" onclick="toSendEmail('+ rowIndex + ');">发送</a>';
	return html;
}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/inter.js"></script>
</body>
</html>