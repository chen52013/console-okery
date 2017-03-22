	var searchForm;//查询form
	var addResourceForm;//添加资源form
	var updateResourceForm;//修改资源form
	//查询表单渲染
	BUI.use(['bui/form'],function(Form){
		
		searchForm = new Form.HForm({
	    	srcNode : "#searchForm",
	  	});
		searchForm.render();
		
		addResourceForm = new Form.HForm({
	    	srcNode : "#addResourceForm",
	  	});
		addResourceForm.render();
		
		updateResourceForm = new Form.HForm({
	    	srcNode : "#updateResourceForm",
	  	});
		updateResourceForm.render();
		
	});
	
	function init(webContextPath) {
		$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryResourceList.do','json',10);
    }
	
	//资源类型处理
	function renderResourceType(record, rowIndex, colIndex, options){
		var resourceType = record.resource_type;
		if(parseInt(record.resource_type) == 0){
			resourceType = "功能";
		}else if(parseInt(record.resource_type) == 1){
			resourceType = "菜单";
		}else if(parseInt(record.resource_type) == 2){
			resourceType = "界面";
		}
		return resourceType;
	}
	
    //查询
    function queryResourceList() {
		if(!searchForm.isValid()){//表单数据验证
			return;
		}
    	forumConsole.refreshGrid('searchForm');
	}
    
    //跳转到添加资源页面
	function toAddResource(){
		window.location.href=contextPath+"/toAddResourcePage.do";
	}
	
	//添加资源信息
	function addResourceSubmit(){
		
		if(!addResourceForm.isValid()){//表单数据验证
			return;
		}
		
		$("#addResourceForm").submit(function () {   
			$("#addResourceForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/addResource.do",
		        success: addResourceSuccess,
		        error: addResourceError
		    });
		    return false;
		});
		$("#addResourceForm").submit();
	}
	
	function addResourceError(){
		 alert("addResource请求出错了!");
	}
	
	//跳转到修改资源信息页
	function toUpdateResource(resource_id){
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
		window.location.href=contextPath+"/toUpdateResourcePage.do?resource_id="+resource_id;
	}
	
	//修改用户信息
	function updateResourceSubmit(){
		
		if(!updateResourceForm.isValid()){//表单数据验证
			return;
		}
		$("#updateResourceForm").submit(function () {   
	        $("#updateResourceForm").ajaxSubmit({
	            type: "post",
	            
	            url: contextPath+ "/updateResource.do",
	            success: updateResourceSuccess,
	            error: updateResourceError
	        });
	        return false;
	    });
	    $("#updateResourceForm").submit();
	}
	
	function updateResourceError(){
		 alert("updateResource请求出错了!");
	}
	
	//删除
	function deleteResource(rowIndex) {
		var obj = gridObj.getRecord(rowIndex);//取得行记录数据
		var sendData = {
				'resource_id':obj.resource_id,
	        	'resource_code':obj.resource_code
		};
		var operateMsg = '确定要删除资源ID为'+ obj.resource_id+' 的记录吗？';
		
		BUI.Message.Confirm(operateMsg, function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteResource.do",true,sendData,'json',deleteResourceSuccess,deleteResourceComplete,deleteResourceError);
		},'question');
		
	}
	
	function deleteResourceSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	queryResourceList();
	}
	function deleteResourceComplete(){
	}
	function deleteResourceError(){
		 alert("deleteResource[ajax请求出错了]!");
	}
	
	//批量删除
	function batchDeleteResource(){
		var resource_id_selected = "";//id1,id2,id3,id4
		var resource_code_selected = "";//id1,id2,id3,id4
		var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要删除的行!','warning');
			return;
		}
		for(var i=0; i<objArray.length; i++){
			
			resource_id_selected += objArray[i].resource_id;
			resource_code_selected += objArray[i].resource_code;
			
			if(i!= objArray.length - 1){
				resource_id_selected += ",";
				resource_code_selected += ",";
			}
		}
		
		var sendData = {
				'resource_id':resource_id_selected,
	        	'resource_code':resource_code_selected
		};
		
		BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteResource.do",true,sendData,'json',batchDeleteResourceSuccess,batchDeleteResourceComplete,batchDeleteResourceError);
		},'question');

	}
	
	function batchDeleteResourceSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	
    	queryResourceList();
	}
	function batchDeleteResourceComplete(){
	}
	function batchDeleteResourceError(){
		 alert("batchDeleteResource[ajax请求出错了]!");
	}
	