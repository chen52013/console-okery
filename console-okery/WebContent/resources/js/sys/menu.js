	var searchForm;//查询form
	var addMenuForm;//添加菜单form
	var updateMenuForm;//修改菜单form
	//查询表单渲染
	BUI.use(['bui/form'],function(Form){
		
		searchForm = new Form.HForm({
	    	srcNode : "#searchForm",
	  	});
		searchForm.render();
		
		addMenuForm = new Form.HForm({
	    	srcNode : "#addMenuForm",
	  	});
		addMenuForm.render();
		
		updateMenuForm = new Form.HForm({
	    	srcNode : "#updateMenuForm",
	  	});
		updateMenuForm.render();
	});
	
	function init(webContextPath) {
		$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryMenuList.do','json',10);
		
		var sendData = null;
		//父菜单名下拉列表
		forumConsole.ajaxCall('POST',contextPath+"/queryParentMenuSelectList.do",true,sendData,'json',queryParentMenuSelectListSuccess,queryParentMenuSelectListComplete,queryParentMenuSelectListError);
		function queryParentMenuSelectListSuccess(data){
			var parentMenuData = data.data;
	    	var parentMenuList = $("#parent_id");
	    	
	    	parentMenuList.empty();//清空select下拉框
	    	var tmpOptOne = $("<option>");
	    	tmpOptOne.val("");
	    	tmpOptOne.text("全部");
	    	parentMenuList.append(tmpOptOne);
	    	for(var i=0;i<parentMenuData.length;i++){
		    	var tmpOpt = $("<option>");
		    	tmpOpt.val(parentMenuData[i].menu_id);
		    	tmpOpt.text(parentMenuData[i].menu_name);
		    	parentMenuList.append(tmpOpt);
		    }
		}
		function queryParentMenuSelectListComplete(){
		}
		function queryParentMenuSelectListError(){
			 alert("queryParentMenuSelectList[ajax请求出错了]!");
		}
    }
	
    //查询
    function queryMenuList() {
		if(!searchForm.isValid()){//表单数据验证
			return;
		}
    	forumConsole.refreshGrid('searchForm');
	}
    
    //父菜单名称处理
	function renderParentName(record, rowIndex, colIndex, options){
		var parent_id = record.parent_id;
		var parent_name = record.parent_name;
		if(parseInt(record.parent_id) == -1){
			parent_name = "无";
		}
		return parent_name;
	}
	
	//菜单级别处理
	function renderMenuLevel(record, rowIndex, colIndex, options){
		var menu_level = record.menu_level;
		var menuLevelName = "";
		if(parseInt(menu_level) == 0){
			menuLevelName = "一级菜单";
		}else if(parseInt(menu_level) == 1){
			menuLevelName = "二级菜单";
		}
		return menuLevelName;
	}
	
    //跳转到添加菜单页面
	function toAddMenu(){
		window.location.href=contextPath+"/toAddMenuPage.do";
	}
	
	//添加菜单信息
	function addMenuSubmit(){
		
		if(!addMenuForm.isValid()){//表单数据验证
			return;
		}
		
		$("#addMenuForm").submit(function () {   
			$("#addMenuForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/addMenu.do",
		        success: addMenuSuccess,
		        error: addMenuError
		    });
		    return false;
		});
		$("#addMenuForm").submit();
	}
	
	function addMenuError(){
		 alert("addMenu请求出错了!");
	}
	
	//跳转到修改菜单信息页
	function toUpdateMenu(menu_id){
	/*	var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要编辑的记录!','warning');
			return false;
		}
		if(objArray.length > 1){
			BUI.Message.Alert('只能选择一条记录进行操作!','warning');
			return false;
		}
		var obj = objArray[0];*/
		window.location.href=contextPath+"/toUpdateMenuPage.do?menu_id="+menu_id;
	}
	
	//修改菜单信息
	function updateMenuSubmit(){
		
		if(!updateMenuForm.isValid()){//表单数据验证
			return;
		}
		
		$("#updateMenuForm").submit(function () {   
			$("#updateMenuForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/updateMenu.do",
		        success: updateMenuSuccess,
		        error: updateMenuError
		    });
		    return false;
		});
		$("#updateMenuForm").submit();
	}
	
	function updateMenuError(){
		 alert("updateMenu请求出错了!");
	}
	
	//删除菜单
	function deleteMenu(rowIndex) {
		var obj = gridObj.getRecord(rowIndex);//取得行记录数据
		var sendData = {
				'menu_id':obj.menu_id
		};
		var operateMsg = '确定要删除菜单ID为'+ obj.menu_id+' 的记录吗？<br />（该菜单下的子菜单会一起被删除!）';
		
		BUI.Message.Confirm(operateMsg, function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteMenu.do",true,sendData,'json',deleteMenuSuccess,deleteMenuComplete,deleteMenuError);
		},'question');
		
	}
	
	function deleteMenuSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	queryMenuList();
	}
	function deleteMenuComplete(){
	}
	function deleteMenuError(){
		 alert("deleteMenu[ajax请求出错了]!");
	}
	
	//批量删除
	function batchDeleteMenu(){
		var menu_id_selected = "";//id1,id2,id3,id4
		var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要删除的行!','warning');
			return;
		}
		for(var i=0; i<objArray.length; i++){
			
			menu_id_selected += objArray[i].menu_id;
			
			if(i!= objArray.length - 1){
				menu_id_selected += ",";
			}
		}
		
		var sendData = {
				'menu_id':menu_id_selected
		};
		var operateMsg = '确定要删除选中的菜单吗？<br />（选中菜单的子菜单会一起被删除!）';
		
		BUI.Message.Confirm(operateMsg,function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteMenu.do",true,sendData,'json',batchDeleteMenuSuccess,batchDeleteMenuComplete,batchDeleteMenuError);
		},'question');

	}
	
	function batchDeleteMenuSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	
    	queryMenuList();
	}
	function batchDeleteMenuComplete(){
	}
	function batchDeleteMenuError(){
		 alert("batchDeleteUser[ajax请求出错了]!");
	}
	