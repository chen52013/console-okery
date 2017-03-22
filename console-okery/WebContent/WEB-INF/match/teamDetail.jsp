<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="security" uri='http://www.springframework.org/security/tags' %>
<%@page import="com.yxqm.console.web.action.home.HomeAction" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>球队走势图</title>
    <jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/resources/js/echarts/echarts.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/echarts/echartsUtil.js"></script>
</head>
<body>
<ol class="breadcrumb  navbar-fixed-top">
    <li class="active">球队走势图</li>
</ol>

<div class="container definewidth m20">
    <form id="teamForm" name="teamForm" action="" method="post">
        <table class="table table-bordered table-hover">
            <tr>
                <td class="tableleft">比赛时间</td>
                <td>
                    <input type="text" id="start_time" name="start_time" value="<%=curDate %>" class="calendar calendar-time" data-rules="{date:true}"/>
                    &nbsp;至&nbsp;
                    <input type="text" id="end_time" name="end_time" value="<%=nextDate %>" class="calendar calendar-time" data-rules="{date:true}"/>
                    <input type="hidden" id="home_id" value="${home_id}" />
                    <input type="hidden" id="guest_id" value="${guest_id}" />
                    <input type="hidden" id="league_name" value="${league_name}" />
                    <input type="hidden" id="_home_team" value="${home_team}" />
                    <input type="hidden" id="_guest_team" value="${guest_team}" />
                </td>
                <td class="tableleft">联赛</td>
                <td>
                    <select id="_league_name" name="league_name" onchange="queryTeamList()">

                    </select>
                </td>
                <td class="tableleft">球队</td>
                <td>
                    <select id="team" name="team">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tableleft"></td>
                <td colspan="5">
                    <button type="button" class="btn btn-success" onclick="return queryTeamDetailList(0);">查询</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success" onclick="return queryTeamDetailList('10');">最近10场</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success" onclick="return queryTeamDetailList('20');">最近20场</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="btn btn-primary" value="返回篮球推荐页面" onclick="returnBasketballListPage()" />
                </td>
            </tr>
        </table>
    </form>
</div>
<ol style="margin:0px auto;text-align:center;">
    <li id="result"></li>
</ol>
<div class="container definewidth m10">
    <div class="col-xs-12" style="height:400px;border: 1px solid #CCCCCC;border-radius: 5px;" id="pie_div">
    </div>
</div>

<jsp:include page="${request.contextPath}/WEB-INF/common/bottom.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/forumConsole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/info/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/forum/teamDetail.js"></script>
<script type="text/javascript">
    var contextPath = '<%=request.getContextPath()%>';
    $(function () {
        init(contextPath);
    })
</script>
</body>
</html>