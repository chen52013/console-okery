function init(contextPath, user_id, recharge_type) {
	console.log('---------------');
	forumConsole.init('rechargeDetailTable', contextPath + '/queryRechargeListByUserId.do?recharge_type='+recharge_type+'&user_id='+user_id, 'json', 10,null,function(){},true,null,null);
}