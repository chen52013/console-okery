package com.yxqm.console.web.bean;

public class InterBean extends SysBaseBean {
    private static final long serialVersionUID = 1L;
    private String inter_id;
    private String inter_name;
    private Integer inter_type;
    private String inter_url;
    private String inter_desc;
    private String inter_crt_time;
    private String inter_mod_time;
    private String channel_name;
    private Integer inter_status;

    public String getInter_id() {
        return inter_id;
    }

    public void setInter_id(String inter_id) {
        this.inter_id = inter_id;
    }

    public String getInter_name() {
        return inter_name;
    }

    public void setInter_name(String inter_name) {
        this.inter_name = inter_name;
    }

    public Integer getInter_type() {
        return inter_type;
    }

    public void setInter_type(Integer inter_type) {
        this.inter_type = inter_type;
    }

    public String getInter_url() {
        return inter_url;
    }

    public void setInter_url(String inter_url) {
        this.inter_url = inter_url;
    }

    public String getInter_desc() {
        return inter_desc;
    }

    public void setInter_desc(String inter_desc) {
        this.inter_desc = inter_desc;
    }

    public String getInter_crt_time() {
        return inter_crt_time;
    }

    public void setInter_crt_time(String inter_crt_time) {
        this.inter_crt_time = inter_crt_time;
    }

    public String getInter_mod_time() {
        return inter_mod_time;
    }

    public void setInter_mod_time(String inter_mod_time) {
        this.inter_mod_time = inter_mod_time;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public Integer getInter_status() {
        return inter_status;
    }

    public void setInter_status(Integer inter_status) {
        this.inter_status = inter_status;
    }
}
