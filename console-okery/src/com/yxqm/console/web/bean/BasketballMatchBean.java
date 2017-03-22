package com.yxqm.console.web.bean;


/**
 * 篮球实体类
 * kober
 */
public class BasketballMatchBean extends SysBaseBean {
    
    private static final long serialVersionUID = 1L;
    private String match_id; 				//比赛ID
    private String match_num; 				//比赛编号
    private String match_time; 				//比赛时间
    private String match_status; 			//比赛状态
    private String match_score;				//比赛分数
	private String sum_score;				//比赛总得分
	private String home_team;				//主队名
	private String home_id;					//主队id
    private String guest_team;				//客队名
    private String guest_id;				//客队id
    private String league_name;				//联赛名称
    private String guess;					//预测
    private String win; 					//胜
    private String draw;					//平
    private String lost;					//负
	private String first_result;			//系统预测结果
	private String last_result;				//比赛结果
	private String std_win_keli;			//胜 凯利值
	private String std_draw_keli;			//平 凯利值
	private String std_lost_keli;			//负 凯利值
	private String home_score;				//主队得分
	private String guest_score;				//客队得分
	private String crt_time;				//创建时间
	private String outcome;					//比赛胜负
	private String outcome_odds;			//胜负赔率
	private String let_points;				//让分胜负
	private String let_points_odds;			//让分赔率
	private String score_diff;				//比赛分差
	private String score_diff_odds;			//分差赔率
	private String big_small_score;			//比赛大小分
	private String big_small_score_odds;	//大小分赔率
	private String start_time;				//比赛开始时间
	private String end_time;				//比赛结束时间
	private String team_name;				//球队名称
	private int count;						//近count场
	private String is_true;					//区分历史对阵

	public String getIs_true() {
		return is_true;
	}

	public void setIs_true(String is_true) {
		this.is_true = is_true;
	}

	public String getHome_id() {
		return home_id;
	}

	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}

	public String getGuest_id() {
		return guest_id;
	}

	public void setGuest_id(String guest_id) {
		this.guest_id = guest_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getHome_score() {
		return home_score;
	}

	public void setHome_score(String home_score) {
		this.home_score = home_score;
	}

	public String getGuest_score() {
		return guest_score;
	}

	public void setGuest_score(String guest_score) {
		this.guest_score = guest_score;
	}

	public String getCrt_time() {
		return crt_time;
	}

	public void setCrt_time(String crt_time) {
		this.crt_time = crt_time;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getOutcome_odds() {
		return outcome_odds;
	}

	public void setOutcome_odds(String outcome_odds) {
		this.outcome_odds = outcome_odds;
	}

	public String getLet_points() {
		return let_points;
	}

	public void setLet_points(String let_points) {
		this.let_points = let_points;
	}

	public String getLet_points_odds() {
		return let_points_odds;
	}

	public void setLet_points_odds(String let_points_odds) {
		this.let_points_odds = let_points_odds;
	}

	public String getScore_diff() {
		return score_diff;
	}

	public void setScore_diff(String score_diff) {
		this.score_diff = score_diff;
	}

	public String getScore_diff_odds() {
		return score_diff_odds;
	}

	public void setScore_diff_odds(String score_diff_odds) {
		this.score_diff_odds = score_diff_odds;
	}

	public String getBig_small_score() {
		return big_small_score;
	}

	public void setBig_small_score(String big_small_score) {
		this.big_small_score = big_small_score;
	}

	public String getBig_small_score_odds() {
		return big_small_score_odds;
	}

	public void setBig_small_score_odds(String big_small_score_odds) {
		this.big_small_score_odds = big_small_score_odds;
	}

	public String getFirst_result() {
		return first_result;
	}

	public void setFirst_result(String first_result) {
		this.first_result = first_result;
	}

	public String getLast_result() {
		return last_result;
	}

	public void setLast_result(String last_result) {
		this.last_result = last_result;
	}

	public String getStd_win_keli() {
		return std_win_keli;
	}

	public void setStd_win_keli(String std_win_keli) {
		this.std_win_keli = std_win_keli;
	}

	public String getStd_draw_keli() {
		return std_draw_keli;
	}

	public void setStd_draw_keli(String std_draw_keli) {
		this.std_draw_keli = std_draw_keli;
	}

	public String getStd_lost_keli() {
		return std_lost_keli;
	}

	public void setStd_lost_keli(String std_lost_keli) {
		this.std_lost_keli = std_lost_keli;
	}

	public String getSum_score() {
		return sum_score;
	}

	public void setSum_score(String sum_score) {
		this.sum_score = sum_score;
	}

    public String getMatch_id() {
		return match_id;
	}

	public void setMatch_id(String match_id) {
		this.match_id = match_id;
	}

	public String getMatch_num() {
		return match_num;
	}

	public void setMatch_num(String match_num) {
		this.match_num = match_num;
	}

	public String getMatch_time() {
		return match_time;
	}

	public void setMatch_time(String match_time) {
		this.match_time = match_time;
	}

	public String getMatch_status() {
		return match_status;
	}

	public void setMatch_status(String match_status) {
		this.match_status = match_status;
	}

	public String getMatch_score() {
		return match_score;
	}

	public void setMatch_score(String match_score) {
		this.match_score = match_score;
	}

	public String getHome_team() {
		return home_team;
	}

	public void setHome_team(String home_team) {
		this.home_team = home_team;
	}

	public String getGuest_team() {
		return guest_team;
	}

	public void setGuest_team(String guest_team) {
		this.guest_team = guest_team;
	}

	public String getLeague_name() {
		return league_name;
	}

	public void setLeague_name(String league_name) {
		this.league_name = league_name;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getLost() {
		return lost;
	}

	public void setLost(String lost) {
		this.lost = lost;
	}

}
