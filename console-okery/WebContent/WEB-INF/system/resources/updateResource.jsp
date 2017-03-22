<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改资源</title>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>

</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
		<li class="active">修改资源</li>
	</ol>
	<div class="container definewidth m20">
		<form id="updateResourceForm" name="updateResourceForm" action="" method="post">
			<input type="hidden" id="resource_id" name="resource_id" value="${resourceBean.resource_id}">
			<table id="updateResourceFormTable" class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft" >资源名称</td>
					<td><input type="text" id="resource_name" name="resource_name" style="width: 400px" 
						value="${resourceBean.resource_name}" data-rules="{required:true}" data-messages="{required:'资源名称不能为空'}"/></td>
					
					<td width="10%" class="tableleft" >资源类型</td>
					<td>
						<select id="resource_type" name="resource_type" style="width: 425px">
		          			<option value="0" ${resourceBean.resource_type eq "0" ? "selected":""}>功能</option>
		          			<option value="1" ${resourceBean.resource_type eq "1" ? "selected":""}>菜单</option>
		          			<option value="2" ${resourceBean.resource_type eq "2" ? "selected":""}>界面</option>
		     			</select>
		     			<%-- <input type="text" id="resource_type" name="resource_type" style="width: 400px" value="${resourceBean.resource_type}" readonly="readonly"/> --%>
					</td>
				</tr>
				<tr>
					<td width="10%" class="tableleft" >资源编码</td>
					<td>
						<%-- <input type="text" id="resource_code" name="resource_code" style="width: 400px" 
							value="${resourceBean.resource_code}" placeholder="参考格式:resource_addUser(前缀必须为resource_)" 
							data-messages="{regexp:'前缀必须为resource_'}" data-rules="{regexp:/^resource_/}"/> --%>
						<input type="text" id="resource_code" readonly="readonly" name="resource_code" style="width: 400px" 
							value="${resourceBean.resource_code}"/>
					</td>
					
					<td width="10%" class="tableleft" >资源描述</td>
					<td><input type="text" id="resource_desc" name="resource_desc" style="width: 400px" 
						value="${resourceBean.resource_desc}" data-rules="{required:true}" data-messages="{required:'资源描述不能为空'}"/></td>
				</tr>
				<tr>
					<td width="10%" class="tableleft">资源访问路径</td>
					<td>
						<%-- <input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="${resourceBean.resource_url}" placeholder="参考格式:/addUser.do(前缀必须为/ 后缀必须为.do)" 
						data-messages="{regexp:'前缀必须为/&nbsp;&nbsp;&nbsp;&nbsp;后缀必须为.do'}" data-rules="{regexp:/^\/(.*).do$/}"/> --%>
						<input type="text" id="resource_url" name="resource_url" style="width: 400px" 
						value="${resourceBean.resource_url}"/>
					</td>
					<td width="10%" class="tableleft">父级资源</td>
					<input id="parent_resource" name="parent_resource" type="hidden" value="${resourceBean.parent_resource}" />
					<td>
						<select id="father_resource" style="float: left;display: inline;width:200px;" name="father_resource" onchange="loadResourcesList(this)">
		          			<option value="" >请选择</option>
		     			</select>
		     			<select id="son_resource" style="float: left;display: inline;width:200px;" name="son_resource" onchange="loadResourcesList(this)">
		          			<option value="" >请选择</option>
		     			</select>
		     			<select id="sun_resource" style="float: left;display: inline;width:200px;" name="sun_resource">
		          			<option value="" >请选择</option>
		     			</select>
		     			<!-- <select id="resource" style="float: left;display: inline;width:200px;" name="sun_resource">
		          			<option value="" >请选择</option>
		     			</select> -->
					</td>
				</tr>
				<tr>
					<td  colspan="4" style=" text-align: center;">
						<button type="button" class="btn btn-success" onclick="return updateResourceSubmit();">修改</button>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/sys/resources.js"></script>

