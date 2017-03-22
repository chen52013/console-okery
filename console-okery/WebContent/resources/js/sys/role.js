/**
 * 角色
 */
	var addRoleDialog;
	var addRoleForm;
	var editRoleDialog;
	var editRoleForm;
	var assignPrivilegeDialog;
    function init(webContextPath) {
    	$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryRoleList.do','json',10);
		addRoleDialog = forumConsole.genDialog(webContextPath,'添加角色','600','530','保存','关闭','addRoleForm','add_role_dialog',addRole,addRoleDialogCancel);
		editRoleDialog = forumConsole.genDialog(webContextPath,'编辑角色','600','530','保存','关闭','editRoleForm','edit_role_dialog',editRole,editRoleDialogCancel);
		assignPrivilegeDialog = forumConsole.genDialog(webContextPath,'分配权限','600','530','保存','关闭','assignPrivilegeForm','assign_privilege_dialog',assignPrivilege,assignPrivilegeDialogCancel);
    }
    
    function addRoleDialogCancel(){
   	 	$("#addRoleForm").remove();
   	 	addRoleDialog.close();
	}
    
    function assignPrivilegeDialogCancel(){
    	$("#assignPrivilegeForm").remove();
    	assignPrivilegeDialog.close();
    }
    
    function editRoleDialogCancel(){
   	 	$("#editRoleForm").remove();
   	 	editRoleDialog.close();
	}
    
    //跳转到添加角色页面
	function toAddRole(){
		addRoleDialog.show();
		$("#add_role_dialog").load(contextPath+"/toAddRolePage.do");
	}
	
    function addRole(){
    	var roleName = $('#add_role_name').val();
		var roleDesc = $('#add_role_desc').val();
		var roleCode = $('#add_role_code').val();
		
		if(!addRoleForm.isValid()){//表单数据验证
			return;
		}
		var sendData = { 
				'roleName':roleName,
	        	'roleDesc':roleDesc,
	        	'roleCode':roleCode
				};
		forumConsole.ajaxCall('POST',contextPath+"/addRole.do",true,sendData,'json',addRoleSuccess,addRoleComplete,addRoleError);
	}
    
    function addRoleSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	
    	$("#addRoleForm").remove();//每次关闭add_role_dialog,都需要移除addRoleForm,再次打开时,再去load() addRoleForm
    	addRoleDialog.close();//关闭dialog
    	queryRoleList();
	}
	function addRoleComplete(){
	}
	function addRoleError(){
		 alert("addRole[ajax请求出错了]!");
	}
	
	function editRole(){
		var roleId = $('#edit_role_id').val();
		var roleName = $('#edit_role_name').val();
		var roleDesc = $('#edit_role_desc').val();
		
		if(!editRoleForm.isValid()){//表单数据验证
			return;
		}
		var sendData = {
				'roleId':roleId,
	        	'roleName':roleName,
	        	'roleDesc':roleDesc
				};
		forumConsole.ajaxCall('POST',contextPath+"/editRole.do",true,sendData,'json',editRoleSuccess,editRoleComplete,editRoleError);
	}
	
	function editRoleSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	
    	$("#editRoleForm").remove();
    	editRoleDialog.close();
    	queryRoleList();
	}
	function editRoleComplete(){
	}
	function editRoleError(){
		 alert("editRole[ajax请求出错了]!");
	}
    
    function queryRoleList(){
        forumConsole.refreshGrid('searchForm');
	}
    
    //跳转到角色权限分配页面
    function toAssignPrivilege(rowIndex){
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
    	
    	assignPrivilegeDialog.show();
    	
    	$("#assign_privilege_dialog").load(contextPath+"/toAssignPrivilegePage.do",function(){
			$("#roleCode").val(obj.roleCode);
			
			var sendData = {
		        	'roleCode':obj.roleCode
			};
			forumConsole.ajaxCall('POST',contextPath+"/queryRolePrivilege.do",true,sendData,'json',queryRolePrivilegeSuccess,queryRolePrivilegeComplete,queryRolePrivilegeError);
		});
    	
    }
    
    function queryRolePrivilegeSuccess(data){
		var privilegeList = data.privilegeList;
		var rolePrivilegeList = data.rolePrivilegeList;
		var tr="";
		var mun=0;
		$("#assignPrivilegeTable").empty();
		for(var i=0;i<privilegeList.length;i++){
			if(i+1!=privilegeList.length){
				tr+='<tr height="30">';
				tr+='<td width="100">';
				tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
				tr+='<td width="100" align="left"><input type="checkbox" name="ch" class="rol_'+(i+1)+'" value='+privilegeList[i+1].privilege_code+' />&nbsp;'+privilegeList[i+1].privilege_name+'</td>';
				tr+='</tr>';
			}else{
				tr+='<tr height="30">';
				tr+='<td width="100">';
				tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
				tr+='<td></td></tr>';
			}
			i++;
		}
		$("#assignPrivilegeTable").append($(tr));
		
		if(rolePrivilegeList.length!=0){
		     var chs = $("table[id=assignPrivilegeTable] :checkbox");
		     for(var k=0;k<chs.length;k++){
		    	 for(var j=0;j<rolePrivilegeList.length;j++){
		    		 if(chs[k].value==rolePrivilegeList[j].privilege_code){
		    			 $(".rol_"+k).attr("checked",true);
		    			 break;
		    		 }
		    	 }
		     }
		}
	}
	function queryRolePrivilegeComplete(){
	}
	function queryRolePrivilegeError(){
		 alert("queryRolePrivilege[ajax请求出错了]!");
	}
	
	//分配角色权限submit
    function assignPrivilege(){
    	var roleCode="";//角色编码
		var privilegeCode="";//分配给角色的权限编码
		var roleCodeStr="";
		var privilegeCodeStr="";
		$("input[name='ch']:checked").each(function(){
			roleCode+=$("#roleCode").val()+",";
			privilegeCode+=$(this).val()+","; 
        });
		roleCodeStr = (roleCode.toString()).substring(0, (roleCode.toString()).length-1);
		privilegeCodeStr = (privilegeCode.toString()).substring(0, (privilegeCode.toString()).length-1);
		if(roleCodeStr.length == 0 && privilegeCodeStr.length == 0){
			roleCodeStr = $("#roleCode").val();
			privilegeCodeStr = "";
		}
		var sendData = {
	        	'role_code':roleCodeStr,
	        	'privilege_code':privilegeCodeStr
		};
		forumConsole.ajaxCall('POST',contextPath+"/assignPrivilege.do",true,sendData,'json',assignPrivilegeSuccess,assignPrivilegeComplete,assignPrivilegeError);
    }

    function assignPrivilegeSuccess(data){
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			//window.location.href=contextPath+"/toUserListPage.do";
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
		//$("#assignPrivilegeForm").remove();
		assignPrivilegeDialog.close();
    	queryRoleList();
	}
	function assignPrivilegeComplete(){
	}
	function assignPrivilegeError(){
		 alert("assignPrivilege[ajax请求出错了]!");
	}
	
    function toEditRole(rowIndex){
    	var obj = gridObj.getRecord(rowIndex);
    	/*var objArray = gridObj.getCheckedRowsRecords();
    	if(objArray.length == 0){
    		BUI.Message.Alert('请选择需要编辑的记录!','warning');
    		return false;
    	}
    	if(objArray.length > 1){
    		BUI.Message.Alert('只能选择一条记录进行操作!','warning');
    		return false;
    	}
    	var obj = objArray[0];*/
    	
    	editRoleDialog.show();
    	$("#edit_role_dialog").load(contextPath+"/toEditRolePage.do",
    			function(){
			//给circlesDetailTable中的属性赋值
			$("#edit_role_id").val(obj.roleId);
			$("#edit_role_name").val(obj.roleName);
			$("#edit_role_desc").val(obj.roleDesc);
			$("#edit_role_code").val(obj.roleCode);
		}		
    	
    	);
    }
    

	function batchDeleteRole(){
		var roleId_selected = "";//id1,id2,id3,id4
		var roleCode_selected = "";//id1,id2,id3,id4
		
		var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要删除的记录!','warning');
			return false;
		}
		for(var i=0; i<objArray.length; i++){
			roleId_selected += objArray[i].roleId;
			roleCode_selected += objArray[i].roleCode;
			
			if(i!= objArray.length - 1){
				roleId_selected += ",";
				roleCode_selected += ",";
			}
		}
		
		var sendData = {
				'roleId':roleId_selected,
	        	'roleCode':roleCode_selected
		};
		
		BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
			forumConsole.ajaxCall("POST",contextPath+"/deleteRole.do",true,sendData,"json",batchDeleteRoleSuccess,batchDeleteRoleComplete,batchDeleteRoleError);
	        },'question');

	}
	
	function batchDeleteRoleSuccess(data){
		if(null == data || data == undefined){
			BUI.Message.Alert("请求失败了!",'warning');
			return;
		}
		if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
			queryRoleList();
		}else{
			BUI.Message.Alert(data.res_msg,'warning');
		}
	}
	
	function batchDeleteRoleComplete(){}
	
	function batchDeleteRoleError(){
		alert("batchDeleteRole[ajax请求出错了]!");
	}
