<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<link href="<%=request.getContextPath()%>/resources/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">菜单列表</li>
	</ol>
	
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td class="tableleft">父菜单名</td>
					<td>
						<select id="parent_id" name="parent_id" ></select>
					</td>

					<td class="tableleft">菜单级别</td>
					<td>
						<select id="menu_level" name="menu_level">
							<option value="">全部</option>
		          			<option value="0">一级菜单</option>
		          			<option value="1">二级菜单</option>
		          			<option value="2">三级菜单</option>
		     			</select>
					</td>
					<td class="tableleft">菜单名</td>
					<td><input type="text" id="menu_name" name="menu_name" value="" /></td>

					<td class="tableleft">序号</td>
					<td><input type="text" id="menu_index" name="menu_index" value="" data-rules="{number:true}"/></td>
				</tr>
				<!-- <tr>
					<td class="tableleft">手机号码</td>
					<td><input type="text" id="u_mobile"  name="u_mobile" value=""/></td>

					<td class="tableleft">用户ID</td>
					<td><input type="text" id="u_user_id" name="u_user_id" value="" data-rules="{number:true}"/> </td>
				</tr>
				<tr>
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
				</tr> -->
				<tr>
					<td class="tableleft"></td>
					<td colspan="7">
					    <security:authorize url="${request.contextPath}/queryMenuList.do">
						<button type="button" class="btn btn-success"
							onclick="return queryMenuList();">查询</button>
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
	       		<security:authorize url="toAddMenuPage.do">
	       		<button type="button" class="btn btn-warning" id="toAddMenu" onclick="toAddMenu()">添加菜单</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize>
				<%-- <security:authorize url="toUpdateMenuPage.do">
	       		<button type="button" class="btn btn-warning" id="toUpdateMenu" onclick="toUpdateMenu()">编辑菜单</button>
				&nbsp;&nbsp;&nbsp;
				</security:authorize> --%>
				<security:authorize url="deleteMenu.do">
				<button type="button" class="btn btn-warning" id="batchDeleteUser" onclick="batchDeleteMenu();">删除菜单</button>
				</security:authorize>
	       </div>
	</div>
	
	<div class="container definewidth m10">
		<table id="searchTable">
			<tr>
			    <th w_check="true" w_index="menu_id" width="3%"></th>
			    <th w_index="menu_name">菜单名</th>
				<th w_index="menu_index">序号</th>
				<!-- <th w_index="parent_id">父菜单ID</th> -->
				<th w_render="renderParentName">父菜单名</th>
				<!-- <th w_index="home_page_id">主页ID </th> -->
				<!-- <th w_index="menu_level">菜单级别</th> -->
				<th w_render="renderMenuLevel">菜单级别</th>
				<th w_render="class_name">菜单图标</th>
				<th w_index="class_name">菜单编码</th>
				<th w_index="menu_url">菜单访问路径</th>
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
	var isAllowEdit = '<%=HomeAction.isAllowed("toUpdateMenuPage.do",request)%>';
	if("true" === isAllowEdit){
	    html = '<a href="#" onclick="toUpdateMenu(\''
			+ gridObj.getRecordIndexValue(record, 'menu_id')
			+ '\');">编辑</a>';
	}
	/*html += '&nbsp;&nbsp;&nbsp;';
	html += '<a href="#" onclick="deleteMenu('+ rowIndex + ');">删除</a>';*/
	return html;
}

function class_name(record, rowIndex, colIndex, options) {
	var html = "<i class='fa "+record.class_name+"'></i>";
	return html;
}
    
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sys/menu.js"></script>
</body>
</html>