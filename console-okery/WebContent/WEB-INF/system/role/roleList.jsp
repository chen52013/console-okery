<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>

<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">角色列表</li>
	</ol>
	
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">角色名称</td>
					<td><input type="text" id="roleName" name="roleName"/></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td>
					    <security:authorize url="${request.contextPath}/queryRoleList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryRoleList();">查询</button>
						</security:authorize>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="container-fluid " style="padding-left:40px">
	       <div class="pull-left" >
	       		<%-- <security:authorize url="toAssignPrivilege.do">
	            <button type="button" class="btn btn-warning" id="toAssignPrivilege"
					onclick="return toAssignPrivilege();">分配权限</button>
				</security:authorize> --%>
	       		<security:authorize url="toAddRole.do">
	            <button type="button" class="btn btn-warning" id="addRole"
					onclick="return toAddRole();">添加角色</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<%-- <security:authorize url="toEditRole.do">
	            <button type="button" class="btn btn-warning" id="toEditRole"
					onclick="return toEditRole();">编辑角色</button>
				</security:authorize> --%>
				<security:authorize url="deleteRole.do">
				<button type="button" class="btn btn-warning" id="del"
					onclick="return batchDeleteRole();">删除角色</button>
				</security:authorize>
	       </div>
	</div>
	
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="roleId" width="3%;"></th>
				<th w_index="roleName">角色名</th>
				<th w_index="roleDesc">角色描述</th>
				<th w_index="roleCode">角色编码</th>
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
	    var isAllowEdit = '<%=HomeAction.isAllowed("toEditRole.do",request)%>';
		if("true" === isAllowEdit){
    		html += '<a href="#" onclick="toEditRole('+ rowIndex + ');">编辑</a>';
		}
		
		var isAllowPrivilege = '<%=HomeAction.isAllowed("toAssignPrivilege.do",request)%>';
		if("true" === isAllowPrivilege){
	    	html += '&nbsp;&nbsp;&nbsp;';
	    	html += '<a href="#" onclick="toAssignPrivilege('+ rowIndex + ');">分配权限</a>';
		}
		return html;
    }
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sys/role.js"></script>
</body>
</html>