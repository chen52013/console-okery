<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加用户</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">添加用户</li>
	</ol>
	<div class="container definewidth m20">
		<form id="addUserForm" name="addUserForm" action="" method="post">
			<input type="hidden" id="ext_sys_object_value" name="ext_sys_object_value" value="">
			<table id="addUserFormTable" class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">用户名称</label></td>
					<td><input type="text" id="u_user_name" name="u_user_name" style="width: 400px" 
						value="" data-rules="{required : true}" data-messages="{required:'用户名不能为空'}"/></td>

					<td width="10%" class="tableleft" ><label class="control-label">登录名</label></td>
					<td colspan="3"><input type="text" id="u_login_name" name="u_login_name" style="width: 400px" 
						value="" data-rules="{required : true}" data-messages="{required:'登录名不能为空'}"
						onkeyup="this.value=this.value.replace(/\W/g,'')" onafterpaste="this.value=this.value.replace(/\W/g,'')"/></td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">手机号码</label></td>
					<td><input type="text" id="u_mobile" name="u_mobile" style="width: 400px" 
						value="" data-messages="{regexp:'请正确输入手机号码'}"
     					data-rules="{regexp:/^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$/}"/></td>
					
					<td width="10%" class="tableleft" ><label class="control-label">登录密码</label></td>
					<td colspan="3"><input type="text" id="u_login_pwd" name="u_login_pwd" style="width: 400px" 
						value="" data-rules="{required:true}" data-messages="{required:'登录密码不能为空'}"/></td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" ><label class="control-label">用户编码</label></td>
					<td><input type="text" id="u_user_code" name="u_user_code" style="width: 400px" 
						value="" data-rules="{required : true}" data-messages="{required:'用户编码不能为空'}"
						onkeyup="this.value=this.value.replace(/\W/g,'')" onafterpaste="this.value=this.value.replace(/\W/g,'')"/></td>
						
					<td width="10%" class="tableleft" ><label class="control-label">状态</label></td>
					<td>
						<select id="u_status" name="u_status" style="width: 425px">
		          			<option value="0">启用</option>
		          			<option value="1">禁用</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<!-- <td width="10%" class="tableleft" >外部系统用户</td>
					<td>
						<select id="ext_sys_id" name="ext_sys_id" style="width: 212px"  onchange="setExtSysUser(this)">
		          			<option value="0">请选择外部系统</option>
		          			<option value="1">圈子管理</option>
		     			</select>
		     			<input type="text" name="ext_sys_object_id" value="" onclick="changeUser();" placeholder="请输入用户id"  id="ext_sys_object_id" />
		     			<div class="pull-left" id="ext_user" style="width:300px;height:25px;">
						</div>
					</td> -->
					<td width="10%" class="tableleft" ><label class="control-label">外部系统用户</label></td>
					<td width="50%" colspan="3">
						<select class="pull-left" id="ext_sys_id" name="ext_sys_id" style="width: 212px"  onchange="setExtSysUser(this)">
		          			<option value="0" ${userBean.ext_sys_id eq "0" ? "selected":""}>请选择外部系统</option>
		          			<option value="1" ${userBean.ext_sys_id eq "1" ? "selected":""}>圈子管理</option>
		     			</select>
		     			<input class="pull-left" type="text" name="ext_sys_object_id" value="" placeholder="请输入用户id" onchange="changeUser();"  id="ext_sys_object_id" />
		     			<div class="pull-left" id="ext_user" style="width:300px;height:25px;">
						</div>
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
					<td  colspan="5" style=" text-align: center;">
						<button type="button" class="btn btn-success" onclick="return addUserSubmit();">添加</button>&nbsp;&nbsp;
						<button type="reset" class="btn btn-success">重置</button>&nbsp;&nbsp;
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.md5.js"></script>
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
	
	var userCodeField = addUserForm.getField('u_user_code');
	userCodeField.set('remote',{
       url : "<%=request.getContextPath()%>/userDataChecked.do",
       dataType:'json',//默认为字符串
       callback : function(data){
	         if(data.success){
	          	return '';
	         }else{
	          	return data.msg;
	         }
       }
    });
	
	var loginNameField = addUserForm.getField('u_login_name');
	loginNameField.set('remote',{
       url : "<%=request.getContextPath()%>/userDataChecked.do",
       dataType:'json',//默认为字符串
       callback : function(data){
	         if(data.success){
	          	return '';
	         }else{
	          	return data.msg;
	         }
       }
    });
	
	//根据外部系统设置外部系统用户
	function setExtSysUser(_this){
		var selectValue = parseInt(_this.value);
		if(selectValue == 1){
			$("#ext_sys_object_id").removeAttr("disabled");
			
		}else if(selectValue == 0){
			$("#ext_sys_object_id").attr("disabled",true);
		}
	}
	
	function changeUser(){
		var u_user_id = $("#ext_sys_object_id").val();
		if("" == u_user_id || null == u_user_id || typeof(u_user_id) == undefined){
			BUI.Message.Alert("用户id不能为空!",'warning');
			$("#ext_user").html("");
			return false;
		}
		var ext_sys_id = $("#ext_sys_id").val();
		if("" == ext_sys_id || null == ext_sys_id || typeof(ext_sys_id) == undefined){
			BUI.Message.Alert("外部系统用户不能为空!",'warning');
			$("#ext_user").html("");
			return false;
		}
		var sendData = {
			'ext_sys_id':ext_sys_id,
			'u_user_id':u_user_id
		};
		if("" != u_user_id && null != u_user_id || typeof(u_user_id) != undefined && u_user_id > 0){
			forumConsole.ajaxCall('POST',"<%=request.getContextPath()%>/queryExtSysUserSelectList.do",true,sendData,'json',queryExtSysUserSelectListSuccess,queryExtSysUserSelectListComplete,queryExtSysUserSelectListError);
		}
		function queryExtSysUserSelectListSuccess(data){
			if(null == data || data == undefined){
    			BUI.Message.Alert("请求失败了!",'warning');
    			$("#ext_user").html("");
    			return;
    		}else{
				extSysUserData = data.data;
				if(extSysUserData.length > 0){
					for(var i = 0;i<extSysUserData.length;i++){
						$("#ext_user").html("&nbsp;&nbsp;用户名：<span style=\"height:30px;line-height:30px;\" id=\"u_user_name\" name=\"u_user_name\">"+extSysUserData[i].nick_name+"</span>"
								+"&nbsp;&nbsp;&nbsp;&nbsp;手机号码：<span style=\"height:30px;line-height:30px;\" id=\"u_user_mobile\" name=\"u_user_mobile\">"+extSysUserData[i].user_mobile+"</span>");
					}
				}else{
					BUI.Message.Alert("该用户不存在！",'warning');
					$("#ext_user").html("");
				}
				
    		}
		}
		function queryExtSysUserSelectListComplete(){
			
		}
		function queryExtSysUserSelectListError(){
			 alert("queryExtSysUserSelectList[ajax请求出错了]!");
		}
	}
</script>
</body>
</html>