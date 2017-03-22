<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改用户信息</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>

</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">修改用户信息</li>
	</ol>
	<div class="container definewidth m20">
		<form id="updateUserForm" name="updateUserForm" action="" method="post">
			<input type="hidden" id="u_user_id" name="u_user_id" value="${userBean.u_user_id}">
			<table id="updateUserFormTable" class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">用户名</label></td>
					<td><input type="text" id="u_user_name" name="u_user_name" style="width: 400px" 
						value="${userBean.u_user_name}" data-rules="{required : true}" data-messages="{required:'用户名不能为空'}"/></td>

					<td width="10%" class="tableleft" ><label class="control-label">登录名</label></td>
					<td colspan="4">${userBean.u_login_name}</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">手机号码</label></td>
					<td><input type="text" id="u_mobile" name="u_mobile" style="width: 400px" 
						value="${userBean.u_mobile}" data-messages="{regexp:'请正确输入手机号码'}"
     					data-rules="{regexp:/^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$/}"/></td>
					
					<td width="10%" class="tableleft" ><label class="control-label">用户编码</label></td>
					<td colspan="4">${userBean.u_user_code}</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">外部系统用户</label></td>
					<td width="50%">
						<select class="pull-left" id="ext_sys_id" name="ext_sys_id" style="width: 212px"  onchange="setExtSysUser(this)">
		          			<option value="0" ${userBean.ext_sys_id eq "0" ? "selected":""}>请选择外部系统</option>
		          			<option value="1" ${userBean.ext_sys_id eq "1" ? "selected":""}>圈子管理</option>
		     			</select>
		     			<input class="pull-left" type="text" name="ext_sys_object_id" value="${userBean.ext_sys_object_id}" placeholder="请输入用户id" onchange="changeExtUser();"  id="ext_sys_object_id" />
						<input type="hidden" name="ext_sys_object_value" value="${userBean.ext_sys_object_value}" id="ext_sys_object_value" />
		     			<div class="pull-left" id="ext_user" style="width:350px;height:25px;">
						</div>
					</td>
					
					<td width="10%" class="tableleft" ><label class="control-label">状态</label></td>
					<td>
						<select id="u_status" name="u_status">
		          			<option value="0" ${userBean.u_status eq "0" ? "selected":""}>启用</option>
		          			<option value="1" ${userBean.u_status eq "1" ? "selected":""}>禁用</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft"><label class="control-label">备注</label></td>
					<td colspan="5">
					<textarea id="u_remark"  name="u_remark" style="width:400px;height:50px;"
					data-rules="{required:true}" data-messages="{required:'备注不能为空'}">${userBean.u_remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="5" style=" text-align: center;">
						<button type="button" class="btn btn-success" onclick="return updateUserSubmit();">修改</button>
						&nbsp;&nbsp;
						<button type="reset" class="btn btn-success">重置</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-success"
							id="go_back">返回</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
var contextPath;
$(function () {
	contextPath = '<%=request.getContextPath()%>';
});
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/sys/user.js"></script>

<script type="text/javascript">
	var extSysUserData;
	$(function () {
	
		$('#go_back').click(function(){
				window.location.href="<%=request.getContextPath()%>/toUserListPage.do";
		});
		var ext_sys_id = $("#ext_sys_id").val();
		if(parseInt(ext_sys_id) == 1){
			$("#ext_sys_object_id").removeAttr("disabled");
		}else if(parseInt(ext_sys_id) == 0){
			$("#ext_sys_object_id").attr("disabled",true);
		}
	});
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/forum/info/jquery.form.js"></script>
</body>
</html>