<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<form id="addRoleForm" class="form-horizontal">
    <table class="table table-bordered table-hover">
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>角色名称：</label></td>
            <td>
            	<input id="add_role_name" name="roleName" type="text" class="input-large" 
            	data-rules="{required : true}" data-messages="{required:'角色名称不能为空'}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">角色描述：</label></td>
            <td>
            	<textarea id="add_role_desc" name="roleDesc" class="control-row4 input-large"></textarea>
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">角色编码：</label></td>
            <td>
            	<input id="add_role_code" name="roleCode" type="text" class="input-large" 
            	placeholder="参考格式:ROLE_SUPERSYSTEM"
            	data-messages="{regexp:'前缀必须为ROLE_ &nbsp;且前缀之后只能为字母'}" data-rules="{regexp:/^ROLE_([a-zA-Z]+)$/}">
            </td>
        </tr>
    </table>
</form>
<script>
BUI.use(['bui/form'],function(Form){
	
	addRoleForm = new Form.HForm({
    	srcNode : "#addRoleForm",
  	});
    
	addRoleForm.render();
});

var roleCodeField = addRoleForm.getField('roleCode');
roleCodeField.set('remote',{
   url : "<%=request.getContextPath()%>/roleDataChecked.do",
   dataType:'json',//默认为字符串
   callback : function(data){
         if(data.success){
          	return '';
         }else{
          	return data.msg;
         }
   }
});
</script>