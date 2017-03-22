<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加资源</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>

</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">添加资源</li>
	</ol>
	<div class="container definewidth m20">
		<form id="addResourceForm" name="addResourceForm" action="" method="post">
			<table id="addResourceFormTable" class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft" >资源名称</td>
					<td><input type="text" id="resource_name" name="resource_name" style="width: 400px" 
						value="" data-rules="{required:true}" data-messages="{required:'资源名称不能为空'}"/></td>
					
					<td width="10%" class="tableleft" >资源类型</td>
					<td id="resourceTypeTd">
						<select id="resource_type" name="resource_type" style="width: 216px" onchange="loadMenuSelect(this)">
		          			<option value="0">功能</option>
		          			<option value="1">菜单</option>
		          			<option value="2">界面</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft">资源访问路径</td>
					<td>
					<!-- <input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="" placeholder="参考格式:/addUser.do(前缀必须为/ 后缀必须为.do)" 
						data-messages="{regexp:'前缀必须为/&nbsp;后缀必须为.do&nbsp;且前后缀之间只能为字母'}" data-rules="{regexp:/^\/([a-zA-Z]+).do$/}"/> -->
					<input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="" placeholder="参考格式:/addUser.do(前缀必须为/ 后缀必须为.do)"/>
					</td>
					<%-- <input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="" placeholder="参考格式:/addUser.do(前缀必须为/ 后缀必须为.do)" 
						data-messages="{regexp:'前缀必须为/&nbsp;&nbsp;&nbsp;&nbsp;后缀必须为.do'}" data-rules="{regexp:/^\/(.*).do$/}"/> --%>
					<!-- <input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="" placeholder="参考格式:/addUser.do(前缀必须为/ 后缀必须为.do)"/> -->
					</td>
					<%-- <td width="10%" class="tableleft" >资源编码</td>
					<td>
						<input type="text" id="resource_code" name="resource_code" style="width: 400px" 
							value="" placeholder="参考格式:resource_addUser(前缀必须为resource_)" 
							data-messages="{regexp:'前缀必须为resource_&nbsp;且前缀之后只能为字母'}" data-rules="{regexp:/^resource_([a-zA-Z]+$)/}"/>
						<input type="text" id="resource_code" name="resource_code" style="width: 400px" 
							value="" placeholder="参考格式:resource_addUser(前缀必须为resource_)" 
							data-messages="{regexp:'前缀必须为resource_'}" data-rules="{regexp:/^resource_/}"/>
						<!-- <input type="text" id="resource_code" name="resource_code" style="width: 400px" 
							value="" placeholder="参考格式:resource_addUser(前缀必须为resource_)"/> -->
					</td> --%>
					
					<td width="10%" class="tableleft" >资源描述</td>
					<td><input type="text" id="resource_desc" name="resource_desc" style="width: 400px" 
						value="" data-rules="{required:true}" data-messages="{required:'资源描述不能为空'}"/></td>
				</tr>
				<tr id="isShowMenuCode" style="display:none">
					<td width="10%" class="tableleft" >菜单编码</td>
					<td colspan="3">
						<input type="text" id="menu_code" name="menu_code" style="width: 400px" value="" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft">父级资源</td>
					<td colspan="3">
						<select id="father_resource" style="float: left;display: inline;width:200px;" name="father_resource" onchange="loadResourcesList(this)">
		          			<option value="" >请选择</option>
		     			</select>
		     			<select id="son_resource" style="float: left;display: inline;width:200px;" name="son_resource" onchange="loadResourcesList(this)">
		          			<option value="" >请选择</option>
		     			</select>
		     			<select id="sun_resource" style="float: left;display: inline;width:200px;" name="sun_resource">
		          			<option value="" >请选择</option>
		     			</select>
					</td>
				</tr>
				<tr>
					<td  colspan="4" style=" text-align: center;">
						<button type="button" class="btn btn-success" onclick="return addResourceSubmit();">添加</button>
						&nbsp;&nbsp;
						<button type="reset" class="btn btn-success">重置</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-success" id="go_back">返回</button>
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
	src="<%=request.getContextPath()%>/resources/js/sys/resources.js"></script>

