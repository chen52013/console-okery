<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>修改用户密码</title>
	<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
<ol class="breadcrumb  navbar-fixed-top">
	<li class="active">修改用户密码</li>
</ol>
<div class="container definewidth m20">
	<form id="userPasswordForm" name="userPasswordForm" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
		<table class="table table-bordered table-hover">
			<tr>
				<td class="tableleft"><label class="control-label">登录名：</label></td>
				<td>
					<input id="username" disabled name="username" type="text" class="input-large" value="${username}" />
				</td>
			</tr>
			<tr>
				<td class="tableleft"><label class="control-label">新密码：</label></td>
				<td>
					<input id="u_password" name="u_password" type="password" class="input-large" placeholder="请输入新密码" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style=" text-align: center;">
					<button type="button" class="btn btn-success" onclick="return changeUserPwdSubmit();">保存</button>
					&nbsp;&nbsp;
					<button type="reset" class="btn btn-warning">重新输入</button>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.md5.js"></script>
<script>
	function changeUserPwdSubmit(){

		var u_password = $("#u_password").val();
		if("" == u_password || null == u_password || typeof(u_password) == undefined){
			BUI.Message.Alert("用户密码不能为空!",'warning');
			return false;
		}
		var password = $.md5(u_password);
		var sendData = {
			'u_password':password
		};

		$("#userPasswordForm").submit(function () {
			$.ajax({
				type: "post",
				data: sendData,
				url: "<%=request.getContextPath()%>/changeUserPwd.do",
				success: changeUserSuccess,
				error: changeUserPwdError
			});
			return false;
		});
		$("#userPasswordForm").submit();
	}

	function changeUserSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			window.location.href="<%=request.getContextPath()%>/j_spring_security_logout";
		}else{
			BUI.Message.Alert(data.res_msg,'warning');
			return;
		}
	}

	function changeUserPwdError(){
		alert("changeUserPwdError请求出错了!");
	}
</script>
</body>
</html>
