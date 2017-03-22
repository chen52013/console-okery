	var searchForm;//查询form
	var addUserForm;//添加用户form
	var updateUserForm;//修改用户form
	var userRoleConfigDialog;//用户角色配置dialog
	
	//查询表单渲染
	BUI.use(['bui/form'],function(Form){
		
		searchForm = new Form.HForm({
	    	srcNode : "#searchForm",
	  	});
		searchForm.render();
		
		addUserForm = new Form.HForm({
	    	srcNode : "#addUserForm",
	  	});
		addUserForm.render();
		
		updateUserForm = new Form.HForm({
	    	srcNode : "#updateUserForm",
	  	});
		updateUserForm.render();
	});
	
	function init(webContextPath) {
		$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryUserList.do','json',10);
		userRoleConfigDialog = forumConsole.genDialog(webContextPath,'角色配置','600','530','保存','关闭','userRoleConfigForm','userRoleConfigDialog',userRoleConfigSubmit,userRoleConfigDialogCancel);
    }
	
	function userRoleConfigDialogCancel(){
   	 	//$("#userRoleConfigForm").remove();
   	 	userRoleConfigDialog.close();
	}
	
	//状态处理
	function renderStatus(record, rowIndex, colIndex, options){
		var userStatus = record.u_status;
		if(parseInt(record.u_status) == 0){
			userStatus = "已启用";
		}else if(parseInt(record.u_status) == 1){
			userStatus = "<font color=\"gray\">已禁用</font>";
		}
		return userStatus;
	}
	
    //查询
    function queryUserList() {
    	var reg = new RegExp("^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$");
		
		var u_mobile = $('#u_mobile').val();
		
    	if(u_mobile!="" && !reg.test(u_mobile)){
			BUI.Message.Alert('请正确输入手机号码!','warning');
			return;
		}
		if(!searchForm.isValid()){//表单数据验证
			return;
		}
    	forumConsole.refreshGrid('searchForm');
	}

    //跳转到添加用户页面
	function toAddUser(){
		window.location.href=contextPath+"/toAddUserPage.do";
	}
	
	//添加用户信息
	function addUserSubmit(){
		var ext_sys_id = $("#ext_sys_id").val();
		var u_user_id = null;
		var u_user_mobile = null;
		if(parseInt(ext_sys_id) == 1){
			u_user_id = $("#ext_sys_object_id").val();
			if("" == u_user_id || null == u_user_id || typeof(u_user_id) == undefined){
				BUI.Message.Alert("用户id不能为空!",'warning');
				return false;
			}
			u_user_mobile = $("#u_user_mobile").val();
			if("" == u_user_mobile || null == u_user_mobile || typeof(u_user_mobile) == undefined){
				BUI.Message.Alert("手机号码不能为空!",'warning');
				return false;
			}
		}
		var u_user_name = $("#u_user_name").val();
		if("" == u_user_name || null == u_user_name || typeof(u_user_name) == undefined){
			BUI.Message.Alert("用户名称不能为空!",'warning');
			return false;
		}
		var u_login_name = $("#u_login_name").val();
		if("" == u_login_name || null == u_login_name || typeof(u_login_name) == undefined){
			BUI.Message.Alert("登录名不能为空!",'warning');
			return false;
		}
		var u_mobile = $("#u_mobile").val();
		if("" == u_mobile || null == u_mobile || typeof(u_mobile) == undefined){
			BUI.Message.Alert("手机号码不能为空!",'warning');
			return false;
		}
		var u_user_code = $("#u_user_code").val();
		if("" == u_user_code || null == u_user_code || typeof(u_user_code) == undefined){
			BUI.Message.Alert("用户编码不能为空!",'warning');
			return false;
		}
		var u_status = $("#u_status").val();
		if("" == u_status || null == u_status || typeof(u_status) == undefined){
			BUI.Message.Alert("状态不能为空!",'warning');
			return false;
		}
		var u_remark = $("#u_remark").val();
		if("" == u_remark || null == u_remark || typeof(u_remark) == undefined){
			BUI.Message.Alert("备注不能为空!",'warning');
			return false;
		}
		var u_login_pwd = $("#u_login_pwd").val();
		if("" == u_login_pwd || null == u_login_pwd || typeof(u_login_pwd) == undefined){
			BUI.Message.Alert("登录密码不能为空!",'warning');
			return false;
		}
		u_login_pwd = $.md5(u_login_pwd);
		if(!addUserForm.isValid()){//表单数据验证
			return;
		}
		var sendData = {
			'u_user_id':u_user_id,
        	'u_user_mobile':u_user_mobile,
        	'u_login_pwd':u_login_pwd,
        	'u_user_name':u_user_name,
        	'u_login_name':u_login_name,
        	'u_mobile':u_mobile,
        	'u_user_code':u_user_code,
        	'u_status':u_status,
        	'u_remark':u_remark
		};
		forumConsole.ajaxCall('POST',contextPath+"/addUser.do",true,sendData,'json',function(data){
			if(null == data || data == undefined){
    			BUI.Message.Alert("请求失败了!",'warning');
    			return;
    		}
    		if(typeof data == 'string'){
    			data = eval("("+data+")");
    		}
    		if(data.res_code == '1'){
    			BUI.Message.Alert(data.res_msg,'success');
    			window.location.href=contextPath+"/toUserListPage.do";
    		}else{
    			BUI.Message.Alert(data.res_msg,'warning');
    			return;
    		}
		},function(){},function(){
			alert("addUser[ajax请求出错了]!");
		});
	}
	
	//跳转到修改用户信息页
	function toUpdateUser(u_user_id){
		window.location.href=contextPath+"/toUpdateUserPage.do?u_user_id="+u_user_id;
	}
	
	//修改用户信息
	function updateUserSubmit(){
		var u_user_id = null;
		var u_user_mobile = null;

		if(parseInt(ext_sys_id) == 1){
			u_user_id = $("#ext_sys_object_id").val();
			if("" == u_user_id || null == u_user_id || typeof(u_user_id) == undefined){
				BUI.Message.Alert("关联用户id不能为空!",'warning');
				return false;
			}
		}

		if(!updateUserForm.isValid()){//表单数据验证
			return;
		}
		$("#updateUserForm").submit(function () {   
	        $(this).ajaxSubmit({
	            type: "post",
	            url: contextPath+ "/updateUser.do",
				dataType: "json",
	            success: updateUserSuccess,
	            error: updateUserError
	        });
	        return false;
	    });
	    $("#updateUserForm").submit();

	}

	function updateUserError(){
		 alert("updateUser请求出错了!");
	}
	
    //删除
	function deleteUser(rowIndex) {
		var obj = gridObj.getRecord(rowIndex);//取得行记录数据
		var sendData = {
				'u_user_id':obj.u_user_id,
	        	'u_user_code':obj.u_user_code
		};
		var operateMsg = '确定要删除用户ID为'+ obj.u_user_id+' 的记录吗？';
		
		BUI.Message.Confirm(operateMsg, function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteUser.do",true,sendData,'json',deleteUserSuccess,deleteUserComplete,deleteUserError);
		},'question');
		
	}
	
	function deleteUserSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	queryUserList();
	}
	function deleteUserComplete(){
	}
	function deleteUserError(){
		 alert("deleteUser[ajax请求出错了]!");
	}
	
	//批量删除
	function batchDeleteUser(){
		var u_user_id_selected = "";//id1,id2,id3,id4
		//var role_code_selected = "";//id1,id2,id3,id4
		var u_user_code_selected = "";//id1,id2,id3,id4
		var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要删除的行!','warning');
			return;
		}
		for(var i=0; i<objArray.length; i++){
			
			u_user_id_selected += objArray[i].u_user_id;
			//role_code_selected += objArray[i].role_code;
			u_user_code_selected += objArray[i].u_user_code;
			
			if(i!= objArray.length - 1){
				u_user_id_selected += ",";
				//role_code_selected += ",";
				u_user_code_selected += ",";
			}
		}
		/*console.log(u_user_id_selected);
		console.log(role_code_selected);
		console.log(u_user_code_selected);*/
		
		var sendData = {
				'u_user_id':u_user_id_selected,
	        	'u_user_code':u_user_code_selected//,
	        	//'role_code':role_code_selected
		};
		
		BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteUser.do",true,sendData,'json',batchDeleteUserSuccess,batchDeleteUserComplete,batchDeleteUserError);
		},'question');

	}
	
	function batchDeleteUserSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	
    	queryUserList();
	}
	function batchDeleteUserComplete(){
	}
	function batchDeleteUserError(){
		 alert("batchDeleteUser[ajax请求出错了]!");
	}
	
	//跳转到角色配置页面
	function toUserRoleConfig(rowIndex){
		var obj = gridObj.getRecord(rowIndex);//取得行记录数据
		/*var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要操作的记录!','warning');
			return false;
		}
		if(objArray.length > 1){
			BUI.Message.Alert('只能选择一条记录进行操作!','warning');
			return false;
		}
		var obj = objArray[0];*/
		
		userRoleConfigDialog.show();
		$("#userRoleConfigDialog").load(contextPath+"/toUserRoleConfigPage.do",function(){
			$("#userCode").val(obj.u_user_code);
			
			var sendData = {
		        	'userCode':obj.u_user_code
			};
			forumConsole.ajaxCall('POST',contextPath+"/queryUserRole.do",true,sendData,'json',queryUserRoleSuccess,queryUserRoleComplete,queryUserRoleError);
		});
	}
	
	function queryUserRoleSuccess(data){
		var roleList = data.roleList;
		var userRoleList = data.userRoleList;
		var tr="";
		var mun=0;
		$("#userRoleConfigTable").empty();
		for(var i=0;i<roleList.length;i++){
			if(i+1!=roleList.length){
				tr+='<tr height="30">';
				tr+='<td width="100">';
				tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+roleList[i].roleCode+' />&nbsp;'+roleList[i].roleName+'</td>';
				tr+='<td width="100" align="left"><input type="checkbox" name="ch" class="rol_'+(i+1)+'" value='+roleList[i+1].roleCode+' />&nbsp;'+roleList[i+1].roleName+'</td>';
				tr+='</tr>';
			}else{
				tr+='<tr height="30">';
				tr+='<td width="100">';
				tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+roleList[i].roleCode+' />&nbsp;'+roleList[i].roleName+'</td>';
				tr+='<td></td></tr>';
			}
			i++;
		}
		$("#userRoleConfigTable").append($(tr));
		
		if(userRoleList.length!=0){
		     var chs = $("table[id=userRoleConfigTable] :checkbox");
		     for(var k=0;k<chs.length;k++){
		    	 for(var j=0;j<userRoleList.length;j++){
		    		 if(chs[k].value==userRoleList[j].roleCode){
		    			 $(".rol_"+k).attr("checked",true);
		    			 break;
		    		 }
		    	 }
		     }
		}
	}
	function queryUserRoleComplete(){
	}
	function queryUserRoleError(){
		 alert("queryUserRole[ajax请求出错了]!");
	}
	
	//用户角色配置submit
	function userRoleConfigSubmit(){
		var userRole="";
		var userCode="";
		var userRoleStr="";
		var userCodeStr="";
		$("input[name='ch']:checked").each(function(){
			userRole+=$(this).val()+","; 
			userCode+=$("#userCode").val()+",";
        });
		userRoleStr = (userRole.toString()).substring(0, (userRole.toString()).length-1);
		userCodeStr = (userCode.toString()).substring(0, (userCode.toString()).length-1);
		if(userRoleStr.length == 0 && userCodeStr.length == 0){
			userRoleStr = "";
			userCodeStr = $("#userCode").val();
		}
		var sendData = {
	        	'user_code':userCodeStr,
	        	'role_code':userRoleStr
		};
		forumConsole.ajaxCall('POST',contextPath+"/addUserRole.do",true,sendData,'json',addUserRoleSuccess,addUserRoleComplete,addUserRoleError);
	}
	
	function addUserRoleSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			//window.location.href=contextPath+"/toUserListPage.do";
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
		userRoleConfigDialog.close();
		queryUserList()
	}
	function addUserRoleComplete(){
	}
	function addUserRoleError(){
		 alert("addUserRole[ajax请求出错了]!");
	}
	//重置密码
	function resetUserPassword(rowIndex){
		var obj = gridObj.getRecord(rowIndex);

		var sendData = {
			'u_user_id':obj.u_user_id,
			'action': 'reset_password'
		};

		BUI.Message.Confirm('确定要重置密码吗？',function(){
			forumConsole.ajaxCall('POST',contextPath+"/updateUser.do",true,sendData,'json',
				function(data){
					if(null == data || data == undefined){
	        			BUI.Message.Alert("请求失败了!",'warning');
	        			return;
	        		}
	        		if(typeof data == 'string'){
	        			data = eval("("+data+")");
	        		}
					if(data.res_code == '1'){
						BUI.Message.Alert(data.res_msg,function(){
	        				forumConsole.refreshPage();
	        			},'success');
					}else{
						BUI.Message.Alert(data.res_msg,'error');
					}
				},function(){},function(){
					alert("updateUser[ajax请求出错了]!");
				}
			);
		},'question');
	}



	//根据外部系统设置外部系统用户
	function setExtSysUser(_this){
		var selectValue = _this.value;
		if(parseInt(selectValue) == 1){
			$("#ext_sys_object_id").removeAttr("disabled");
		}else if(parseInt(selectValue) == 0){
			$("#ext_sys_object_id").attr("disabled",true);
		}
	}

	function updateUserSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'warning');
		}
	}

	function changeExtUser(){
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
			forumConsole.ajaxCall('POST', contextPath + "/queryExtSysUserSelectList.do",true,sendData,'json',queryExtSysUserSelectListSuccess,queryExtSysUserSelectListComplete,queryExtSysUserSelectListError);
		}
		function queryExtSysUserSelectListSuccess(data){
			if(null == data || data == undefined){
				BUI.Message.Alert("请求失败了!",'warning');
				$("#ext_user").html("");
				return;
			}else{
				extSysUserData = data.data;
				if(extSysUserData.length > 0){
						$("#ext_user").html("&nbsp;&nbsp;用户名：<span style=\"height:30px;line-height:30px;\" id=\"u_user_name\" name=\"u_user_name\">"+extSysUserData[0].nick_name+"</span>"
							+"&nbsp;&nbsp;&nbsp;&nbsp;手机号码：<span style=\"height:30px;line-height:30px;\" id=\"u_user_mobile\" name=\"u_user_mobile\">"+extSysUserData[0].user_mobile+"</span>");
					$("#ext_sys_object_value").val(extSysUserData[0].user_mobile);
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