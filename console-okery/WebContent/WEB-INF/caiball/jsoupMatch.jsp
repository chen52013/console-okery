<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<form id="jsoupMatchForm" class="form-horizontal">
    <input id="jsoup_url" name="jsoup_url" type="text" class="input-large" placeholder="请输入需要抓取的网址">
</form>
<script>
var jsoupMatchForm;
BUI.use(['bui/form'],function(Form){
	jsoupMatchForm = new Form.HForm({
    	srcNode : "#jsoupMatchForm",
  	});
	jsoupMatchForm.render();
});
</script>
