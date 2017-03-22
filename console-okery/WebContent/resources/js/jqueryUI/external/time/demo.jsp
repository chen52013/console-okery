<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
    <title></title>
    <link href="<%=request.getContextPath()%>/resources/Js/jqueryUI/jquery-ui.css" rel="stylesheet">

    <link href="<%=request.getContextPath()%>/resources/Js/jqueryUI/external/time/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.css" type="text/css" />
    <link href="<%=request.getContextPath()%>/resources/Js/jqueryUI/external/time/jQuery-Timepicker-Addon/demos.css" rel="stylesheet" type="text/css" />

  <script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
   <script src="<%=request.getContextPath()%>/resources/Js/jqueryUI/jquery-ui.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/Js/jqueryUI/external/time/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.js" type="text/javascript"></script>

    <!--中文-->
    <script src="<%=request.getContextPath()%>/resources/Js/jqueryUI/localization/datepicker-zh-CN.js" type="text/javascript" charset="gb2312"></script>
    <script src="<%=request.getContextPath()%>/resources/Js/jqueryUI/localization/timepicker-zh-CN.js" type="text/javascript"></script>
    <script type="text/javascript">
        jQuery(function () {
            // 时间设置
            jQuery('#time').datetimepicker({
                timeFormat: "HH:mm:ss",
                dateFormat: "yy-mm-dd"
            });
            jQuery('#time2').datetimepicker({
                timeFormat: "HH:mm:ss",
                dateFormat: "yy-mm-dd"
            });

        });
    </script>
</head>
<body>

<ol class="breadcrumb  navbar-fixed-top">
	  <li class="active">查询付款列表</li>
	</ol>
	<div class="container definewidth m20">
		<form id="searchForm" name="searchForm" action="" method="post">
			<table class="table table-bordered table-hover">
				<tr>
					<td width="10%" class="tableleft">客户订单号</td>
					<td><input type="text" name="order_no" value="" /></td>
					
					<td class="tableleft">用户</td>
					<td><input type="text" name="userName" value="" /></td>
				</tr>
				<tr>
					<td class="tableleft">付款结果</td>
					<td>
					<select name="status" id="status" class="selectpicker" data-style="btn-primary">
					   <option value="" selected>请选择</option>
					   <option value="0">成功</option> 
					   <option value="1">失败</option> 
					</select>
					 </td>
						
					<td class="tableleft">付款时间</td>
					
					<td>
					 <input id="time" type="text" /></td>
				</tr>
				<tr>
					<td class="tableleft"></td>
					<td colspan="3">
						<button type="button" class="btn btn-success" type="button" onclick="return queryPayList();">查询</button>
					</td>
				</tr>
			</table>
		</form>
    </div>
   
    <input id="time2" type="text" />
</body>
</html>
