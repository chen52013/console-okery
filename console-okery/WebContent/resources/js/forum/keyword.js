var editKeywordDialog;
var addKeywordDialog;
var searchForm;
var addKeywordForm;

//查询表单渲染
BUI.use(['bui/form'],function(Form){
    searchForm = new Form.HForm({
        srcNode : "#searchForm",
    });
    searchForm.render();
    addKeywordForm = new Form.HForm({
        srcNode : "#addKeywordForm",
    });
    addKeywordForm.render();
});

function init(webContextPath) {
    $(':button').addClass('btn btn-mini');
    forumConsole.init('searchTable',webContextPath+'/queryKeyWordList.do','json',10);
    editKeywordDialog = forumConsole.genDialog(webContextPath,'编辑网址收藏','800','auto','保存','取消','addKeywordForm','editKeywordDialog',editKeyword,editKeywordDialogCancel);
    addKeywordDialog =  forumConsole.genDialog(webContextPath,'添加网址收藏','800','auto','保存','取消','addKeywordForm','addKeywordDialog',addKeyword,addKeywordDialogCancel);
}
//查询
function queryKeyWordList(){
    forumConsole.refreshGrid('searchForm');
}
//添加网址收藏页面
function toAddKeyWord(){
    addKeywordDialog.show();
    $("#addKeywordDialog").load(contextPath+"/toAddKeyword.do",function(){
        //加载分类
        forumConsole.ajaxCall('POST',contextPath+"/queryKeyWordList.do",true,null,'json',function(data){
            var typeData = data.data;
            var typeList = $("#_type_name");
            typeList.empty();//清空select下拉框
            var tmpOptOne = $("<option>");
            tmpOptOne.val("");
            tmpOptOne.text("请选择");
            typeList.append(tmpOptOne);
            var typeNames = [];
            if(typeData != null && typeData.length>0){
                for(var i=0;i<typeData.length;i++){
                    var type_name = typeData[i].type_name;
                    if(type_name != null && type_name != "" && typeof(type_name) != 'undefined'){
                        typeNames.push(type_name);
                    }
                }
            }
            if(typeNames.length > 0 && typeNames != null){
                var res = [typeNames[0]];
                for(var j=1;j<typeNames.length;j++){
                    var repeat = false;
                    for(var k = 0; k < res.length; k++) {
                        if(typeNames[j] == res[k]){
                            repeat = true;
                            break;
                        }
                    }
                    if(!repeat){
                        res.push(typeNames[j]);
                    }

                }
                for(var l = 0; l < res.length; l++) {
                    var tmpOpt = $("<option>");
                    var type_name = res[l];
                    tmpOpt.val(type_name);
                    tmpOpt.text(type_name);
                    typeList.append(tmpOpt);
                }
            }
        },function(){},function(){
            BUI.Message.Alert("queryKeyWordList[ajax请求出错了]!",'error');
        });
    });
}
//添加网址收藏功能
function addKeyword(){
    if(!addKeywordForm.isValid()){//表单数据验证
        return;
    }
    $("#addKeywordForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/addKeyword.do",
        success: successCallBack,
        error: errorCallBack
    });
    $("#addKeywordForm").remove();
    addKeywordDialog.close();
    queryKeyWordList();
}
//编辑收藏夹页面
function toEditKeyword(rowIndex){
    var obj = gridObj.getRecord(rowIndex);
    editKeywordDialog.show();
    $("#editKeywordDialog").load(contextPath+"/toAddKeyword.do",
        function(){
            //加载分类
            forumConsole.ajaxCall('POST',contextPath+"/queryKeyWordList.do",true,null,'json',function(data){
                var typeData = data.data;
                var typeList = $("#_type_name");
                typeList.empty();//清空select下拉框
                var tmpOptOne = $("<option>");
                tmpOptOne.val("");
                tmpOptOne.text("请选择");
                typeList.append(tmpOptOne);
                var typeNames = [];
                if(typeData != null && typeData.length>0){
                    for(var i=0;i<typeData.length;i++){
                        var type_name = typeData[i].type_name;
                        if(type_name != null && type_name != "" && typeof(type_name) != 'undefined'){
                            typeNames.push(type_name);
                        }
                    }
                }
                if(typeNames.length > 0 && typeNames != null){
                    var res = [typeNames[0]];
                    for(var j=1;j<typeNames.length;j++){
                        var repeat = false;
                        for(var k = 0; k < res.length; k++) {
                            if(typeNames[j] == res[k]){
                                repeat = true;
                                break;
                            }
                        }
                        if(!repeat){
                            res.push(typeNames[j]);
                        }

                    }
                    for(var l = 0; l < res.length; l++) {
                        var tmpOpt = $("<option>");
                        var type_name = res[l];
                        tmpOpt.val(type_name);
                        tmpOpt.text(type_name);
                        typeList.append(tmpOpt);
                    }
                }
            },function(){},function(){
                BUI.Message.Alert("queryKeyWordList[ajax请求出错了]!",'error');
            });
            $("#_type_name").val(obj.type_name);
            $("#_keyword_name").val(obj.keyword_name);
            $("#_title").val(obj.title);
            $("#_keyword_url").val(obj.url);
            $("#_keyword_status").val(obj.keyword_status);
        }
    );
}
//编辑收藏夹功能
function editKeyword(){
    if(!addKeywordForm.isValid()){//表单数据验证
        return;
    }
    $("#addKeywordForm").ajaxSubmit({
        type: "post",
        url: contextPath+ "/editKeyword.do",
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
    $("#addKeywordForm").remove();
    editKeywordDialog.close();
    queryKeyWordList();
}

function editErrorCallBack(){
    alert("请求出错了!");
}

function editKeywordDialogCancel(){
    $("#addKeywordForm").remove();
    editKeywordDialog.close();
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

function completeCallBack(){}

function errorCallBack(){
    alert("请求出错了!");
}

function addKeywordDialogCancel(){
    $("#addKeywordForm").remove();
    addKeywordDialog.close();
}

function batchDeleteKeyWord(){
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
    deleteKeyword(inter_ids);
}

function deleteKeyword(inter_ids){
    BUI.Message.Confirm('确认要删除选中的记录吗？',function(){
        forumConsole.ajaxCall("POST",contextPath+"/deleteKeyword.do",true,{'inter_ids':inter_ids},"json",successCallBack,completeCallBack,errorCallBack);
    },'question');
}
