<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix='security' uri='http://www.springframework.org/security/tags' %> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会话不存在</title>
</head>

<jsp:include page="${request.contextPath}/WEB-INF/common/common.jsp"></jsp:include>
<script type="text/javascript">
function sessionTimeOut(){
	window.top.location.href = '<%=request.getContextPath()%>/j_spring_security_logout';
}
var int=setInterval("sessionTimeOut()",5000);
</script>
<body>
<div class="alert alert-block">
  <h4>Warning!</h4>
  会话已不存在,5秒后自动跳转或者点击<a
				href="#" title="登录"
				class="dl-log-quit" onclick="sessionTimeOut()">[登录]</a>跳转到登录页面
</div>
				
</body>
</html>