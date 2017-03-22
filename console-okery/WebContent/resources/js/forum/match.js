var editMatchDialog;
var addMatchDialog;
var jsoupMatchDialog;
function init(webContextPath) {
	$(':button').addClass('btn btn-mini');
	forumConsole.init('searchTable',webContextPath+'/queryMatchList.do','json',10);
	editMatchDialog = forumConsole.genDialog(webContextPath,'编辑推荐','800','auto','保存','关闭','editMatchForm','edit_inter_dialog',editMatch,editMatchDialogCancel);
	addMatchDialog =  forumConsole.genDialog(webContextPath,'添加推荐','800','auto','保存','关闭','addMatchForm','add_inter_dialog',addMatch,addMatchDialogCancel);
	jsoupMatchDialog =  forumConsole.genDialog(webContextPath,'抓取推荐','450','auto','抓取','关闭','jsoupMatchForm','jsoup_match_dialog',jsoupMatch,jsoupMatchDialogCancel);
}

function editMatch(){
	if(!editMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#editMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/editMatch.do",
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
	$("#editMatchForm").remove();
	editMatchDialog.close();
	queryMatchList();
}

function editErrorCallBack(){
	 alert("请求出错了!");
}

function editMatchDialogCancel(){
	$("#editMatchForm").remove();
	editMatchDialog.close();
}

function addMatch(){
	if(!addMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#addMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/addMatch.do",
        success: successCallBack,
        error: errorCallBack
    });
    $("#addMatchForm").remove();
	addMatchDialog.close();
	queryMatchList();
}

function jsoupMatch(){
	if(!jsoupMatchForm.isValid()){//表单数据验证
		return;
	}
    $("#jsoupMatchForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/jsoupMatch.do",
        success: successCallBack,
        error: errorCallBack
    });
    $("#jsoupMatchForm").remove();
	jsoupMatchDialog.close();
	queryMatchList();
}

function batchJsoupMatch(){
	jsoupMatchDialog.show();
	$("#jsoup_match_dialog").load(contextPath+"/toJsoupMatch.do");
}

function toSendEmail(rowIndex){
	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
	var sendData = {
		'match_name':obj.match_name,
		'match_title':obj.match_title,
		'match_time':obj.match_time,
		'match_desc':obj.match_desc
	};
	var operateMsg = '确定要推送'+ obj.home_team+' VS '+ obj.away_team+' 的比赛推荐吗';
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
	
}

function queryMatchList(){
    forumConsole.refreshGrid('searchForm');
}

function completeCallBack(){}

function errorCallBack(){
	 alert("请求出错了!");
}

function addMatchDialogCancel(){
	$("#addMatchForm").remove();
	addMatchDialog.close();inter
}

function jsoupMatchDialogCancel(){
	$("#jsoupMatchForm").remove();
	jsoupMatchDialog.close();
}

function toAddMatch(){
	addMatchDialog.show();
	$("#add_match_dialog").load(contextPath+"/toAddMatch.do");
}

function toEditMatch(rowIndex){
	var obj = gridObj.getRecord(rowIndex);
	editMatchDialog.show();
	$("#edit_privilege_dialog").load(contextPath+"/toEditMatch.do?inter_id="+obj.inter_id,
		function(){
	
		}
	);
}

function batchDeleteMatch(){
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
	
	deleteMatch(inter_ids);
}

function deleteMatch(inter_ids){
	BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
		forumConsole.ajaxCall("POST",contextPath+"/deleteMatch.do",true,{'inter_ids':inter_ids},"json",successCallBack,completeCallBack,errorCallBack);
    },'question');
}
