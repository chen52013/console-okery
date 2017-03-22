(function($) {
	var forumConsole = {
		gridObj : null,
		init : function(search_table_id, query_url, query_data_type,
				per_page_size,_otherParames,_complete,is_edit,edit_event, edit_function) {
			gridObj = $.fn.bsgrid.init(search_table_id, {
				url : query_url,
				pageSizeSelect : true,
				dataType : query_data_type,
				pagingLittleToolbar : true,
				pageSize : per_page_size,
				displayBlankRows: false, // single grid setting
		        displayPagingToolbarOnlyMultiPages: true, // single grid setting
				otherParames:_otherParames,
				complete:_complete,
				extend: {
	                settings: {
	                    supportGridEdit: is_edit, // default false, if support extend grid edit
	                    supportGridEditTriggerEvent: (undefined == edit_event || edit_event==null)?null:edit_event
	                }
	            },
				event: {
	                customRowEvents: {
	                    click: function (record, rowIndex, trObj, options) {
	                       // alert('click trObj:\n' + trObj.html());
	                    	var checkObj =  gridObj.getCell(rowIndex, 1);
	                    	var selectRow = gridObj.getSelectedRow();
	                    	if(selectRow.length == 0){
	                    		if(undefined != gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0] && null !=gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0]){
	                    			gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0].checked=false;
	                    		}
	                    	}else{
	                    		if(undefined != gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0] && null !=gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0]){
	                    			gridObj.getCell(rowIndex, 0)[0].getElementsByTagName('input')[0].checked=true;
	                    		}
	                    	}
	                    }
	                },
	                customCellEvents: {
	                    click: function (record, rowIndex, colIndex, tdObj, trObj, options) {
	                       // alert('click tdObj:\n' + tdObj.html());
	                    }
	                },
	                customCellEditEvents: {
	                	click: edit_function
	                }
	            }
			});
			
			return gridObj;
		},
		refreshGrid : function(searchFormId,_otherParams){
			if(null != searchFormId && undefined !=searchFormId ){
				gridObj.options.otherParames = $('#'+searchFormId).serializeArray();
			}else if(null != _otherParams && undefined != _otherParams){
				gridObj.options.otherParames = _otherParams;
			}
			
	        gridObj.page(1);
		},
		refreshGridPage : function(searchFormId,pageNum){
			if(null === pageNum || '' === pageNum || undefined == pageNum){
				pageNum = 1;
			}
			gridObj.options.otherParames = $('#'+searchFormId).serializeArray();
	        gridObj.page(pageNum);
		},
		refreshPage : function(){
			gridObj.refreshPage();
		},
		genDialog : function(contextPath,titleName,width,height,saveButtonName,closeButtonName,dialogFormId,divId,saveCallBack,cancelCallBack){
		var dialogObj;
			BUI.use(['bui/overlay','bui/data','bui/form'],function(Overlay,Data,Form){
				dialogObj = new Overlay.Dialog({
					 title:titleName,
					 width:width,
					 height:height,
					 buttons:[
				              {
				                text:saveButtonName,
				                elCls : 'button button-primary',
				                handler :function(){
				                	saveCallBack(contextPath);
				                }
				              },{
				                text:closeButtonName,
				                elCls : 'button',
				                handler :cancelCallBack
				              }
				            ],
					 bodyContent:"<div id=\""+divId+"\">Loadding...</div>",
					 mask:true,
					 closeAction:'hide',
					 cancel:cancelCallBack
				});
			 });
			
		   return dialogObj;
		},
		genDetailDialog : function(contextPath,titleName,width,height,closeButtonName,dialogFormId,divId,cancelCallBack){
			var dialogObj;
			BUI.use(['bui/overlay','bui/data','bui/form'],function(Overlay,Data,Form){
				dialogObj = new Overlay.Dialog({
					 title:titleName,
					 width:width,
					 height:height,
					 buttons:[
				              {
				                text:closeButtonName,
				                elCls : 'button',
				                handler :cancelCallBack
				              }
				            ],
					 bodyContent:"<div id=\""+divId+"\">Loadding...</div>",
					 mask:true,
					 closeAction:'hide',
					 cancel:cancelCallBack
				});
			 });
			
		   return dialogObj;
		},
		ajaxCall:function(httpRequestType,httpUrl,async,sendData,resDatatype,successCallBack,completeCallBack,errorCallBack){
			$.ajax({
		        //提交数据的类型 POST GET
		        type:httpRequestType,
		        //提交的网址
		        url:httpUrl,
		        async:async,
		        //提交的数据
		        data:sendData,
		        //返回数据的格式
		        datatype: resDatatype,//"xml", "html", "script", "json", "jsonp", "text".
		        //成功返回之后调用的函数             
		        success:successCallBack,
		        complete:completeCallBack,
		         //调用出错执行的函数
		         error:errorCallBack       
		     });
		}
	};
	window.forumConsole = forumConsole;

})(window.jQuery);