<script type="text/javascript">
	var menuData;//保存菜单数据
	var resourceData;//资源数据
	$(function () {
	
		$('#go_back').click(function(){
				window.location.href="<%=request.getContextPath()%>/toResourcesListPage.do";
		});
		
		//初始化资源一级菜单
		var sendData = {'parent_resource':-1};
		forumConsole.ajaxCall('POST',contextPath+"/queryResourceList.do",true,sendData,'json',function(data){
			resourceData = data.data;
			var resourceList = $("#father_resource");
			resourceList.empty();//清空select下拉框
			var tmpOptOne = $("<option>");
			tmpOptOne.val("");
			tmpOptOne.text("请选择");
			resourceList.append(tmpOptOne);
	    	for(var i=0;i<resourceData.length;i++){
		    	var tmpOpt = $("<option>");
		    	var tmpOptValue = resourceData[i].resource_id;
		    	tmpOpt.val(tmpOptValue);
		    	tmpOpt.text(resourceData[i].resource_name);
		    	resourceList.append(tmpOpt);
		    }
		},function(){},function(){
			BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
		});
		
	});
	
	<%-- var resourceCodeField = addResourceForm.getField('resource_code');
	resourceCodeField.set('remote',{
       url : "<%=request.getContextPath()%>/resourceDataChecked.do",
       dataType:'json',//默认为字符串
       callback : function(data){
	         if(data.success){
	          	return '';
	         }else{
	          	return data.msg;
	         }
       }
    }); --%>
	
	var resourceUrlField = addResourceForm.getField('resource_url');
	resourceUrlField.set('remote',{
       url : "<%=request.getContextPath()%>/resourceDataChecked.do",
       dataType:'json',//默认为字符串
       callback : function(data){
	         if(data.success){
	          	return '';
	         }else{
	          	return data.msg;
	         }
       }
    });
	
	function addResourceSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			window.location.href="<%=request.getContextPath()%>/toResourcesListPage.do";
		}else{
			BUI.Message.Alert(data.res_msg,'warning');
			return;
		}
	}
	
	//加载级联菜单
	function loadResourcesList(_this){
		var selectValue = _this.value;
		var id = _this.id; //father_resource:一级菜单  son_resource:二级菜单 sun_resource:三级菜单
		var commonId = "";
		var selectHtml = "";
		// 如果点击为father_resource 则显示son_resource ...
    	if(id == "father_resource"){
    		commonId = $("#son_resource");
			selectHtml = "<select id=\"son_resource\" name=\"son_resource\" ></select>";
    	}else if(id == "son_resource"){
    		commonId = $("#sun_resource");
    		selectHtml = "<select id=\"sun_resource\" name=\"sun_resource\" ></select>";
    	}
    	commonId.append(selectHtml);
    	if(null != selectValue && "" != selectValue && typeof(selectValue) != 'undefined'){
    		var sendData = {'parent_resource':selectValue};
    		//菜单下拉列表
    		forumConsole.ajaxCall('POST',contextPath+"/queryResourceList.do",true,sendData,'json',function(data){
    			resourceData = data.data;
    			var resourceList = commonId;
    			resourceList.empty();//清空select下拉框
    			var tmpOptOne = $("<option>");
    			tmpOptOne.val("");
    			tmpOptOne.text("请选择");
    			resourceList.append(tmpOptOne);
    	    	for(var i=0;i<resourceData.length;i++){
    		    	var tmpOpt = $("<option>");
    		    	var tmpOptValue = resourceData[i].resource_id;
    		    	tmpOpt.val(tmpOptValue);
    		    	tmpOpt.text(resourceData[i].resource_name);
    		    	resourceList.append(tmpOpt);
    		    }
    		},function(){},function(){
    			BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
    		});
    	}
	}
	
	//加载菜单下拉列表
	function loadMenuSelect(_this){
		var selectValue = _this.value;
		if(selectValue == "1"){//菜单
			$("#isShowMenuCode").show();
			$("#resource_url").attr("readonly","readonly");
			
			var selectHtml = "<select id=\"menu_id\" name=\"menu_id\" style=\"width:200px\" onchange=\"setMenuCode(this)\"></select>";
	    	$("#resourceTypeTd").append(selectHtml);
	    	var sendData = null;
			//菜单下拉列表
			forumConsole.ajaxCall('POST',contextPath+"/queryMenuSelectList.do",true,sendData,'json',queryMenuSelectListSuccess,queryMenuSelectListComplete,queryMenuSelectListError);
			
		}else{
			$("#menu_id").remove();
			$("#resource_url").removeAttr("readonly");
			$("#resource_url").val("");
			$("#menu_code").val("");
			$("#isShowMenuCode").hide();
		}
	}
	
	function queryMenuSelectListSuccess(data){
		menuData = data.data;
		var menuList = $("#menu_id");
		menuList.empty();//清空select下拉框
		var tmpOptOne = $("<option>");
		tmpOptOne.val("");
		tmpOptOne.text("请选择菜单");
		menuList.append(tmpOptOne);
    	for(var i=0;i<menuData.length;i++){
	    	var tmpOpt = $("<option>");
	    	var tmpOptValue = menuData[i].menu_id;
	    	tmpOpt.val(tmpOptValue);
	    	tmpOpt.text(menuData[i].menu_name);
	    	menuList.append(tmpOpt);
	    }
    	
	}
	function queryMenuSelectListComplete(){
	}
	function queryMenuSelectListError(){
		 alert("queryMenuSelectList[ajax请求出错了]!");
	}
	
	//根据菜单选项设置对应菜单编码到资源编码input中
	function setMenuCode(_this){

		var selectValue = _this.value;
		if(selectValue == ""){//如果是 "请选择菜单" 则清空resource_url,menu_code
			$("#resource_url").val("");
			$("#menu_code").val("");
		}else{
			for(var i=0; i<menuData.length; i++){
				if(menuData[i].menu_id == parseInt(selectValue) ){
					if(menuData[i].menu_url == "#"){
						$("#resource_url").val(menuData[i].menu_code);
						$("#menu_code").val(menuData[i].menu_code);
					}else{
						$("#resource_url").val("/" + menuData[i].menu_url);
						$("#menu_code").val(menuData[i].menu_code);
					}
					break;
				}
			}
			$('#resource_url').triggerHandler("change");
		}
	}
</script>
</body>
</html>