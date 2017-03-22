<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer" %>
	<%@ page isELIgnored="false" %>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><%=CustomPropertyPlaceholderConfigurer.getProperty("sys.flag")%></title>
	<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/reset.css">
    <link href="<%=request.getContextPath()%>/resources/assets/css/default/bui-min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/common.js"></script>
    <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/assets/js/bui-min.js"></script>
    <script type="text/javascript">
        $(function(){
            document.onkeydown = function(e){
                var ev = document.all ? window.event : e;
                if(ev.keyCode==13) {
                    $(".form-signin").submit();
                }
            }
        });
    </script>
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }
        .code-signin {
            max-width: 300px;
            padding: 19px 0px 0px 75px;
            margin: 0 auto;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
        .form-signin-heading{
        	line-height: 40px;
        	font-family: inherit;
    		font-weight: bold;
    		font-size: 31.5px;
        }

    </style>
</head>
<body>
<div class="login">
	<div id="particles-js"></div>
	<div class="login-widget">
	    <form id="loginForm" action="<%=request.getContextPath()%>/j_spring_security_check" method="post" role="form" class='login-form'>
		    <div class="flex-row">
		        <label class="lf--label" for="username">
		            <svg x="0px" y="0px" width="12px" height="13px">
		                <path fill="#B1B7C4" d="M8.9,7.2C9,6.9,9,6.7,9,6.5v-4C9,1.1,7.9,0,6.5,0h-1C4.1,0,3,1.1,3,2.5v4c0,0.2,0,0.4,0.1,0.7 C1.3,7.8,0,9.5,0,11.5V13h12v-1.5C12,9.5,10.7,7.8,8.9,7.2z M4,2.5C4,1.7,4.7,1,5.5,1h1C7.3,1,8,1.7,8,2.5v4c0,0.2,0,0.4-0.1,0.6 l0.1,0L7.9,7.3C7.6,7.8,7.1,8.2,6.5,8.2h-1c-0.6,0-1.1-0.4-1.4-0.9L4.1,7.1l0.1,0C4,6.9,4,6.7,4,6.5V2.5z M11,12H1v-0.5 c0-1.6,1-2.9,2.4-3.4c0.5,0.7,1.2,1.1,2.1,1.1h1c0.8,0,1.6-0.4,2.1-1.1C10,8.5,11,9.9,11,11.5V12z"/>
		            </svg>
		        </label>
		        <input name="j_username" id="j_username" class='lf--input' onblur="checkIsAuth();" placeholder='请输入账号' type='text' required="required" autofocus="autofocus"/>
		    </div>
		    <c:if test="${not empty param.login_error}">
			    <div color="red">
			        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
			    </div>
			</c:if>
		    <div class="flex-row">
		        <label class="lf--label" for="password">
		            <svg x="0px" y="0px" width="15px" height="5px">
		                <g>
		                    <path fill="#B1B7C4" d="M6,2L6,2c0-1.1-1-2-2.1-2H2.1C1,0,0,0.9,0,2.1v0.8C0,4.1,1,5,2.1,5h1.7C5,5,6,4.1,6,2.9V3h5v1h1V3h1v2h1V3h1 V2H6z M5.1,2.9c0,0.7-0.6,1.2-1.3,1.2H2.1c-0.7,0-1.3-0.6-1.3-1.2V2.1c0-0.7,0.6-1.2,1.3-1.2h1.7c0.7,0,1.3,0.6,1.3,1.2V2.9z"/>
		                </g>
		            </svg>
		        </label>
		        <input id="j_password" name="j_password" class='lf--input' placeholder='请输入密码' type='password' required="required"/>
		    </div>
		    <div class="flex-row">
		        <input type="text" class="lf--input" id="j_verification_code" name="j_verification_code" minlength="1" maxlength="6" data-rules="{required:true,number:true}" data-messages="{required:'手机动态验证码不能为空'}" style="border:1px solid #ccc;" placeholder="请打开验证器获取动态码">
				<input type="button" id="createVerificationButton" style="" class="btn btn-info" onclick="createVerificationCode()" value="扫码获取动态码" />		        
		    </div>
        	<p><button class="lf--submit" type="submit" accesskey="enter">登录</button></p>
			<p class="text-muted text-center" style="margin-top:15px;margin-left:23%;"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
	        </p>
        	<a target="_blank" style="float:right;margin-top:10px;text-decoration:underline;" href="<%=request.getContextPath()%>/resources/guide.html">扫码操作演示>>></a>
		</form>
		<div class="code-signin" id="code-div">
		    <iframe id="code_iframe" src="" width="300px;" height="300px;" frameborder="0" scrolling="auto">
			</iframe>
	    </div>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/particles.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/app.js"></script>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script>
	var contextPath;
	$(function () {
		/* $("#code-div").hide(); */
		$("#createVerificationButton").hide();
		contextPath = '<%=request.getContextPath()%>';
	});
	function checkIsAuth(){
		var j_username = $("#j_username").val();
		var sendData = null;
		var is_true = false;
		if(j_username != null && j_username != "" && typeof(j_username) != 'undefined'){
			sendData = {'j_username':j_username};
			is_true = true;
		}else{
			//未授权显示按钮
			$("#createVerificationButton").show();
			$("#code-div").show();
		}
		if(is_true){
			forumConsole.ajaxCall('POST',contextPath+"/checkIsAuth.do",true,sendData,'json',
				function(data){
					if(data.res_code == '0'){
						//未授权显示按钮
						$("#createVerificationButton").show();
						$("#code-div").show();
						$("#j_verification_code").attr("placeholder", "请按演示安装验证器");
					}else{
						//已经授权隐藏按钮
						$("#createVerificationButton").hide();
						$("#code-div").hide();
						$("#j_verification_code").attr("placeholder", "请打开验证器获取动态码");
					}
				},function(){},function(){
					BUI.Message.Alert("checkIsAuth[ajax请求出错了]!",'error');
				}
			);
		}
	}
	
	function createVerificationCode(){
		var j_username = $("#j_username").val();
		var sendData = null;
		if(j_username == null || j_username == "" || typeof(j_username) == 'undefined'){
			BUI.Message.Alert("账号不能为空！",'warning');
			return false;
		}else{
			sendData = {'j_username':j_username}
		}
		forumConsole.ajaxCall('POST',contextPath+"/createVerificationCode.do",true,sendData,'json',
			function(data){
				if(data.res_code == '1'){
					$("#code_iframe").attr("src",data.oth_data);
					$("#code-div").show();
				}else{
					$("#code-div").hide();
					BUI.Message.Alert(data.res_msg,'error');
					return;
				}
			},function(){},function(){
				BUI.Message.Alert("createVerificationCode[ajax请求出错了]!",'error');
			}
		);
	}
</script>