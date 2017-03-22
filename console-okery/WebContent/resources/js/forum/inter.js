var editInterDialog;
var addInterDialog;
function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	forumConsole.init('searchTable',webContextPath+'/queryInterList.do','json',10);
	editInterDialog = forumConsole.genDialog(webContextPath,'编辑收藏','800','auto','保存','关闭','editInterForm','edit_inter_dialog',editInter,editInterDialogCancel);
	addInterDialog =  forumConsole.genDialog(webContextPath,'添加收藏','800','auto','保存','关闭','addInterForm','add_inter_dialog',addInter,addInterDialogCancel);

}

function editInter(){
	if(!editInterForm.isValid()){//表单数据验证
		return;
	}
    $("#editInterForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/editInter.do",
        success: editSuccessCallBack,
        error: editErrorCallBack
    });
}

function editSuccessCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	$("#editInterForm").remove();
	editInterDialog.close();
	queryInterList();
}

function editErrorCallBack(){
	 alert("请求出错了!");
}

function editInterDialogCancel(){
	$("#editInterForm").remove();
	editInterDialog.close();
}

function addInter(){
	if(!addInterForm.isValid()){//表单数据验证
		return;
	}
    $("#addInterForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/addInter.do",
        success: successCallBack,
        error: errorCallBack
    });
}

function toGoJsoup(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	var sendData = {
		'inter_url':obj.inter_url
	};
	var operateMsg = '确定要抓取网址为'+ obj.inter_url+' 的数据吗';
	BUI.Message.Confirm(operateMsg, function(){
		forumConsole.ajaxCall('POST',contextPath+"/goJsoup.do",true,sendData,'json',successCallBack,errorCallBack);
	},'question');
}

function toSendEmail(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	var sendData = {
		'inter_url':obj.inter_url
	};
	var operateMsg = '确定要推送网址为'+ obj.inter_url+' 的数据吗';
	BUI.Message.Confirm(operateMsg, function(){
		forumConsole.ajaxCall('POST',contextPath+"/sendEmail.do",true,sendData,'json',successCallBack,errorCallBack);
	},'question');
}

function successCallBack(data){
	if(null == data || data == undefined){
		BUI.Message.Alert("请求失败了!",'warning');
		return;
	}
	if(data.res_code == '1'){
		BUI.Message.Alert(data.res_msg,'success');
	}else{
		BUI.Message.Alert(data.res_msg,'warning');
		return;
	}
	
	$("#addInterForm").remove();
	addInterDialog.close();
	queryInterList();
}

function queryInterList(){
    forumConsole.refreshGrid('searchForm');
}

function completeCallBack(){}

function errorCallBack(){
	 alert("请求出错了!");
}

function addInterDialogCancel(){
	$("#addInterForm").remove();
	addInterDialog.close();
}

function toAddInter(){
	addInterDialog.show();
	$("#add_inter_dialog").load(contextPath+"/toAddInter.do");
}

function toEditInter(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	editInterDialog.show();
	$("#edit_privilege_dialog").load(contextPath+"/toEditInter.do?inter_id="+obj.inter_id,
		function(){
	
		}
	);
}

function batchDeleteInter(){
	var objArray = gridObj.getCheckedRowsRecords();
	if(objArray.length == 0){
		BUI.Message.Alert('请选择需要删除的记录!','warning');
		return false;
	}
	var inter_ids ="";
	for(var i=0; i<objArray.length; i++){
		inter_ids += objArray[i].inter_id;
		if(i!= objArray.length - 1){
			inter_ids += ",";
		}
	}
	
	deleteInter(inter_ids);
}

function deleteInter(inter_ids){
	BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
		forumConsole.ajaxCall("POST",contextPath+"/deleteInter.do",true,{'inter_ids':inter_ids},"json",successCallBack,completeCallBack,errorCallBack);
    },'question');
}
