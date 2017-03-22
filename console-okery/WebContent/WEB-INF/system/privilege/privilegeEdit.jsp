<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<form id="editPrivilegeForm" class="form-horizontal">
    <input type="hidden" name="privilege_id" id="edit_privilege_id" value="${param.privilege_id }" />
    <table class="table table-bordered table-hover">
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>权限名称：</label></td>
            <td colspan="2">
            	<input id="edit_privilege_name" name="privilege_name" type="text" class="input-large" 
            	data-rules="{required : true}" data-messages="{required:'权限名称不能为空'}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>权限编码：</label></td>
            <td colspan="2">
            	<input id="edit_privilege_code" readonly="readonly" name="privilege_code" type="text" class="input-large" 
            	data-rules="{required : true}" data-messages="{required:'权限编码不能为空'}" value="${param.privilege_code}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">权限描述：</label></td>
            <td colspan="2">
            	<textarea id="edit_privilege_desc" name="privilege_desc" class="control-row4 input-large"></textarea>
            </td>
        </tr>

		<tr>
			<td width="20%" class="tableleft"><label class="control-label"><s>*</s>资源内容：</label></td>
			<td>
				<div>
					<ul id="privilege_list" name="privilege_content_all"  class="ztree"></ul>
				</div>
			</td>
		</tr>
    </table>
</form>
<script>
var editPrivilegeForm;
BUI.use(['bui/form'],function(Form){
	
	editPrivilegeForm = new Form.HForm({
    	srcNode : "#editPrivilegeForm",
  	});
    
	editPrivilegeForm.render();
});

$(function(){
	setting = {
		async: {
			enable: true,
			url:contextPath+"/getResourceTree.do",
			autoParam:["id", "name"],
			otherParam:{"privilege_code":$("#edit_privilege_code").val()}
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"parent_resource",
				rootPid:null
			}
		},
		callback: {
		}
	};

	zTreeInit(setting);
//		forumConsole.ajaxCall("POST",contextPath+"/queryResourceContent.do",true,sendData,"json",queryResourceSuccessCallBack,queryResourceCompleteCallBack,queryResourceErrorCallBack);
});

function zTreeInit(_setting){
	$.fn.zTree.init($("#privilege_list"), _setting);
}

function queryResourceSuccessCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求资源失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		var resData  = data.res_data;
		if(null != resData && resData != undefined ){
		 for(var i=0;i<  resData.length;i++){
			 $("#edit_privilege_content_all").append("<option value='"+resData[i].resource_code+"'>"+resData[i].resource_name+"</option>");
		 }
		}
	}else{
		BUI.Message.Alert("请求资源失败了!",'warning');
	}
}
function queryResourceCompleteCallBack(){
	
}
function queryResourceErrorCallBack(){
	BUI.Message.Alert("请求失败了!",'warning');
} 

</script>