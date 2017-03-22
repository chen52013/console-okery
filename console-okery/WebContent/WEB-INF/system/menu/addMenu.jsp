<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加菜单</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<link href="<%=request.getContextPath()%>/resources/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">添加菜单</li>
	</ol>
	<div class="container definewidth m20">
		<form id="addMenuForm" name="addMenuForm" action="" method="post">
			<table id="addMenuFormTable" class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft" >父菜单</td>
					<td>
						<select id="parent_id" name="parent_id" style="width: 425px" onchange="setMenuLevel(this)">
						</select>
					</td>

					<td width="10%" class="tableleft" >菜单级别</td>
					<td>
						<input type="hidden" id="menu_level" name="menu_level" value="0">
						<select id="menu_level_select" name="menu_level_select" style="width: 425px" onchange="setMenuUrl(this)" disabled="disabled">
		          			<option value="0">一级菜单</option>
		          			<option value="1">二级菜单</option>
		          			<!-- <option value="2">三级菜单</option> -->
		     			</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" >菜单名</td>
					<td><input type="text" id="menu_name" name="menu_name" style="width: 400px" 
						value="" data-rules="{required:true}" data-messages="{required:'菜单名不能为空'}"/></td>
						
					<td width="10%" class="tableleft" >序号</td>
					<td><input type="text" id="menu_index" name="menu_index" style="width: 400px" 
						value="" data-rules="{required:true,number:true}" data-messages="{required:'序号不能为空'}"/></td>
				</tr>
				<!-- <tr>
					<td width="10%" class="tableleft" >菜单级别</td>
					<td><input type="text" id="menu_level" name="menu_level" style="width: 400px" 
						value="" data-rules="{number:true}"/></td>
					<td width="10%" class="tableleft" >菜单编码</td>
					<td><input type="text" id="menu_code" name="menu_code" style="width: 400px" 
						value="" data-rules="{required : true}" data-messages="{required:'用户编码不能为空'}"
						onkeyup="this.value=this.value.replace(/\W/g,'')" onafterpaste="this.value=this.value.replace(/\W/g,'')"/></td>
				</tr> -->
				<tr>
					<td width="10%" class="tableleft" >菜单访问路径</td>
					<td><input type="text" id="menu_url" name="menu_url" style="width: 400px" 
						 data-rules="{required:true}" data-messages="{required:'菜单访问路径不能为空'}"/></td>
					<td width="10%" class="tableleft" >菜单图标</td>
					<td>
						<select id="class_name" name="class_name" style="width: 425px;">
		          			<option value="fa-flask">fa-flask</option>
		          			<option value="fa-bar-chart-o">fa-bar-chart-o</option>
		          			<option value="fa-envelope">fa-envelope</option>
		          			<option value="fa-edit">fa-edit</option>
		          			<option value="fa-desktop">fa-desktop</option>
		          			<option value="fa-table">fa-table</option>
		          			<option value="fa-picture-o">fa-picture-o</option>
		          			<option value="fa-cutlery">fa-cutlery</option>
		          			<option value="fa-home">fa-home</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td  colspan="4" style=" text-align: center;">
						<button type="button" class="btn btn-success" onclick="return addMenuSubmit();">添加</button>&nbsp;&nbsp;
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/sys/menu.js"></script>

<script type="text/javascript">
	var parentMenuData;		
	$(function () {
	
		$('#go_back').click(function(){
				window.location.href="<%=request.getContextPath()%>/toMenuListPage.do";
		});
		
		var sendData = null;
		//父菜单名下拉列表
		forumConsole.ajaxCall('POST',contextPath+"/queryParentMenuSelectList.do",true,sendData,'json',queryParentMenuSelectListSuccess,queryParentMenuSelectListComplete,queryParentMenuSelectListError);
		function queryParentMenuSelectListSuccess(data){
			parentMenuData = data.data;
	    	var parentMenuList = $("#parent_id");
	    	
	    	parentMenuList.empty();//清空select下拉框
	    	var tmpOptOne = $("<option>");
	    	tmpOptOne.val("-1");
	    	tmpOptOne.text("无");
	    	parentMenuList.append(tmpOptOne);
	    	for(var i=0;i<parentMenuData.length;i++){
		    	var tmpOpt = $("<option>");
		    	tmpOpt.val(parentMenuData[i].menu_id);
		    	tmpOpt.text(parentMenuData[i].menu_name);
		    	parentMenuList.append(tmpOpt);
		    }
		}
		function queryParentMenuSelectListComplete(){
		}
		function queryParentMenuSelectListError(){
			 alert("queryParentMenuSelectList[ajax请求出错了]!");
		}
	});
	
	<%-- var userCodeField = addUserForm.getField('u_user_code');
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
    }); --%>
	
    //根据父菜单设置新增菜单的菜单级别
    function setMenuLevel(_this){
    	var selectValue = _this.value;
    	var selectedParentMenuData;//所选父菜单
    	var currentMenuLevel;
    	if(parseInt(selectValue) != -1){//判断父菜单是否选择的 无,无父菜单时菜单级别为一级。
    		for(var i=0; i<parentMenuData.length; i++){
        		if(parseInt(selectValue) == parseInt(parentMenuData[i].menu_id)){
        			selectedParentMenuData = parentMenuData[i];
        			currentMenuLevel = parseInt(parentMenuData[i].menu_level) + 1; 
        			break;
        		}
        	}
        	$("#menu_level_select").val(currentMenuLevel);
        	var menu_level_select = $("#menu_level_select").val();
    		$("#menu_level").val(menu_level_select);
        	/* $("#menu_level_select").attr("disabled",true); */
        	$('#menu_level_select').triggerHandler("change");
    	}else if(parseInt(selectValue) == -1){
    		$("#menu_level_select").val("0");
    		var menu_level_select = $("#menu_level_select").val();
    		$("#menu_level").val(menu_level_select);
    		/* $("#menu_level_select").attr("disabled",true); */
    		$('#menu_level_select').triggerHandler("change");
    	}
    }    
    //根据菜单级别设置菜单访问路径(如果菜单级别为一级和二级,则菜单访问路径为#)
    function setMenuUrl(_this){
    	var selectValue = parseInt(_this.value);
    	if(selectValue == 0 || selectValue == 1){
    		$("#menu_url").val("");
    		$('#menu_url').triggerHandler("change");
    	}else{
    		$("#menu_url").val("");
    		$('#menu_url').triggerHandler("change");
    	}
    }
    
	function addMenuSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			window.location.href="<%=request.getContextPath()%>/toMenuListPage.do";
		}else{
			BUI.Message.Alert(data.res_msg,'warning');
			return;
		}
	}
</script>
</body>
</html>