<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<form id="editRoleForm" class="form-horizontal">
    <table class="table table-bordered table-hover">
        <tr>
            <td width="20%" class="tableleft"><label class="control-label"><s>*</s>角色名称：</label></td>
            <td>
            	<input id="edit_role_id" name="roleId" type="hidden"/>
            	<input id="edit_role_name" name="roleName" type="text" class="input-large" 
            	data-rules="{required : true}" data-messages="{required:'角色名称不能为空'}">
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">角色描述：</label></td>
            <td>
            	<textarea id="edit_role_desc" name="roleDesc" class="control-row4 input-large"></textarea>
            </td>
        </tr>
        <tr>
            <td width="20%" class="tableleft"><label class="control-label">角色编码：</label></td>
            <td>
            	<input id="edit_role_code" name="roleCode" type="text" class="input-large" readonly="readonly" >
            </td>
        </tr>
    </table>
</form>
<script>
BUI.use(['bui/form'],function(Form){
	
	editRoleForm = new Form.HForm({
    	srcNode : "#editRoleForm",
  	});
    
	editRoleForm.render();
});
</script>