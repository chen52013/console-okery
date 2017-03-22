package com.yxqm.console.web.bean;

public class ForumResponse {
    private String res_code;
    private String res_msg;
    private String oth_data;

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getOth_data() {
        return oth_data;
    }

    public void setOth_data(String oth_data) {
        this.oth_data = oth_data;
    }
}
