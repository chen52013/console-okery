
	function init(webContextPath) {

		$(':button').addClass('btn btn-mini');
		var user_id = $("#user_id").val();
		var otherParams = {};
		if(user_id){
			otherParams.user_id = user_id;
		}

		forumConsole.init('searchTable',webContextPath+'/queryUserAccountDetailList.do','json',10,otherParams);
    }
	
	function changeValue(record, rowIndex, colIndex, options){
		var change_type = record.change_type;//0:增加  1:减少
		var change_value = record.change_value;
		if(parseInt(change_type) == 0){
			change_value = "+" + change_value;
		}else if(parseInt(change_type) == 1){
			change_value = "-" + change_value;
		}
		return change_value;
	}

	function queryUserAccountDetailList() {

		forumConsole.refreshGrid('searchForm');
	}

	function itemEvent(record, rowIndex, colIndex, options){
		var item_event = record.item_event;
		if(item_event == "GET_FREE_COIN"){
			item_event = "免费领取";
		}else if(item_event == "PRIZE_COIN"){
			item_event = "派奖";
		}else if(item_event == "TRADE_COIN"){
			item_event = "下单";
		}else if(item_event == "FG_GAME_PRIZE"){
			item_event = "猜拳派奖";
		}else if(item_event == "FG_GAME_TRADE"){
			item_event = "猜拳下注";
		}else if(item_event == "COIN_FROM_DIAMEND"){
			item_event = "钻石兑换游戏币";
		}else if(item_event == "BUY_SERVICE_PRESENT"){
			item_event = "购买服务赠送";
		}else if(item_event == "ADMIN_USER_OPT"){
			item_event = "后台操作";
		}
		return item_event;
	}