package com.yxqm.console.system.bean;

public class UserRoleRefBean extends SysBaseBean {
	private static final long serialVersionUID = -2178471826397906515L;
	private String ref_id;
	private String user_code;
	private String role_code;

	public String getRef_id() {
		return this.ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getRole_code() {
		return this.role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
}