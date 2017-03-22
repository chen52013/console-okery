	var searchForm;//查询form
	
	//查询表单渲染
	BUI.use(['bui/form'],function(Form){
		
		searchForm = new Form.HForm({
	    	srcNode : "#searchForm",
	  	});
		searchForm.render();
	});
	
	function init(webContextPath) {
		$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryReplacePhraseList.do','json',20);
    }
	
	//状态处理
	function statusRender(record, rowIndex, colIndex, options){
		var status = record.status;
		if(parseInt(record.status) == 0){
			status = "不可用";
		}else if(parseInt(record.status) == 1){
			status = "可用";
		}
		return status;
	}
	
	//查询
	function queryReplacePhraseList(){
		if(!searchForm.isValid()){//表单数据验证
			return;
		}
		forumConsole.refreshGrid('searchForm');
	}
	
	//删除
	function deleteReplacePhrase(){
		var objArray = gridObj.getCheckedRowsRecords();
    	var sendData = null;
    	var replace_id_selected = "";
    	
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要操作的行!','warning');
			return;
		}
		for(var i=0; i<objArray.length; i++){
			replace_id_selected += objArray[i].replace_id;
			
			if(i!= objArray.length - 1){
				replace_id_selected += ",";
			}
		}
		
		sendData = {
				'replace_id':replace_id_selected
				};
		
		BUI.Message.Confirm("确定要删除所选的词组吗？", function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteReplacePhrase.do",true,sendData,'json',
					function(data){
						if(data.res_code == '1'){
							BUI.Message.Alert(data.res_msg,'success');
						}else{
							BUI.Message.Alert(data.res_msg,'error');
							return;
						}
						
						queryReplacePhraseList();
						
					},function(){},function(){
						alert("deleteReplacePhrase[ajax请求出错了]!");
					});
			
		},'question');
		
	}
	
	//跳转到新增替换词组页面
	function toAddReplacePhrasePage(){
		window.location.href=contextPath+"/toAddReplacePhrasePage.do";
	}
	
	//新增词组
	function addReplacePhraseSubmit(){
		var isAlert = false;
		
		$.each( $('table input'), function(i, input){
			if(null === input.value || "" === input.value || undefined === input.value){
				isAlert = true;
				return false;
			}
		});
		
		if(isAlert == true){
			BUI.Message.Alert('原文词组或替换词组不能为空','warning');
			return;
		}
		
		$("#addReplacePhraseForm").ajaxSubmit({
			type: "post",
			url: contextPath+ "/addReplacePhrase.do",
	        success: function(data){
	        	if(parseInt(data.res_code) == 1){
	    			BUI.Message.Alert(data.res_msg,function(){
	    				window.location.href=contextPath+"/toReplacePhrasePage.do";
	    			},'success');
	    		}else{
	    			BUI.Message.Alert(data.res_msg,'error');
	    			return;
	    		}
	        },
	        error: function(){
	        	alert("addReplacePhrase请求出错了!");
	        }
	    });
		
	}
	