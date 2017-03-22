<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="matchKeliForm" name="matchKeliForm" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
    <table class="table table-bordered table-hover">
        <tr>
            <td class="tableleft">赛事id</td>
            <td>
                <input type="text" id="match_id" name="match_id" />
            </td>
            <td>
                <security:authorize url="${request.contextPath}/queryMatchKeliList.do">
                    <button type="button" class="btn btn-success"
                            onclick="return queryMatchKeliList();">查询</button>
                </security:authorize>
            </td>
        </tr>
    </table>
    <div class="col-xs-12" style="height:450px;border: 1px solid #CCCCCC;border-radius: 5px;" id="pie_div">
    </div>
</form>
