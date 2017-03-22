<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">--%>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<form id="pkBasketballMatchForm" name="pkBasketballMatchForm" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
    <table class="table table-bordered table-hover">
        <tr>
            <input type="hidden" id="pk_match_id" name="match_id" value="" />
            <td style="width:15%;text-align:center;" id="pk_match_time"></td>
            <td style="text-align:center;" id="pk_home_team"></td>
            <td style="text-align:center;" id="pk_guest_team"></td>
        </tr>
        <tr>
            <td style="width:15%;text-align:center;">战绩</td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 40%;" id="home_win_count">
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="home_lost_count">
                    </div>
                </div>
            </td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-info" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 50%;" id="guest_win_count">
                    </div>
                    <div class="progress-bar progress-bar-warning" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="guest_lost_count">
                    </div>
                </div>
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td style="width:15%;text-align:center;">胜负比</td>--%>
            <%--<td style="text-align:center;">--%>
                <%--<div class="progress progress-striped active">--%>
                    <%--<div class="progress-bar progress-bar-success" role="progressbar"--%>
                         <%--aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"--%>
                         <%--style="width: 40%;" id="home_win_precent">--%>
                    <%--</div>--%>
                    <%--<div class="progress-bar progress-bar-danger" role="progressbar"--%>
                         <%--aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"--%>
                         <%--style="width: 20%;" id="home_lost_precent">--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</td>--%>
            <%--<td style="text-align:center;">--%>
                <%--<div class="progress progress-striped active">--%>
                    <%--<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="100"--%>
                         <%--aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="guest_win_precent">--%>
                    <%--</div>--%>
                    <%--<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="100"--%>
                         <%--aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="guest_lost_precent">--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td style="width:15%;text-align:center;">赢盘比</td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="home_let_win_precent">
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="home_let_lost_precent">
                    </div>
                </div>
            </td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;"  id="guest_let_win_precent">
                    </div>
                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;"  id="guest_let_lost_precent">
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td style="width:15%;text-align:center;">大小分比</td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="home_big_precent">
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="home_small_precent">
                    </div>
                </div>
            </td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="guest_big_precent">
                    </div>
                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="guest_small_precent">
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td style="width:15%;text-align:center;">平均分</td>
            <td colspan="3" style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="avg_home_score">
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="100"
                         aria-valuemin="0" aria-valuemax="100" style="width: 40%;" id="avg_guest_score">
                    </div>
                </div>
            </td>
        </tr>

        <tr>
            <td style="width:15%;text-align:center;">历史对阵(胜负)</td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 40%;" id="h_win_count">4胜
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="h_lost_count">2负
                    </div>
                </div>
            </td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-info" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 50%;" id="g_win_count">2胜
                    </div>
                    <div class="progress-bar progress-bar-warning" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="g_lost_count">5负
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td style="width:15%;text-align:center;">历史对阵(赢盘)</td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 40%;" id="h_let_win_count">4胜
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="h_let_lost_count">2负
                    </div>
                </div>
            </td>
            <td style="text-align:center;">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-info" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 50%;" id="g_let_win_count">2胜
                    </div>
                    <div class="progress-bar progress-bar-warning" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="g_let_lost_count">5负
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td style="width:15%;text-align:center;">历史对阵(大小分)</td>
            <td style="text-align:center;" colspan="3">
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 40%;" id="hg_big_count">4胜
                    </div>
                    <div class="progress-bar progress-bar-danger" role="progressbar"
                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                         style="width: 20%;" id="hg_small_count">2负
                    </div>
                </div>
            </td>
        </tr>
    </table>
</form>
<script>
    BUI.use('bui/calendar',function(Calendar){
        var datepicker = new Calendar.DatePicker({
            trigger:'.calendar-time',
            showTime:true,
            autoRender : true
        });
    });
</script>