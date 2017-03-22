<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>

<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">用户列表</li>
	</ol>
	
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td class="tableleft">登录名</td>
					<td><input type="text" id="u_login_name" name="u_login_name" value="" /></td>

					<td class="tableleft">用户名称</td>
					<td><input type="text" id="u_user_name" name="u_user_name" value="" /></td>
					<td class="tableleft">手机号码</td>
					<td><input type="text" id="u_mobile"  name="u_mobile" value=""/></td>

					<td class="tableleft">用户ID</td>
					<td><input type="text" id="u_user_id" name="u_user_id" value="" data-rules="{number:true}"/> </td>
					<td class="tableleft">用户编码</td>
					<td><input type="text" id="u_user_code"  name="u_user_code" value=""/></td>

					<td class="tableleft">状态</td>
					<td>
						<select id="u_status" name="u_status" >
							<option value="">全部</option>
		          			<option value="0">已启用</option>
		          			<option value="1">已禁用</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="11">
					    <security:authorize url="queryUserList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryUserList();">查询</button>
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
	       		<%-- <security:authorize url="toUserRoleConfigPage.do">
	       		<button type="button" class="btn btn-warning" id="toUserRoleConfig" onclick="toUserRoleConfig()">分配角色</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize> --%>
				<security:authorize url="toAddUserPage.do">
	       		<button type="button" class="btn btn-warning" id="toAddUser" onclick="toAddUser()">添加用户</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<%-- <security:authorize url="toUpdateUserPage.do">
	       		<button type="button" class="btn btn-warning" id="toUpdateUser" onclick="toUpdateUser()">编辑用户</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize> --%>
				<security:authorize url="deleteUser.do">
				<button type="button" class="btn btn-warning" id="batchDeleteUser" onclick="batchDeleteUser();">删除用户</button>
				</security:authorize>
	       </div>
	</div>
	
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="u_user_id" width="3%"></th>
			    <th w_index="u_user_name">用户名称</th>
				<th w_index="u_login_name">登录名</th>
				<!-- <th w_index="u_status">状态 </th> -->
				<th w_render="renderStatus">状态 </th>
				<th w_index="u_mobile" width="135px">手机号</th>
				<th w_index="u_user_code">用户编码</th>
				<th w_index="crt_date" width="135px">创建时间 </th>
				<th w_index="mod_date" width="135px">修改时间 </th>
				<th w_index="u_remark">备注</th>
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
    	var isAllowEdit = '<%=HomeAction.isAllowed("toUpdateUserPage.do",request)%>';
    	if("true" === isAllowEdit){
			html = '<a href="#" onclick="toUpdateUser(\''
					+ gridObj.getRecordIndexValue(record, 'u_user_id')
					+ '\');">编辑</a>';
			html += '&nbsp;&nbsp;&nbsp;';
			html += '<a href="#" onclick="resetUserPassword('+ rowIndex + ');">重置密码</a>';
			html += '&nbsp;&nbsp;&nbsp;';
    	}
		
    	var isAllowAllocation = '<%=HomeAction.isAllowed("toUserRoleConfigPage.do",request)%>';
    	if("true" === isAllowAllocation){
			html += '<a href="#" onclick="toUserRoleConfig('+ rowIndex + ');">分配角色</a>';
    	}
		return html;
	}
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.md5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/sys/user.js"></script>
</body>
</html>