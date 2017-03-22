<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<form id="addPrivilegeForm" class="form-horizontal">
    <table class="table table-bordered table-hover">
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>权限名称：</label></td>
            <td colspan="2">
            	<input id="add_privilege_name" name="privilege_name" type="text" class="input-large" 
            	data-rules="{required : true}" data-messages="{required:'权限名称不能为空'}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>权限编码：</label></td>
            <td colspan="2">
            	<input id="add_privilege_code" name="privilege_code" type="text" class="input-large" 
            	placeholder="参考格式:PRI_FORUM_GLOBAL"
            	data-messages="{regexp:'前缀必须为PRI_ &nbsp;且前缀之后只能为字母和下划线'}" data-rules="{regexp:/^PRI_([a-zA-Z_]+)$/}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">权限描述：</label></td>
            <td colspan="2">
            	<textarea id="add_privilege_desc" name="privilege_desc" class="control-row4 input-large"></textarea>
            </td>
        </tr>
        
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>资源内容：</label></td>
            <td>
				<div>
					<ul id="privilege_list" name="privilege_content"  class="ztree"></ul>
				</div>
            </td>
        </tr>
    </table>
</form>
<script>
var addPrivilegeForm;
BUI.use(['bui/form'],function(Form){
	
	addPrivilegeForm = new Form.HForm({
    	srcNode : "#addPrivilegeForm",
  	});
    
	addPrivilegeForm.render();
});

var privilegeCodeField = addPrivilegeForm.getField('privilege_code');
privilegeCodeField.set('remote',{
   url : "<%=request.getContextPath()%>/privilegeDataChecked.do",
   dataType:'json',//默认为字符串
   callback : function(data){
         if(data.success){
          	return '';
         }else{
          	return data.msg;
         }
   }
});

$(function(){
	 $('#add_privilege_content').bind('dblclick',function(){//给下拉框绑定双击事件
	     moveToRight('add_privilege_content','add_privilege_content_all');
	    });
	    $('#add_privilege_content_all').bind('dblclick',function(){
	     moveToLeft('add_privilege_content','add_privilege_content_all');
	    }); 

	setting = {

		async: {
			enable: true,
			url:contextPath+"/getResourceTree.do",
			autoParam:["id", "name"]
//			otherParam:{"USER_ID":USER_ID}
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
});

function zTreeInit(_setting){
	var privilege_list = $.fn.zTree.init($("#privilege_list"), _setting);
}

function queryResourceSuccessCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求资源失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		var resData  = data.res_data;
		 for(var i=0;i<  resData.length;i++){
			 $("#add_privilege_content_all").append("<option value='"+resData[i].resource_code+"'>"+resData[i].resource_name+"</option>");
		 }
		
	}else{
		BUI.Message.Alert("请求资源失败了!",'warning');
	}
}
function queryResourceCompleteCallBack(){
}
function queryResourceErrorCallBack(){
	alert("请求资源失败");
}

</script>
