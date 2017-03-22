var editPrivilegeDialog;
var addPrivilegeDialog;
function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	forumConsole.init('searchTable',webContextPath+'/queryPrivilegeList.do','json',10);
	editPrivilegeDialog = forumConsole.genDialog(webContextPath,'编辑权限','800','auto','保存','关闭','editPrivilegeForm','edit_privilege_dialog',editPrivilege,editPrivilegeDialogCancel);
	addPrivilegeDialog =  forumConsole.genDialog(webContextPath,'添加权限','800','auto','保存','关闭','addPrivilegeForm','add_privilege_dialog',addPrivilege,addPrivilegeDialogCancel);

}

function editPrivilege(){
	if(!editPrivilegeForm.isValid()){//表单数据验证
		return;
	}
	var privilege_selected = $.fn.zTree.getZTreeObj("privilege_list").getCheckedNodes(true);
	if(privilege_selected.length == 0 || privilege_selected == undefined){
		BUI.Message.Alert('不能没有权限内容!','warning');
		return false;
	}

	var privilege_content_arr = [];
	for(var i=0;i<privilege_selected.length;i++){
		privilege_content_arr.push({"name":"privilege_content", "value":privilege_selected[i].resource_code});
	}
	
	
    $("#editPrivilegeForm").ajaxSubmit({
        type: "post",
		data: privilege_content_arr,
        url: contextPath+ "/editPrivilege.do",
        success: editSuccessCallBack,
        error: editErrorCallBack
    });
	
}

function editSuccessCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	
	$("#editPrivilegeForm").remove();
	editPrivilegeDialog.close();
	queryPrivilegeList();
}

function editErrorCallBack(){
	 alert("请求出错了!");
}

function editPrivilegeDialogCancel(){
	$("#editPrivilegeForm").remove();
	editPrivilegeDialog.close();
}

function addPrivilege(){
	if(!addPrivilegeForm.isValid()){//表单数据验证
		return;
	}
	var privilege_selected = $.fn.zTree.getZTreeObj("privilege_list").getCheckedNodes(true);
	if(privilege_selected.length == 0 || privilege_selected == undefined){
		BUI.Message.Alert('不能没有权限内容!','warning');
		return false;
	}
	var privilege_content_arr = [];
	for(var i=0;i<privilege_selected.length;i++){
		privilege_content_arr.push({"name":"privilege_content", "value":privilege_selected[i].resource_code});
	}

	$("#addPrivilegeForm").ajaxSubmit({
		type: "post",
		url: contextPath+ "/addPrivilege.do",
		data: privilege_content_arr,
		success: successCallBack,
		error: errorCallBack
	});
}


function successCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	
	$("#addPrivilegeForm").remove();
	addPrivilegeDialog.close();
	queryPrivilegeList();
}

function queryPrivilegeList(){
    forumConsole.refreshGrid('searchForm');
}

function completeCallBack(){}

function errorCallBack(){
	 alert("请求出错了!");
}

function addPrivilegeDialogCancel(){
	$("#addPrivilegeForm").remove();
	addPrivilegeDialog.close();
}

function toAddPrivilege(){
	addPrivilegeDialog.show();
	$("#add_privilege_dialog").load(contextPath+"/toAddPrivilege.do");
}

function toEditPrivilege(rowIndex){
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
	editPrivilegeDialog.show();
	$("#edit_privilege_dialog").load(contextPath+"/toEditPrivilege.do?privilege_id="+obj.privilege_id+"&privilege_code="+obj.privilege_code,
			function(){
		//给circlesDetailTable中的属性赋值
		$("#edit_privilege_id").val(obj.privilege_id);
		$("#edit_privilege_name").val(obj.privilege_name);
		$("#edit_privilege_desc").val(obj.privilege_desc);
		$("#edit_privilege_code").val(obj.privilege_code);
		
		 var privilegeResourceRefBeans  =obj.privilegeResourceRefBeans;
		 if(null != privilegeResourceRefBeans && privilegeResourceRefBeans != undefined ){
			 for(var i=0;i< privilegeResourceRefBeans.length;i++){
				 $("#edit_privilege_content").append("<option value='"+privilegeResourceRefBeans[i].resource_code+"'>"+privilegeResourceRefBeans[i].resource_name+"</option>");
			 }
		 }
	}		
	
	);
}

function privilegeContent(record, rowIndex, colIndex, options){
	var html = '<select multiple  class="form-control" name="privilege_content_"'+rowIndex+'> ';
	var privilegeResourceRefBeans  =record.privilegeResourceRefBeans;
	 for(var i=0;i<  privilegeResourceRefBeans.length;i++){
		 html += "<option value='"+privilegeResourceRefBeans[i].resource_code+"'>"+privilegeResourceRefBeans[i].resource_name+"</option>";
	 }
	html += '</select>';
	return html;
}

function batchDeletePrivilege(){
	var objArray = gridObj.getCheckedRowsRecords();
	if(objArray.length == 0){
		BUI.Message.Alert('请选择需要删除的记录!','warning');
		return false;
	}
	var privilege_codes ="";
	for(var i=0; i<objArray.length; i++){
		privilege_codes += objArray[i].privilege_code;
		
		if(i!= objArray.length - 1){
			privilege_codes += ",";
		}
	}
	
	deletePrivilege(privilege_codes);
}

function deletePrivilege(privilege_code){
	BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
		forumConsole.ajaxCall("POST",contextPath+"/deletePrivilege.do",true,{'privilege_codes':privilege_code},"json",successCallBack,completeCallBack,errorCallBack);
        },'question');
}


function moveToRight(select1,select2)//把选中的移动到右边 sleect1和sleect2分别是下拉列表框的ID
{
 $('#'+select1+' option:selected').each(function(){
  $("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>").appendTo("#"+select2+"");
  $(this).remove();
  $('#'+select2).bind('dblclick',function(){
  moveToLeft(select1,select2);
  });  
 });
}
function moveAllToRight(select1,select2)//把所有的移动到右边
{
 $('#'+select1+' option').each(function(){
  $("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>").appendTo("#"+select2+"");
  $(this).remove();
 });
}
function moveToLeft(select1,select2)//把选中的移动到左边
{
 $('#'+select2+' option:selected').each(function(){
  $("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>").appendTo("#"+select1+"");
  $(this).remove();
 });
}
function moveAllToLeft(select1,select2)//把所有的移动到左边
{
 $('#'+select2+' option').each(function(){
  $("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>").appendTo("#"+select1+"");
  $(this).remove();
 });
}

//刷新权限缓存
function refreshPrivilegeCache(){
	var sendData = null;
	forumConsole.ajaxCall('POST',contextPath+"/refreshPrivilegeCache.do",true,sendData,'json',refreshPrivilegeCacheSuccess,refreshPrivilegeCacheComplete,refreshPrivilegeCacheError);
	function refreshPrivilegeCacheSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
	}
	function refreshPrivilegeCacheComplete(){
	}
	function refreshPrivilegeCacheError(){
		 alert("refreshPrivilegeCache[ajax请求出错了]!");
	}
}

         
