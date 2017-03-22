	var searchForm;//查询form
	var addUserForm;//添加用户form
	var updateUserForm;//修改用户form
	var userPrizeDialog;//派奖
	var userRechargeDialog;//充值
	var userDeductionDialog;//扣款
	var userPrizeForm;
	var userRechargeForm;
	var userDeductionForm;
	var assignPrivilegeToUserDialog;//给用户分配权限dialog
	var userGroupDialog;//用户分组
	var userGroupForm;
	
	var expertSetForm;
	var expertSetDialog;
	var userBlackForm;
	var userBlackDialog;
	
	//查询表单渲染
	BUI.use(['bui/form'],function(Form){
		
		searchForm = new Form.HForm({
	    	srcNode : "#searchForm",
	  	});
		searchForm.render();
		
		addUserForm = new Form.HForm({
	    	srcNode : "#addUserForm",
	  	});
		addUserForm.render();
		
		updateUserForm = new Form.HForm({
	    	srcNode : "#updateUserForm",
	  	});
		updateUserForm.render();
		
		userGroupForm = new Form.HForm({
	    	srcNode : "#userGroupForm",
	  	});
		userGroupForm.render();
		
		expertSetForm = new Form.HForm({
	    	srcNode : "#expertSetForm",
	  	});
	    
		expertSetForm.render();
	});
	
	function init(webContextPath) {
		$(':button').addClass('btn btn-mini');
		forumConsole.init('searchTable',webContextPath+'/queryUserDetailList.do','json',10);
		userPrizeDialog = forumConsole.genDialog(webContextPath,'用户派奖','600','400','派发','关闭','userPrizeForm','userPrizeDialog',function(){
			if(!userPrizeForm.isValid()){//表单数据验证
				return;
			}
			$("button[class='button button-primary']").attr("disabled","disabled");
			$("#userPrizeForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/userPrize.do",
		        success: function(data){
		        	if(data.res_code == '1'){
		        		userPrizeDialog.close();
		    			BUI.Message.Alert(data.res_msg,'success');
		    			$("button[class='button button-primary']").removeAttr('disabled');
		    		}else{
		    			BUI.Message.Alert(data.res_msg,'error');
		    			$("button[class='button button-primary']").removeAttr('disabled');
		    			return;
		    		}
		        },
		        error: function(){
		        	BUI.Message.Alert("派发奖金异常",'error');
		        	$("button[class='button button-primary']").removeAttr('disabled');
	    			return;
		        }
		    });
		},function(){
			userPrizeDialog.close();
		});

		userRechargeDialog = forumConsole.genDialog(webContextPath,'用户充值','920','500','充值','关闭','userRechargeForm','userRechargeDialog',function(){
			if(!userRechargeForm.isValid()){//表单数据验证
				return;
			}
			$("button[class='button button-primary']").attr("disabled","disabled");
			$("#userRechargeForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/recharge.do",
		        success: function(data){
		        	if(data.res_code == '1'){
		        		userRechargeDialog.close();
		    			BUI.Message.Alert(data.res_msg,'success');
		    			$("button[class='button button-primary']").removeAttr('disabled');
		    		}else{
		    			BUI.Message.Alert(data.res_msg,'error');
		    			$("button[class='button button-primary']").removeAttr('disabled');
		    			return;
		    		}
		        },
		        error: function(){
		        	BUI.Message.Alert("充值金额异常",'error');
		        	$("button[class='button button-primary']").removeAttr('disabled');
	    			return;
		        }
		    });
		},function(){
			userRechargeDialog.close();
		});

		userBlackDialog = forumConsole.genDialog(webContextPath,'用户黑名单','400','160','确定','关闭','userBlackForm','userBlackDialog',function(){
			//获取复选框里面的值
			var str = document.getElementsByName("checkbox");
			var objarray = str.length;
			//获取选中的复选框讲他们存入checkboxList
			var checkboxList="";
			for (var i = 0;i < objarray; i++){
				if(str[i].checked == true){
					checkboxList += str[i].value+",";
				}
			}
			var user_id = $("#user_id").val();
			var sendData = {
				"type":checkboxList,
				"user_id":user_id
			};
			forumConsole.ajaxCall('POST',contextPath+"/userBlack.do",false,sendData,'json',function(data){
				if(null == data || data == undefined){
					BUI.Message.Alert("请求失败了!",'warning');
					return;
				}
				if(typeof data == 'string'){
					data = eval("("+data+")");
				}
				if(data.res_code == '1'){
					$("#userBlackForm").remove();
					userBlackDialog.close();//关闭dialog
					BUI.Message.Alert(data.res_msg,function(){
						forumConsole.refreshPage();
					},'success');
				}else{
					BUI.Message.Alert(data.res_msg,'error');
				}
			},function(){},function(){
				alert("userBlack[ajax请求出错了]!");
			});
		},function(){
			userBlackDialog.close();
		});

		userDeductionDialog = forumConsole.genDialog(webContextPath,'用户扣款','600','400','扣款','关闭','userDeductionForm','userDeductionDialog',function(){
			if(!userDeductionForm.isValid()){//表单数据验证
				return;
			}
			$("button[class='button button-primary']").attr("disabled","disabled");
			$("#userDeductionForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/userDeduction.do",
		        success: function(data){
		        	if(data.res_code == '1'){
		        		userDeductionDialog.close();
		    			BUI.Message.Alert(data.res_msg,'success');
		    		}else{
		    			BUI.Message.Alert(data.res_msg,'error');
		    			return;
		    		}
		        	$("button[class='button button-primary']").removeAttr('disabled');
		        },
		        error: function(){
		        	BUI.Message.Alert("用户扣款异常",'error');
	    			return;
	    			$("button[class='button button-primary']").removeAttr('disabled');
		        }
		    });
		},function(){
			userDeductionDialog.close();
		});

		assignPrivilegeToUserDialog = forumConsole.genDialog(webContextPath,'用户权限配置','600','530','保存','关闭','assignPrivilegeToUserForm','assignPrivilegeToUserDialog',function(){
			var privilegeCode = "";
			var privilegeCodeStr = "";
			var userId = $("#userId").val();
			var circles_id = $("#circles_id").val();
			var old_circles_id = $("#old_circles_id").val();
			$("input[name='ch']:checked").each(function(){
				privilegeCode+=$(this).val()+",";
	        });
			privilegeCodeStr = (privilegeCode.toString()).substring(0, (privilegeCode.toString()).length-1);
			if(privilegeCodeStr.length == 0){
				privilegeCodeStr = "";
			}
			var sendData = {
		        	'user_id':userId,
		        	'privilege_code':privilegeCodeStr,
		        	'obj_id':circles_id,
		        	'old_obj_id':old_circles_id
			};
			forumConsole.ajaxCall('POST',contextPath+"/addUserPrivilege.do",true,sendData,'json',
					function(data){

						if(data.res_code == '1'){
							BUI.Message.Alert(data.res_msg,function(){
								assignPrivilegeToUserDialog.close();
								forumConsole.refreshPage();
							},'success');
						}else{
							BUI.Message.Alert(data.res_msg,'error');
							return;
						}

					},function(){},function(){
						alert("addUserPrivilege请求出错了!");
					});

		},function(){
			assignPrivilegeToUserDialog.close();
		});

		userGroupDialog =  forumConsole.genDialog(webContextPath,'用户分组设置','500','300','保存','关闭','userGroupForm','userGroupDialog',function(){
			//确定操作
			if(!userGroupForm.isValid()){//表单数据验证
				return;
			}

			$("#userGroupForm").ajaxSubmit({
	            type: "post",
	            url: contextPath+ "/addUserGroup.do",
	            success: function(data){
	            	if(null == data || data == undefined){
	        			BUI.Message.Alert("请求失败了!",'warning');
	        			return;
	        		}
	        		if(typeof data == 'string'){
	        			data = eval("("+data+")");
	        		}

	        		if(data.res_code == '1'){
	        			$("#userGroupForm").remove();
	        			userGroupDialog.close();

	        			BUI.Message.Alert(data.res_msg,function(){
	        				forumConsole.refreshGrid();
	        			},'success');
	        		}else{
	        			BUI.Message.Alert(data.res_msg,'error');
	        		}
	            },
	            error: function(){
	            	BUI.Message.Alert("请求失败了!",'error');
        			return;
	            }
	        });
		},function(){
			$("#userGroupForm").remove();
			userGroupDialog.close();
		});

		expertSetDialog = forumConsole.genDialog(webContextPath,'专家设置','510','410','保存','关闭','expertSetForm','expertSetDialog',function(){
			//保存操作
			var user_id = $('#edit_user_id').val();
			var expert_level = $('#edit_expert_level').val();
			var is_hot = $('#edit_is_hot').val();
			var order_seq = $('#edit_order_seq').val();
			//获取分成比例的值
			var prorate = $('#prorate').val();
			//获取复选框里面的值
			var str = document.getElementsByName("checkbox");
			var objarray = str.length;

			//获取选中的复选框讲他们存入checkboxList
			var checkboxList="";

			//获取未选中的复选框讲他们存入noList
			var noList="";
			for (var i = 0;i < objarray; i++){
				if(str[i].checked == true){
					checkboxList += str[i].value+",";
				}else{
					noList += str[i].value+",";
				}
			}
			if(!expertSetForm.isValid()){//表单数据验证
				return;
			}

			var sendData = {
					'user_id':user_id,
		        	'expert_level':expert_level,
		        	'is_hot':is_hot,
		        	'order_seq':order_seq,
		        	'checkbox':checkboxList,
		        	'noList':noList,
		        	'prorate':prorate
			};
			forumConsole.ajaxCall('POST',contextPath+"/expertSet.do",true,sendData,'json',function(data){
				if(null == data || data == undefined){
        			BUI.Message.Alert("请求失败了!",'warning');
        			return;
        		}
        		if(typeof data == 'string'){
        			data = eval("("+data+")");
        		}
				if(data.res_code == '1'){
					$("#expertSetForm").remove();
			    	expertSetDialog.close();//关闭dialog

					BUI.Message.Alert(data.res_msg,function(){
        				forumConsole.refreshPage();
        			},'success');
				}else{
					BUI.Message.Alert(data.res_msg,'error');
				}
			},function(){},function(){
				alert("expertSet[ajax请求出错了]!");
			});
		},function(){
			//退出操作
			$("#expertSetForm").remove();
			expertSetDialog.close();
		});

    }
	
	//状态处理
	function renderStatus(record, rowIndex, colIndex, options){
		var userStatus = record.u_status;
		if(parseInt(record.u_status) == 0){
			userStatus = "已启用";
		}else if(parseInt(record.u_status) == 1){
			userStatus = "<font color=\"gray\">已禁用</font>";
		}
		return userStatus;
	}
	
    //查询
    function queryGameUserList() {
    	var reg = new RegExp("^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$");
		
		var u_mobile = $('#u_mobile').val();
		
    	if(u_mobile!="" && !reg.test(u_mobile)){
			BUI.Message.Alert('请正确输入手机号码!','warning');
			return;
		}
		if(!searchForm.isValid()){//表单数据验证
			return;
		}
    	forumConsole.refreshGrid('searchForm');
	}

	//查询
	function queryUserDetailList() {
		var reg = new RegExp("^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$");

		var u_mobile = $('#u_mobile').val();

		if(u_mobile!="" && !reg.test(u_mobile)){
			BUI.Message.Alert('请正确输入手机号码!','warning');
			return;
		}
		/*if(!searchForm.isValid()){//表单数据验证
			return;
		}*/
		forumConsole.refreshGrid('searchForm');
	}

	function queryAutoUserDetailList() {
		forumConsole.refreshGrid('searchForm');
	}


    //跳转到添加用户页面
	function toAddUser(){
		window.location.href=contextPath+"/toAddGameUserPage.do";
	}
	
	//添加用户信息
	function addUserSubmit(){
		
		if(!addUserForm.isValid()){//表单数据验证
			return;
		}
		
		$("#addUserForm").submit(function () {   
			$("#addUserForm").ajaxSubmit({
				type: "post",
				url: contextPath+ "/addUser.do",
		        success: addUserSuccess,
		        error: addUserError
		    });
		    return false;
		});
		$("#addUserForm").submit();
	}
	
	function addUserError(){
		 alert("addUser请求出错了!");
	}
	
	//跳转到修改用户信息页
	function toUpdateUser(u_user_id){
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
		window.location.href=contextPath+"/toUpdateGameUserPage.do?u_user_id="+u_user_id;
	}
	
	//修改用户信息
	function updateUserSubmit(){
		
		if(!updateUserForm.isValid()){//表单数据验证
			return;
		}
		$("#updateUserForm").submit(function () {   
	        $("#updateUserForm").ajaxSubmit({
	            type: "post",
	            
	            url: contextPath+ "/updateUser.do",
	            success: updateUserSuccess,
	            error: updateUserError
	        });
	        return false;
	    });
	    $("#updateUserForm").submit();
	}
	
	function updateUserError(){
		 alert("updateUser请求出错了!");
	}
	
    //删除
	function deleteUser(rowIndex) {
		var obj = gridObj.getRecord(rowIndex);//取得行记录数据
		var sendData = {
				'u_user_id':obj.u_user_id,
	        	'user_code':obj.user_code
		};
		var operateMsg = '确定要删除用户ID为'+ obj.u_user_id+' 的记录吗？';
		
		BUI.Message.Confirm(operateMsg, function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteGameUser.do",true,sendData,'json',deleteUserSuccess,deleteUserComplete,deleteUserError);
		},'question');
		
	}
	
	function deleteUserSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,'success');
		}else{
			BUI.Message.Alert(data.res_msg,'failed');
			return;
		}
    	queryGameUserList();
	}
	function deleteUserComplete(){
	}
	function deleteUserError(){
		 alert("deleteUser[ajax请求出错了]!");
	}
	
	//批量删除
	function batchDeleteUser(){
		var u_user_id_selected = "";//id1,id2,id3,id4
		//var role_code_selected = "";//id1,id2,id3,id4
		var user_code_selected = "";//id1,id2,id3,id4
		var objArray = gridObj.getCheckedRowsRecords();
		if(objArray.length == 0){
			BUI.Message.Alert('请选择需要删除的行!','warning');
			return;
		}
		for(var i=0; i<objArray.length; i++){
			
			u_user_id_selected += objArray[i].u_user_id;
			//role_code_selected += objArray[i].role_code;
			user_code_selected += objArray[i].user_code;
			
			if(i!= objArray.length - 1){
				u_user_id_selected += ",";
				//role_code_selected += ",";
				user_code_selected += ",";
			}
		}
		/*console.log(u_user_id_selected);
		console.log(role_code_selected);
		console.log(user_code_selected);*/
		
		var sendData = {
				'u_user_id':u_user_id_selected,
	        	'user_code':user_code_selected//,
	        	//'role_code':role_code_selected
		};
		
		BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
			
			forumConsole.ajaxCall('POST',contextPath+"/deleteGameUser.do",true,sendData,'json',batchDeleteUserSuccess,batchDeleteUserComplete,batchDeleteUserError);
		},'question');

	}
	
	function batchDeleteUserSuccess(data){
    	if(data.res_code == '1'){
			BUI.Message.Alert(data.res_msg,function(){
				queryGameUserList();
			},'success');
		}else{
			BUI.Message.Alert(data.res_msg,'error');
			return;
		}
	}
	function batchDeleteUserComplete(){
	}
	function batchDeleteUserError(){
		 alert("batchDeleteUser[ajax请求出错了]!");
	}

	//跳转到派奖界面
	function toPrizePage(_user_code,_nick_name){
		userPrizeDialog.show();
		$("#userPrizeDialog").load(contextPath+"/toPrizePage.do",
				function(){
			$("#prize_user_code").val(_user_code);
			$("#prize_obj").html(_nick_name);
		});
	}
	//跳转到充值界面
	function toRechargePage(_user_code,_nick_name){
		userRechargeDialog.show();
		$("#userRechargeDialog").load(contextPath+"/toRechargePage.do",
				function(){
			$("#recharge_user_code").val(_user_code);
			$("#recharge_obj").html(_nick_name);
			$("#right").show();
		});
	}
	
	//跳转到扣款界面
	function toDeductionPage(_user_code,_nick_name){
		userDeductionDialog.show();
		$("#userDeductionDialog").load(contextPath+"/toDeductionPage.do",
				function(){
			$("#deduction_user_code").val(_user_code);
			$("#deduction_obj").html(_nick_name);
			
			//查询用户游戏币余额
			var sendData = {
					'acc_name':_user_code,
					'item_type':'1001'
			}
			forumConsole.ajaxCall('POST',contextPath+"/queryUserGameCurrencyBalance.do",true,sendData,'json',function(data){
				if(data.res_code == '1'){
					$("#deduction_obj_balance").html(data.balance);
				}else{
					$("#deduction_obj_balance").html(data.balance);
				}
			},function(){},function(){
				BUI.Message.Alert("AJAX请求出错了！",'error');
				return;
			});
			
		});
	}
	
    function setExpert(_user_id){
    	var sendData = {
    			'user_id':_user_id
    	};
    	BUI.Message.Confirm('确定要申请为专家吗？',function(){
			forumConsole.ajaxCall('POST',contextPath+"/setExpert.do",true,sendData,'json',function(data){
				if(data.res_code == '1'){
					BUI.Message.Alert(data.res_msg,function(){
						queryGameUserList();
					},'success');
					
				}else{
					BUI.Message.Alert(data.res_msg,'error');
					return;
				}
			},function(){},function(){
				BUI.Message.Alert(data.res_msg,'error');
				return;
			});
		},'question');
    }
    
    function renderStatus(record, rowIndex, colIndex, options){
    	var status = record.status;// 10-有效 * 11-冻结	 * 12-无效
    	if(status == '10'){
    		return "有效";
    	}else if(status == '11'){
    		return "冻结";
    	}else if(status == '12'){
    		return "无效";
    	}else{
    		return "";
    	}
    }
    
    //黑名单
    function userBlack(rowIndex){
    	userBlackDialog.show();
    	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
    	var sendData = {
    		"user_id":obj.user_code,
    		"status":1
    	};
    	$("#userBlackDialog").load(contextPath+"/toUserblack.do",function(){
    		forumConsole.ajaxCall('POST',contextPath+"/queryUserBlackList.do",true,sendData,'json',function(data){
				var data = data.data;
				for(var i = 0;i<data.length;i++){
					var type = data[i].type;
					var str = document.getElementsByName("checkbox");
					var objarray = str.length;
					//获取选中的复选框讲他们存入checkboxList
					var checkboxList="";
					for (var j = 0;j < objarray; j++){
						if(type == str[j].value){ 
							str[j].checked = true;
						}
					}
				}
				$("#user_id").val(obj.user_code);
			},function(){},function(){
				return;
			});
    	});
    }
    
    function renderIsExpert(record, rowIndex, colIndex, options){
    	var is_expert = record.is_expert;//是否专家用户 0-否 1-是
    	if(is_expert == '0'){
    		return "否";
    	}else if(is_expert == '1'){
    		return "是";
    	}else{
    		return "";
    	}
    }
    
    function cancelExpert(_user_id){
    	var sendData = {
    			'user_id':_user_id
    	};
    	BUI.Message.Confirm('确定要取消专家吗？',function(){
			forumConsole.ajaxCall('POST',contextPath+"/cancelExpert.do",true,sendData,'json',function(data){
				if(data.res_code == '1'){
					BUI.Message.Alert(data.res_msg,function(){
						queryGameUserList();
					},'success');
				}else{
					BUI.Message.Alert(data.res_msg,'error');
					return;
				}
			},function(){},function(){
				BUI.Message.Alert(data.res_msg,'error');
				return;
			});
		},'question');
    }

	 //跳转到给用户分配权限页面
    function toAssignPrivilegeToUserPage(rowIndex){
    	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
    	
    	assignPrivilegeToUserDialog.show();
		$("#assignPrivilegeToUserDialog").load(contextPath+"/toAssignPrivilegeToUserPage.do", function(){
			$("#userId").val(obj.user_id);
			var sendCirclesData = null;
			//圈子下拉列表
			forumConsole.ajaxCall('POST',contextPath+"/queryCirclesSelectList.do",false,sendCirclesData,'json',function(data){
				var resultData = data.data;
				var circlesList = $("#circles_id");
				
				circlesList.empty();//清空select下拉框
				var tmpOptOne = $("<option>");
				for(var i=0;i<resultData.length;i++){
			    	var tmpOpt = $("<option>");
			    	tmpOpt.val(resultData[i].circles_id);
			    	tmpOpt.text(resultData[i].circles_name);
			    	circlesList.append(tmpOpt);
			    }
			},function(){},function(){
				alert("queryCirclesSelectList[ajax请求出错了]!");
			});
			
			var sendData = {
		        	'user_id':obj.user_id
			};
			forumConsole.ajaxCall('POST',contextPath+"/queryUserPrivilege.do",true,sendData,'json',function(data){
				var privilegeList = data.privilegeList;
				var userPrivilegeList = data.userPrivilegeList;
				//选中圈子
				if(null !== userPrivilegeList && userPrivilegeList.length != 0){
					var circles_id = parseInt(userPrivilegeList[0].obj_id);
					$("#circles_id").val(circles_id);
					$("#old_circles_id").val(circles_id);
				}
				
				var tr="";
				var mun=0;
				$("#assignPrivilegeToUserTable").empty();
				for(var i=0;i<privilegeList.length;i++){
					if(i+1!=privilegeList.length){
						tr+='<tr height="30">';
						tr+='<td width="100">';
						tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
						tr+='<td width="100" align="left"><input type="checkbox" name="ch" class="rol_'+(i+1)+'" value='+privilegeList[i+1].privilege_code+' />&nbsp;'+privilegeList[i+1].privilege_name+'</td>';
						tr+='</tr>';
					}else{
						tr+='<tr height="30">';
						tr+='<td width="100">';
						tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
						tr+='<td></td></tr>';
					}
					i++;
				}
				$("#assignPrivilegeToUserTable").append($(tr));
				
				if(userPrivilegeList.length!=0){
				     var chs = $("table[id=assignPrivilegeToUserTable] :checkbox");
				     for(var k=0;k<chs.length;k++){
				    	 for(var j=0;j<userPrivilegeList.length;j++){
				    		 if(chs[k].value==userPrivilegeList[j].privilege_code){
				    			 $(".rol_"+k).attr("checked",true);
				    			 break;
				    		 }
				    	 }
				     }
				}
				
			},function(){},function(){
				alert("queryUserPrivilege[ajax请求出错了]!");
			});
		});
    }
    
    //给用户分配权限时，选择圈子后查询该用户所选圈子的权限
    function loadPrivilegeByCircle(_this){
    	var userId = $("#userId").val();
    	var circle_id = _this.value;
    	
    	var sendData = {
	        	'user_id':userId,
	        	'obj_id':circle_id
		};
		forumConsole.ajaxCall('POST',contextPath+"/queryUserPrivilege.do",false,sendData,'json',function(data){
			var privilegeList = data.privilegeList;
			var userPrivilegeList = data.userPrivilegeList;
			
			var tr="";
			var mun=0;
			$("#assignPrivilegeToUserTable").empty();
			for(var i=0;i<privilegeList.length;i++){
				if(i+1!=privilegeList.length){
					tr+='<tr height="30">';
					tr+='<td width="100">';
					tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
					tr+='<td width="100" align="left"><input type="checkbox" name="ch" class="rol_'+(i+1)+'" value='+privilegeList[i+1].privilege_code+' />&nbsp;'+privilegeList[i+1].privilege_name+'</td>';
					tr+='</tr>';
				}else{
					tr+='<tr height="30">';
					tr+='<td width="100">';
					tr+='<input type="checkbox" name="ch" class="rol_'+i+'" value='+privilegeList[i].privilege_code+' />&nbsp;'+privilegeList[i].privilege_name+'</td>';
					tr+='<td></td></tr>';
				}
				i++;
			}
			$("#assignPrivilegeToUserTable").append($(tr));
			
			if(userPrivilegeList.length!=0){
			     var chs = $("table[id=assignPrivilegeToUserTable] :checkbox");
			     for(var k=0;k<chs.length;k++){
			    	 for(var j=0;j<userPrivilegeList.length;j++){
			    		 if(chs[k].value==userPrivilegeList[j].privilege_code){
			    			 $(".rol_"+k).attr("checked",true);
			    			 break;
			    		 }
			    	 }
			     }
			}
			
		},function(){},function(){
			alert("queryUserPrivilege[ajax请求出错了]!");
		});
    }
    
    function toUserGroupPage(_rowIndex){
    	var obj = gridObj.getRecord(_rowIndex);//取得行记录数据
    	
    	userGroupDialog.show();
		$("#userGroupDialog").load(contextPath+"/toUserGroupPage.do", function(){
			$("#add_user_id").val(obj.user_id);
			$("#add_user_code").val(obj.user_code);
			$("#add_group_type").val(obj.group_type);
		});
    }
    
    function expertLevel(record, rowIndex, colIndex, options){
    	if(record.expert_level == "1"){
    		return "初级专家";
    	}else if(record.expert_level == "2"){
    		return "中级专家";
    	}else if(record.expert_level == "3"){
    		return "高级专家";
    	}else if(record.expert_level == "4"){
    		return "资深专家";
    	}else{
    		return "--";
    	}
    }
    
    //专家标签
    function expertTag(record, rowIndex, colIndex, options){
    	var html = '<select multiple  class="form-control" name="expert_tag_'+rowIndex+'"> ';
    	var tagList = record.tagList;
    	if(null != tagList && ""!= tagList){
    		for (var i = 0; i < tagList.length; i++) {
    			html += "<option value='"+tagList[i].tag_name+"'>"+tagList[i].tag_name+"</option>";
    		}
    	}
    	html += '</select>';
		return html;
    }
    
    function isHot(record, rowIndex, colIndex, options){
    	if(record.is_hot == "0"){
    		return "否";
    	}else if(record.is_hot == "1"){
    		return "是";
    	}else{
    		return "未知";
    	}
    }
    
    //设置
    function expertSet(rowIndex){
    	expertSetDialog.show();
    	var obj = gridObj.getRecord(rowIndex);//取得行记录数据
    	var data = "";
    	$("#expertSetDialog").load(contextPath+"/toExpertSet.do",function(){
  		  	$('#edit_user_id').val(obj.user_id);
  		  	$('#edit_expert_level').val(obj.expert_level);
  		  	$('#edit_is_hot').val(obj.is_hot);
  		  	$('#edit_order_seq').val(obj.order_seq);
  		  	$('#prorate').val(obj.prorate);
  		  	//获取用户的专家标签
  		  	var tagList = obj.tagList;
  		  	//创建一个数组来存储标签code
  		  	var tag_cd = new Array();
  		  	for (var i = 0; i < tagList.length; i++) {
  		  		tag_cd[i] = tagList[i].tag_cd;
  		  	}
  		  	//查询复选框标签
  		  	forumConsole.ajaxCall('POST',contextPath+"/queryCheckboxList.do",true,data,'json',
  				function(data){
  					var html = "";
  					var result = data.data;
  					for (var i = 0; i < result.length; i++) {
  						var count = 0;
  						for (var j = 0; j < tag_cd.length; j++) {
  			  		  		if(tag_cd[j] == result[i].tag_cd){
  			  		  			html+="<input type=\"checkbox\" name=\"checkbox\" value=\""+result[i].tag_cd+"\" checked=\"checked\">"+result[i].tag_name;
  			  		  		}else if(tag_cd[j] != result[i].tag_cd){
  			  		  			count++;
  			  		  		}
  			  		  	}
  						if(count == tag_cd.length){
  							html+="<input type=\"checkbox\" name=\"checkbox\" value=\""+result[i].tag_cd+"\">"+result[i].tag_name;
  						}
  						html+="&nbsp;&nbsp;&nbsp;&nbsp;";
  					}
  					$("#checkbox").html(html);
  				}
  			);
    	});
    }
    
    //账务明细
    function toAccountDetailPage(user_id){
		//userDeductionDialog.show();

		var sendData = {
			'user_id':user_id
		}

		var url = contextPath+"/toAccountDetailsPage.do" + "?user_id="+user_id;
		var detailForm = $("#detailForm").attr("action",url);
		detailForm.submit();

		/*
		$("#userDeductionDialog").load(contextPath+"/toAccountDetailsPage.do",
			function(){

				//查询用户
				var sendData = {
					'user_id':user_id
				}
				forumConsole.ajaxCall('POST',contextPath+"/queryUserAccountDetailList.do",true,sendData,'json',function(data){
					if(data.res_code == '1'){

					}else{

					}
				},function(){},function(){
					BUI.Message.Alert("AJAX请求出错了！",'error');
					return;
				});

			});*/
    }
    
    function formsubmit() {
	    Today = new Date();
	    var NowHour = Today.getHours();
	    var NowMinute = Today.getMinutes();
	    var NowSecond = Today.getSeconds();
	    var mysec = (NowHour*3600)+(NowMinute*60)+NowSecond;
	    if((mysec-document.userPrizeForm.mypretime.value)>600){
	  		//600只是一个时间值，就是5分钟内禁止重复提交，值随便设
	  		document.userPrizeForm.mypretime.value=mysec;
	    }else{
	  		alert(' 按一次就够了，请勿重复提交！请耐心等待！谢谢合作！');
	  		return false;
	    }
//	    document.forms.userPrizeForm.submit();
	}