var add_user_layer;
var rechargeListGrid;

var searchForm;//查询form
//查询表单渲染
BUI.use(['bui/form'], function (Form) {
	searchForm = new Form.HForm({
		srcNode: "#searchForm",
	});
	searchForm.render();
});

function init(contextPath) {
	rechargeListGrid = forumConsole.init('rechargeTable', contextPath + '/querySuspiciousUserList.do', 'json', 10,null,function(){},true,null,null);
	add_user_layer = forumConsole.genDialog(contextPath,'添加可疑用户','800','450','确认','取消','add_suspicious_table','add_user_layer',add_user_layer_save,add_user_layer_close);
}

function add_user_layer_save(){
	var sendData = {
		user_name: $("#user_name").val(),
		acct_num: $("#acct_num").val()
	};
	forumConsole.ajaxCall('POST',contextPath+"/addNewSuspiciousUser.do",true,sendData,'json',function(data){
		if(data.success == 'true'){
			BUI.Message.Alert("添加成功",'info');
		}else{
			BUI.Message.Alert("添加失败",'error');
		}
	},function(){},function(){
		BUI.Message.Alert("AJAX请求出错了！",'error');
		return;
	});
	add_user_layer.close();
	$("#add_suspicious_table").remove();
}


function add_user_layer_close(){
	add_user_layer.close();
	$("#add_suspicious_table").remove();
}