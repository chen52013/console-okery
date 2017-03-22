package com.yxqm.console.web.bean;


/**
 * �����Ƽ�
 * @author Administrator
 *
 */
public class MatchBean extends SysBaseBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String match_id; 
    private String match_name; 
    private String match_title; 
    private String match_time; 
    private String match_type; 
    private String home_team; 
    private String away_team; 
    private String is_true; 
    private String match_result;
    private String push_result; 
    private String match_desc; 
    private String crt_time;
    private String mod_time;
    private Double match_money;
    private String match_summary;

    public Double getMatch_money() {
        return match_money;
    }

    public void setMatch_money(Double match_money) {
        this.match_money = match_money;
    }

    public String getMatch_summary() {
        return match_summary;
    }

    public void setMatch_summary(String match_summary) {
        this.match_summary = match_summary;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getIs_true() {
        return is_true;
    }

    public void setIs_true(String is_true) {
        this.is_true = is_true;
    }

    public String getMatch_result() {
        return match_result;
    }

    public void setMatch_result(String match_result) {
        this.match_result = match_result;
    }

    public String getPush_result() {
        return push_result;
    }

    public void setPush_result(String push_result) {
        this.push_result = push_result;
    }

    public String getMatch_desc() {
        return match_desc;
    }

    public void setMatch_desc(String match_desc) {
        this.match_desc = match_desc;
    }

    public String getCrt_time() {
        return crt_time;
    }

    public void setCrt_time(String crt_time) {
        this.crt_time = crt_time;
    }

    public String getMod_time() {
        return mod_time;
    }

    public void setMod_time(String mod_time) {
        this.mod_time = mod_time;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }
}
