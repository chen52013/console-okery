<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限列表</title>

<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">权限列表</li>
	</ol>
	
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">权限名称</td>
					<td><input type="text" id="privilege_name" name="privilege_name"/></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td>
					    <security:authorize url="${request.contextPath}/queryPrivilegeList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryPrivilegeList();">查询</button>
					    </security:authorize>		
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	       		<security:authorize url="toAddPrivilege.do">
	            <button type="button" class="btn btn-warning" id="addPrivilege"
					onclick="return toAddPrivilege();">添加权限</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<%-- <security:authorize url="toEditPrivilege.do">
				<button type="button" class="btn btn-warning" id="editPrivilege"
					onclick="return toEditPrivilege();">编辑权限</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize> --%>	
				<security:authorize url="deletePrivilege.do">
				<button type="button" class="btn btn-warning" id="delPrivilege"
					onclick="return batchDeletePrivilege();">删除权限</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<security:authorize url="refreshPrivilegeCache.do">
				<button type="button" class="btn btn-warning" id="refreshPrivilegeCache"
					onclick="return refreshPrivilegeCache();">刷新权限缓存</button>
				</security:authorize>
	       </div>
	</div>
	
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="privilege_id" width="3%;"></th>
				<th w_index="privilege_name">权限名</th>
				<th w_index="privilege_desc">权限描述</th>
				<th w_index="privilege_code">权限编码</th>
				<th width="10%"  w_render="privilegeContent">资源</th>
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
	var html = '';
	var isAllowPrivilege = '<%=HomeAction.isAllowed("toEditPrivilege.do",request)%>';
	if("true" === isAllowPrivilege){
	    html = '<a href="#" onclick="toEditPrivilege('+ rowIndex  + ');">编辑</a>';
	}
	return html;
}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sys/privilege.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ztree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/ztree/css/zTreeStyle/zTreeStyle.css" />
</body>
</html>