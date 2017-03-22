<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="${request.contextPath}/WEB-INF/common/top.jsp"></jsp:include>
<form id="editBasketballMatchForm" name="editBasketballMatchForm" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
    <table class="table table-bordered table-hover">
        <tr>
            <td class="tableleft">比赛时间</td>
            <td>
                <input type="text" id="edit_match_time" name="match_time" value="" class="calendar calendar-time" data-rules="{date:true}"/>
            </td>
            <td class="tableleft">比赛id</td>
            <td>
                <input type="text" id="edit_match_id" name="match_id" value="" disabled/>
            </td>
        </tr>

        <tr>
            <td class="tableleft">比赛编号</td>
            <td>
                <input type="text" id="edit_match_num" name="match_num" value="" />
            </td>
            <td class="tableleft">比赛状态</td>
            <td>
                <select id="edit_match_status" name="match_status">
                    <option value="完">完</option>
                    <option value="未开始">未开始</option>
                    <option value="取消">取消</option>
                    <option value="第一节">第一节</option>
                    <option value="第二节">第二节</option>
                    <option value="第三节">第三节</option>
                    <option value="第四节">第四节</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="tableleft">主队</td>
            <td>
                <input type="text" id="edit_home_team" name="home_team" value="" />
            </td>
            <td class="tableleft">客队</td>
            <td>
                <input type="text" id="edit_guest_team" name="guest_team" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">主队得分</td>
            <td>
                <input type="text" id="edit_home_score" name="home_score" value="" />
            </td>
            <td class="tableleft">客队得分</td>
            <td>
                <input type="text" id="edit_guest_score" name="guest_score" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">联赛</td>
            <td>
                <select id="edit_league_name" name="league_name">

                </select>
            </td>
            <td class="tableleft">预测</td>
            <td>
                <input type="text" id="edit_guess" name="guess" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">比赛胜赔率</td>
            <td>
                <input type="text" id="edit_win" name="win" value="" />
            </td>
            <td class="tableleft">比赛负赔率</td>
            <td>
                <input type="text" id="edit_lost" name="lost" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">比赛胜负</td>
            <td>
                <input type="text" id="edit_outcome" name="outcome" value="" />
            </td>
            <td class="tableleft">比赛胜负赔率</td>
            <td>
                <input type="text" id="edit_outcome_odds" name="outcome_odds" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">让分胜负</td>
            <td>
                <input type="text" id="edit_let_points" name="let_points" value="" />
            </td>
            <td class="tableleft">让分胜负赔率</td>
            <td>
                <input type="text" id="edit_let_points_odds" name="let_points_odds" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">胜负差</td>
            <td>
                <input type="text" id="edit_score_diff" name="score_diff" value="" />
            </td>
            <td class="tableleft">胜负差赔率</td>
            <td>
                <input type="text" id="edit_score_diff_odds" name="score_diff_odds" value="" />
            </td>
        </tr>

        <tr>
            <td class="tableleft">大小分</td>
            <td>
                <input type="text" id="edit_big_small_score" name="big_small_score" value="" />
            </td>
            <td class="tableleft">大小分赔率</td>
            <td>
                <input type="text" id="edit_big_small_score_odds" name="big_small_score_odds" value="" />
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