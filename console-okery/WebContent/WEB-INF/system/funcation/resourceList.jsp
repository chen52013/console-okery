<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>资源列表</title>

	<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>

</head>
<body>
	<ol class="breadcrumb  navbar-fixed-top">
	  <li class="active">资源列表</li>
	</ol>
	
	<form class="form-inline definewidth m20"  id="searchForm" name="searchForm" action="" method="post">    
    资源名称：
    <input type="text" name="username" id="username"class="abc input-default" placeholder="" value="">&nbsp;&nbsp;  
    <button type="button" class="btn btn-primary" type="button" onclick="return queryUser();">查询</button>&nbsp;&nbsp; 
    <button type="button" class="btn btn-success" id="addnew" onclick="return toAddUser();">新增资源</button>
</form>
	<div class="container definewidth m20" >
		<table id="searchTable">
			<tr>
				<th w_index="resource_name">资源名</th>
				<th w_index="resource_url">资源URL</th>
				<th w_index="resource_level">资源级别</th>
				<th w_index="resource_top">父资源</th>
				<th w_index="resource_code">资源编码</th>
				<th w_index="resource_order">资源序号</th>
				<th w_render="operate" >操作</th>
			</tr>
		</table>
	</div>

<div id="artDialog-gridForm" style="display: none;">
    <form id="gridForm" class="bsgrid_form"  >
        <table>
            <tr>
                <td class="formLabel"><span class="require">*</span>资源名:</td>
                <td class="formInput">
                    <input name="resouce_name" type="text" editAble="false" />
                </td>
            </tr>
            <tr>
                <td class="formLabel"><span class="require">*</span>资源URL:</td>
                <td class="formInput">
                    <input name="resource_url" type="text" editAble="false" />
                </td>
            </tr>
            <tr>
                <td class="formLabel"><span class="require">*</span>资源级别:</td>
                <td class="formInput">
                    <input name="resource_level" type="text" editAble="false" />
                </td>
            </tr>
            
             <tr>
                <td class="formLabel"><span class="require">*</span>资源类型:</td>
                <td class="formInput">
                   <select name="resource_type" editAble="false" edit-customAble="false"><option value="1">功能</option><option value="0">菜单</option></select>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center; border-left-width: 0; border-right-width: 0; border-bottom-width: 0;">
                    <input type="button" value="保存" onclick="doCommit();" />
                    &emsp;
                    <input type="button" value="退出" onclick="gridFormDialog.hidden();" />
                </td>
            </tr>
        </table>
    </form>
</div>

	<script type="text/javascript">
	var gridFormObject;
    var gridFormDialog;
       var gridObj;
       $(function () {
        gridObj = $.fn.bsgrid.init('searchTable', {
            url: '<%=request.getContextPath()%>/queryUserList.do',
				// autoLoad: false,
				pageSizeSelect : true,
				dataType: 'json',
				pagingLittleToolbar : true,
				pageSize : 10
			});
			$(':button').addClass('btn btn-mini');
			
			if ($('#artDialog-gridForm').length == 1) {
	            var gridFormHtml = $("#artDialog-gridForm").html();
	            $("#artDialog-gridForm").html('');
	            gridFormDialog = $.dialog({
	                id: 'artDialog-gridForm-dialog',
	                title: 'Form',
	                width: 400,
	                height: 30,
	                padding: '0px 0px 0px 0px',
	                content: gridFormHtml,
	                lock: true,
	                fixed: true,
	                ok: false,
	                okValue: false,
	                cancel: function () {
	                    gridFormDialog.hidden();
	                    return false;
	                },
	                visible: false
	            });
	        }

	        // grid form obj, note grid form should init after artDialog
	        gridFormObject = $.fn.bsgrid_form.init('gridForm', {});
		});

		function operate(record, rowIndex, colIndex, options) {
			return '<a href="#" onclick="toUserRole(\''
					+ gridObj.getRecordIndexValue(record, 'username')
					+ '\');">角色</a>';
		}
		
		function toUserRole(_username){
			alert(_username)
		}
		
		function queryUser(){
			gridObj.options.otherParames = $('#searchForm').serializeArray();
	        gridObj.page(1);
		}
		
		function toAddUser(){
			 $('#gridForm')[0].reset();
		        showFormDialog('add');
		}
		
		 function showFormDialog(type) {
		        gridFormObject.showForm(type);
		        if (type == 'view') {
		            $('#gridForm :button[value=Save]').hide();
		        } else {
		            $('#gridForm :button[value=Save]').show();
		        }
		        gridFormDialog.title(type);
		        // hide artDialog footer
		        $('div[aria-labelledby=d-title-artDialog-gridForm-dialog] .d-footer').hide();
		        gridFormDialog.visible();
		    }
		
	</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/plugins/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/builds/merged/bsgrid.all.min.js"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/plugins/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/plugins/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/artDialog/override/artDialog.plugin.override.en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/artDialog/override/artDialog.plugin.override.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bsgrid/artDialog/override.pop.js"></script>
</body>
</html>


