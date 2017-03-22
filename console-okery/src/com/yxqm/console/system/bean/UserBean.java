package com.yxqm.console.system.bean;

import java.util.List;

public class UserBean extends SysBaseBean {
	private static final long serialVersionUID = -5873355872743514110L;
	private long userId;
	private String userName;
	private String loginName;
	private String loginPwd;
	private int status;
	private String crtDate;
	private String modDate;
	private String remark;
	private String userCode;
	private String mobile;
	private int extSysId;
	private int extSysObjectId;
	private String extSysObjectValue;
	private String u_user_id;
	private String u_user_name;
	private String u_login_name;
	private String u_login_pwd;
	private String u_status;
	private String crt_date;
	private String mod_date;
	private String u_remark;
	private String u_user_code;
	private String u_mobile;
	private String ext_sys_id;
	private String ext_sys_object_id;
	private String ext_sys_object_value;
	private String nick_name;
	private String user_mobile;
	private String is_expert;
	private String count;
	private List<String> userList;
	private String secret_key;
	private Integer is_auth;

	public String getIs_expert() {
		return this.is_expert;
	}

	public void setIs_expert(String is_expert) {
		this.is_expert = is_expert;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}

	public String getModDate() {
		return this.modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getU_user_id() {
		return this.u_user_id;
	}

	public void setU_user_id(String u_user_id) {
		this.u_user_id = u_user_id;
	}

	public String getU_user_name() {
		return this.u_user_name;
	}

	public void setU_user_name(String u_user_name) {
		this.u_user_name = u_user_name;
	}

	public String getU_login_name() {
		return this.u_login_name;
	}

	public void setU_login_name(String u_login_name) {
		this.u_login_name = u_login_name;
	}

	public String getU_login_pwd() {
		return this.u_login_pwd;
	}

	public void setU_login_pwd(String u_login_pwd) {
		this.u_login_pwd = u_login_pwd;
	}

	public String getU_status() {
		return this.u_status;
	}

	public void setU_status(String u_status) {
		this.u_status = u_status;
	}

	public String getCrt_date() {
		return this.crt_date;
	}

	public void setCrt_date(String crt_date) {
		this.crt_date = crt_date;
	}

	public String getMod_date() {
		return this.mod_date;
	}

	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}

	public String getU_remark() {
		return this.u_remark;
	}

	public void setU_remark(String u_remark) {
		this.u_remark = u_remark;
	}

	public String getU_user_code() {
		return this.u_user_code;
	}

	public void setU_user_code(String u_user_code) {
		this.u_user_code = u_user_code;
	}

	public String getU_mobile() {
		return this.u_mobile;
	}

	public void setU_mobile(String u_mobile) {
		this.u_mobile = u_mobile;
	}

	public String getExt_sys_id() {
		return this.ext_sys_id;
	}

	public void setExt_sys_id(String ext_sys_id) {
		this.ext_sys_id = ext_sys_id;
	}

	public int getExtSysId() {
		return this.extSysId;
	}

	public void setExtSysId(int extSysId) {
		this.extSysId = extSysId;
	}

	public int getExtSysObjectId() {
		return this.extSysObjectId;
	}

	public String getExtSysObjectValue() {
		return this.extSysObjectValue;
	}

	public String getExt_sys_object_id() {
		return this.ext_sys_object_id;
	}

	public String getExt_sys_object_value() {
		return this.ext_sys_object_value;
	}

	public void setExtSysObjectId(int extSysObjectId) {
		this.extSysObjectId = extSysObjectId;
	}

	public void setExtSysObjectValue(String extSysObjectValue) {
		this.extSysObjectValue = extSysObjectValue;
	}

	public void setExt_sys_object_id(String ext_sys_object_id) {
		this.ext_sys_object_id = ext_sys_object_id;
	}

	public void setExt_sys_object_value(String ext_sys_object_value) {
		this.ext_sys_object_value = ext_sys_object_value;
	}

	public String getNick_name() {
		return this.nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_mobile() {
		return this.user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<String> getUserList() {
		return this.userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public String getSecret_key() {
		return this.secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public Integer getIs_auth() {
		return this.is_auth;
	}

	public void setIs_auth(Integer is_auth) {
		this.is_auth = is_auth;
	}
}