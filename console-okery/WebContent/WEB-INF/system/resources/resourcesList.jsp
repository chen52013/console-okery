<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>

<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">资源列表</li>
	</ol>
	
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td class="tableleft">资源名称</td>
					<td><input type="text" id="resource_name" name="resource_name" value="" /></td>

					<td class="tableleft">资源访问路径</td>
					<td><input type="text" id="resource_url" name="resource_url" value="" /></td>
					<td class="tableleft">资源描述</td>
					<td><input type="text" id="resource_desc"  name="resource_desc" value=""/></td>

					<td class="tableleft">资源类型</td>
					<td>
						<select id="resource_type" name="resource_type" >
							<option value="">全部</option>
		          			<option value="0">功能</option>
		          			<option value="1">菜单</option>
		          			<option value="2">界面</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="7">
					    <security:authorize url="${request.contextPath}/queryResourceList.do">
						<button type="button" class="btn btn-success" onclick="return queryResourceList();">查询</button>
						</security:authorize>
						&nbsp;&nbsp;&nbsp;
						<!-- 
						<button type="reset" class="btn btn-success">清空</button>
						 -->
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	       		<security:authorize url="toAddResourcePage.do">
	       		<button type="button" class="btn btn-warning" id="toAddResource" onclick="toAddResource()">添加资源</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<%-- <security:authorize url="toUpdateResourcePage.do">
	       		<button type="button" class="btn btn-warning" id="toUpdateResource" onclick="toUpdateResource()">编辑资源</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize> --%>
				<security:authorize url="deleteResource.do">
				<button type="button" class="btn btn-warning" id="batchDeleteResource" onclick="batchDeleteResource();">删除资源</button>
				</security:authorize>
	       </div>
	</div>
	
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="resource_id" width="3%"></th>
			    <th w_index="resource_name">资源名称</th>
			    <th w_render="renderResourceType">资源类型</th>
				<th w_index="resource_code">资源编码</th>
				<th w_index="resource_desc">资源描述 </th>
				<th w_index="resource_url">资源访问路径</th>
				<th w_render="operate">操作</th>
			</tr>
		</table>
	</div>

<script type="text/javascript">
var contextPath = '<%=request.getContextPath()%>';
$(function () {
	init(contextPath);
})

	//操作
    function operate(record, rowIndex, colIndex, options) {
	    var html = '';
	    var isAllowEdit = '<%=HomeAction.isAllowed("toUpdateResourcePage.do",request)%>';
		if("true" === isAllowEdit){
			html += '<a href="#" onclick="toUpdateResource(\''
					+ gridObj.getRecordIndexValue(record, 'resource_id')
					+ '\');">编辑</a>';
		}
		/*html += '&nbsp;&nbsp;&nbsp;';
		html += '<a href="#" onclick="deleteResource('+ rowIndex + ');">删除</a>';*/
		return html;
	}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sys/resources.js"></script>
</body>
</html>