<script type="text/javascript">
	var menuData;//保存菜单数据
	var resourceData;//资源数据
	
	$(function () {
		$('#go_back').click(function(){
				window.location.href="<%=request.getContextPath()%>/toResourcesListPage.do";
		});
	});
	
	//拿到当前资源的resource_id parent_resource 没有则为0
	var parent_resource = $("#parent_resource").val();
	var resource_id = $("#resource_id").val();
	if(null == parent_resource || "" == parent_resource || typeof(parent_resource) == 'undefined'){
		parent_resource = 0;
	}
	
	var father_resource_id = "";		//父级资源id
	var father_parent_resource = "";	//父级资源
	var son_resource_id = "";			//子级资源id
	var son_parent_resource = "";		//子级资源
	var sun_resource_id = "";			//孙级资源id
	var sun_parent_resource = "";		//孙级资源
	
	var one_resource_id;				//一级资源id
	var one_parent_resource;			//一级资源
	var two_resource_id;				//二级资源id
	var two_parent_resource;			//二级资源
	var three_resource_id;				//三级资源id
	var three_parent_resource;			//三级资源
	var four_resource_id;				//四级资源id
	var four_parent_resource;			//四级资源
	
	//通过资源ID查找相对应上级资源
	var sendData = {'resource_id':parent_resource};
	forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sendData,'json',function(data){
		var sunData = data.data;
		if(null != sunData && "" != sunData && typeof(sunData) != 'undefined'){
			sun_resource_id = sunData[0].resource_id;
			sun_parent_resource = sunData[0].parent_resource;
			resort(father_resource_id, father_parent_resource, son_resource_id, son_parent_resource, sun_resource_id, sun_parent_resource, resource_id, parent_resource);
			loadResourceSelect(one_resource_id, one_parent_resource, two_resource_id, three_resource_id);
			//通过上级资源ID查找相对应上上级资源
			var sun_sendData = {'resource_id':sun_parent_resource};
			forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sun_sendData,'json',function(data){
				var sonData = data.data;
				if(null != sonData && "" != sonData && typeof(sonData) != 'undefined'){
					son_resource_id = sonData[0].resource_id;
					son_parent_resource = sonData[0].parent_resource;
					resort(father_resource_id, father_parent_resource, son_resource_id, son_parent_resource, sun_resource_id, sun_parent_resource, resource_id, parent_resource);	
					loadResourceSelect(one_resource_id, one_parent_resource, two_resource_id, three_resource_id);
					//通过上上级资源ID查找相对应上上上级资源
					var son_sendData = {'resource_id':son_parent_resource};
					forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,son_sendData,'json',function(data){
						var fatherData = data.data;
						if(null != fatherData && "" != fatherData && typeof(fatherData) != 'undefined'){
							father_resource_id = fatherData[0].resource_id;
							father_parent_resource = fatherData[0].parent_resource;
							resort(father_resource_id, father_parent_resource, son_resource_id, son_parent_resource, sun_resource_id, sun_parent_resource, resource_id, parent_resource);
							loadResourceSelect(one_resource_id, one_parent_resource, two_resource_id, three_resource_id);
						}
					},function(){},function(){
						BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
					});
				}
			},function(){},function(){
				BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
			});
		}else{
			loadResourceSelect(resource_id, parent_resource, two_resource_id, three_resource_id);
		}
	},function(){},function(){
		BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
	});
	
	//重新排序
	function resort(father_resource_id,father_parent_resource,son_resource_id,son_parent_resource,sun_resource_id,sun_parent_resource,resource_id,parent_resource){
		if(null != father_resource_id && "" != father_resource_id && typeof(father_resource_id) != 'undefined'){
			//第4个 例子： 《相对应 不唯一》
			//1:{father_parent_resource:圈子系统}
			//2:{son_parent_resource:资讯管理} 
			//3:{sun_parent_resource:文章管理} 
			//4:{parent_resource:查询文章} 
			//如果sun_parent_resource存在则说明他是第4个  也就是一级大菜单
			one_resource_id = father_resource_id;
			one_parent_resource = father_parent_resource;
			
			two_resource_id = son_resource_id;
			two_parent_resource = son_parent_resource;
			
			three_resource_id = sun_resource_id;
			three_parent_resource = sun_parent_resource;
			
			four_resource_id = resource_id;
			four_parent_resource = parent_resource;
		}else{
			if(null != son_resource_id && "" != son_resource_id && typeof(son_resource_id) != 'undefined'){
				one_resource_id = son_resource_id;
				one_parent_resource = son_parent_resource;
				
				two_resource_id = sun_resource_id;
				two_parent_resource = sun_parent_resource;
				
				three_resource_id = resource_id;
				three_parent_resource = parent_resource;
			}else{
				if(null != sun_resource_id && "" != sun_resource_id && typeof(sun_resource_id) != 'undefined'){
					one_resource_id = sun_resource_id;
					one_parent_resource = sun_parent_resource;
					
					two_resource_id = resource_id;
					two_parent_resource = parent_resource;
				}else{
					one_resource_id = resource_id;
					one_parent_resource = parent_resource;
				}
			}
		}
	}
	
	//加载级联菜单
	function loadResourceSelect(one_resource_id,one_parent_resource,two_resource_id,three_resource_id){
		//获取一级菜单
		if(null != one_resource_id && "" != one_resource_id && typeof(one_resource_id) != 'undefined'){
			sendData = {'parent_resource':one_parent_resource};
			forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sendData,'json',function(data){
				//通过parent_resource查找相所有父级资源
				var oneData = data.data;
				if(null != oneData && "" != oneData && typeof(oneData) != 'undefined'){
					var oneResourceList = $("#father_resource");
					oneResourceList.empty();//清空select下拉框
					var tmpOptOne = $("<option>");
					tmpOptOne.val("");
	    			tmpOptOne.text("请选择");
	    			oneResourceList.append(tmpOptOne);
					for(var i=0;i<oneData.length;i++){
	    		    	var tmpOpt = $("<option>");
						if(one_resource_id == oneData[i].resource_id){
	   		    			tmpOpt.val(oneData[i].resource_id);
	   	    		    	tmpOpt.text(oneData[i].resource_name);
	   	    		    	tmpOpt.attr("selected",true);
	   		    		}else{
	   		    			tmpOpt.val(oneData[i].resource_id);
	   	    		    	tmpOpt.text(oneData[i].resource_name);
	   		    		}
						oneResourceList.append(tmpOpt);
	    		    }
				}
			},function(){},function(){
				BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
			});
		}
		
		//获取二级菜单
		if(null != two_resource_id && "" != two_resource_id && typeof(two_resource_id) != 'undefined'){
			sendData = {'parent_resource':one_resource_id};
			forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sendData,'json',function(data){
				//通过parent_resource查找相所有父级资源
				var twoData = data.data;
				if(null != twoData && "" != twoData && typeof(twoData) != 'undefined'){
					var twoResourceList = $("#son_resource");
					twoResourceList.empty();//清空select下拉框
					var tmpOptOne = $("<option>");
					tmpOptOne.val("");
	    			tmpOptOne.text("请选择");
	    			twoResourceList.append(tmpOptOne);
					for(var i=0;i<twoData.length;i++){
	    	    		var tmpOpt = $("<option>");
						if(two_resource_id == twoData[i].resource_id){
	   		    			tmpOpt.val(twoData[i].resource_id);
	   	    		    	tmpOpt.text(twoData[i].resource_name);
	   	    		    	tmpOpt.attr("selected",true);
	   		    		}else{
	   		    			tmpOpt.val(twoData[i].resource_id);
	   	    		    	tmpOpt.text(twoData[i].resource_name);
	   		    		}
						twoResourceList.append(tmpOpt);
	    		    }
				}
			},function(){},function(){
				BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
			});
		}
		
		//获取三级菜单
		if(null != three_resource_id && "" != three_resource_id && typeof(three_resource_id) != 'undefined'){
			sendData = {'parent_resource':two_resource_id};
			forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sendData,'json',function(data){
				//通过parent_resource查找相所有父级资源
				var threeData = data.data;
				if(null != threeData && "" != threeData && typeof(threeData) != 'undefined'){
					var threeResourceList = $("#sun_resource");
					threeResourceList.empty();//清空select下拉框
					var tmpOptOne = $("<option>");
	    			tmpOptOne.val("");
	    			tmpOptOne.text("请选择");
	    			threeResourceList.append(tmpOptOne);
	    	    	for(var i=0;i<threeData.length;i++){
	    	    		var tmpOpt = $("<option>");
						if(three_resource_id == threeData[i].resource_id){
	   		    			tmpOpt.val(threeData[i].resource_id);
	   	    		    	tmpOpt.text(threeData[i].resource_name);
	   	    		    	tmpOpt.attr("selected",true);
	   		    		}else{
	   		    			tmpOpt.val(threeData[i].resource_id);
	   	    		    	tmpOpt.text(threeData[i].resource_name);
	   		    		}
						threeResourceList.append(tmpOpt);
	    		    }
				}
			},function(){},function(){
				BUI.Message.Alert("queryResourceList[ajax请求出错了]!",'error');
			});
		}
	}
	
	
	
	var resourceCodeField = updateResourceForm.getField('resource_code');
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
    });
	
	var resourceUrlField = updateResourceForm.getField('resource_url');
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
	
	function updateResourceSuccess(data){
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
    		forumConsole.ajaxCall('POST','<%=request.getContextPath()%>'+"/queryResourceList.do",true,sendData,'json',function(data){
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
			var selectHtml = "<select id=\"menu_id\" name=\"menu_id\" style=\"width:200px\"></select>";
	    	$("#resourceTypeTd").append(selectHtml);
	    	var sendData = null;
			//菜单下拉列表
			forumConsole.ajaxCall('POST',contextPath+"/queryMenuSelectList.do",true,sendData,'json',queryMenuSelectListSuccess,queryMenuSelectListComplete,queryMenuSelectListError);
			
		}else{
			$("#menu_id").remove();
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
	    	var tmpOptValue = menuData[i].menuId;
	    	tmpOpt.val(tmpOptValue);
	    	tmpOpt.text(menuData[i].menuName);
	    	menuList.append(tmpOpt);
	    }
	}
	function queryMenuSelectListComplete(){
	}
	function queryMenuSelectListError(){
		 alert("queryMenuSelectList[ajax请求出错了]!");
	}
</script>
</body>
</html>