<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="addKeywordForm" name="addKeywordForm" class="form-horizontal" method="post">
    <table class="table table-bordered table-hover">
        <tr>
            <input id="id" name="id" type="hidden"/>

            <td class="tableleft">所属分类：</td>
            <td>
                <select id="_type_name" name="type_name">

                </select>
            </td>
        </tr>
        <tr>
            <td class="tableleft">关键字标签：</td>
            <td>
                <input id="_keyword_name" name="keyword_name" type="text" class="input-large" data-rules="{required:true}" data-messages="{required:'关键字标签不能为空'}">
            </td>
        </tr>
        <tr>
            <td class="tableleft">标题：</td>
            <td>
                <input id="_title" name="title" type="text" class="input-large" data-rules="{required:true}" data-messages="{required:'标题不能为空'}">
            </td>
        </tr>
        <tr>
            <td class="tableleft">url地址：</td>
            <td>
                <input id="_keyword_url" name="url" type="text" class="input-large" data-rules="{required:true}" data-messages="{required:'url地址不能为空'}">
                <input type="button" value="获取关键字" onclick="getKeyword()"/>
            </td>
        </tr>
        <tr>
            <td class="tableleft">状态值：</td>
            <td>
                <select id="_keyword_status" name="keyword_status">
                    <option value="10">有效</option>
                    <option value="11">无效</option>
                </select>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    function getKeyword(){
        var keyword_url = $("#_keyword_url").val();
        var other_params = {};
        if(keyword_url == "" || keyword_url == null || typeof(keyword_url) == 'undefined'){
            BUI.Message.Alert("url地址不能为空！",'warning');
            return false;
        }else {
            other_params.keyword_url = keyword_url;
            forumConsole.ajaxCall('POST', contextPath + "/getKeyWordList.do", true, other_params, 'json', function (data) {
                var keywordData = data.keyword;
                if(keywordData != null && keywordData != "" && typeof(keywordData) != 'undefined'){
                    $("#_keyword_name").val(keywordData.substring(0,keywordData.length-1));
                }else{
                    BUI.Message.Alert("未获取到关键字！",'warning');
                }
                var title = data.title;
                if(title != null && title != "" && typeof(title) != 'undefined'){
                    $("#_title").val(title);
                }else{
                    BUI.Message.Alert("未获取到标题！",'warning');
                }
            }, function () {
            }, function () {
                BUI.Message.Alert("getKeyWordList[ajax请求出错了]!", 'error');
            });
        }
    }
</script>
