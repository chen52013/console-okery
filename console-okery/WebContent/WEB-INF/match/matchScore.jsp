<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%
    Date now = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(now);
    String curDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    cal.add(Calendar.DAY_OF_MONTH, 1);
    String nextDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
%>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<form id="matchScoreForm" name="matchScoreForm" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
    <table class="table table-bordered table-hover">
        <tr>
            <td class="tableleft">比赛时间</td>
            <td>
                <input type="text" id="start_time" name="start_time" value="<%=curDate %>" class="calendar calendar-time" data-rules="{date:true}"/>
                &nbsp;至&nbsp;
                <input type="text" id="end_time" name="end_time" value="<%=nextDate %>" class="calendar calendar-time" data-rules="{date:true}"/>
            </td>
        </tr>
        <tr>
            <td class="tableleft"></td>
            <td colspan="7">
                <security:authorize url="${request.contextPath}/queryMatchScoreTimeList.do">
                    <button type="button" class="btn btn-success"
                            onclick="return queryMatchScoreTimeList();">查询</button>
                </security:authorize>
            </td>
        </tr>
    </table>
    <div class="col-xs-12" style="height:380px;border: 1px solid #CCCCCC;border-radius: 5px;" id="pie_div">
    </div>
</form>
