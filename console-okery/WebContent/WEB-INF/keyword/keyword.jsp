<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>网址收藏夹大全</title>
    <jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
</head>
<body>
<ol class="breadcrumb  navbar-fixed-top">
    <li class="active">网址收藏夹大全</li>
</ol>
<div class="container definewidth m20">
    <form id="searchForm" name="searchForm" action="" method="post">
        <table class="table table-bordered table-hover">
            <tr>
                <td width="10%" class="tableleft">分类名称</td>
                <td>
                    <select id="type_name" name="type_name">

                    </select>
                </td>
                <td width="10%" class="tableleft">关键字</td>
                <td><input type="text" id="keyword_name" name="keyword_name"/></td>

                <td width="10%" class="tableleft">标题</td>
                <td><input type="text" id="title" name="title"/></td>

                <td width="10%" class="tableleft">url</td>
                <td><input type="text" id="url" name="url"/></td>

                <td width="10%" class="tableleft">状态</td>
                <td>
                    <select id="keyword_status" name="keyword_status">
                        <option value="10">有效</option>
                        <option value="11">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableleft"></td>
                <td colspan="10">
                    <button type="button" class="btn btn-success" onclick="return queryKeyWordList();">查询</button>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="container-fluid " style="padding-left:40px">
    <div class="pull-left" >
        <button type="button" class="btn btn-warning" id="addKeyWord" onclick="return toAddKeyWord();">添加收藏</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-warning" id="delKeyWord" onclick="return batchDeleteKeyWord();">删除收藏</button>
    </div>
</div>
<div class="container definewidth m10">
    <table id="searchTable">
        <tr>
            <th w_check="true" w_index="id" width="3%;"></th>
            <th w_index="type_name">分类名称</th>
            <th w_index="keyword_name">关键字</th>
            <th w_index="title">标题</th>
            <th w_render="url">url</th>
            <th w_render="keyword_status">状态</th>
            <th w_index="crt_time">创建时间</th>
            <th w_index="mod_time">编辑时间</th>
            <th w_render="operate">操作</th>
        </tr>
    </table>
</div>
<script type="text/javascript">
    var contextPath = '<%=request.getContextPath()%>';
    $(function () {
        init(contextPath);
        //加载分类
        forumConsole.ajaxCall('POST',contextPath+"/queryKeyWordList.do",true,null,'json',function(data){
            var typeData = data.data;
            var typeList = $("#type_name");
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
    function keyword_status(record, rowIndex, colIndex, options){
        var keyword_status = record.keyword_status;
        if(keyword_status == 10 || keyword_status == "10"){
            return "有效";
        }else if(keyword_status == 11 || keyword_status == "11"){
            return '无效';
        }
    }
    function url(record, rowIndex, colIndex, options){
        var url = record.url;
        if(url == null || url == "" || typeof(url) == 'undefined'){
            return "";
        }else{
            return "<a target='_blank' href='"+url+"'>"+url+"</a>";
        }
    }
    function operate(record, rowIndex, colIndex, options){
        return '<a href="#" onclick="toEditKeyword('+ rowIndex + ');">编辑</a>';
    }
</script>
<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/keyword.js"></script>
</body>
</html>