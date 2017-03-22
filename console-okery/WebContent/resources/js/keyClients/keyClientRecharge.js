var recharge_layer;
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
	rechargeListGrid = forumConsole.init('rechargeTable', contextPath + '/queryKeyClientRechargeList.do', 'json', 5,null,function(){},true,null,null);
	recharge_layer = forumConsole.genDialog(contextPath,'充值明细','800','450','确认','关闭','rechargeDetailTable','recharge_layer',recharge_list_close,recharge_list_close);
}

function recharge_list_close(){
	recharge_layer.close();
	$("#rechargeDetailTable").remove();
}