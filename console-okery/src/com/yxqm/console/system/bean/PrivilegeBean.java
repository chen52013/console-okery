package com.yxqm.console.system.bean;

import java.util.List;

public class PrivilegeBean extends SysBaseBean {
	private static final long serialVersionUID = -1482507358230445109L;
	private String privilege_id;
	private String privilege_name;
	private String privilege_code;
	private String privilege_desc;
	private String[] privilege_content;
	private List<PrivilegeResourceRefBean> privilegeResourceRefBeans;
	private String crt_date;

	public List<PrivilegeResourceRefBean> getPrivilegeResourceRefBeans() {
		return this.privilegeResourceRefBeans;
	}

	public void setPrivilegeResourceRefBeans(List<PrivilegeResourceRefBean> privilegeResourceRefBeans) {
		this.privilegeResourceRefBeans = privilegeResourceRefBeans;
	}

	public String[] getPrivilege_content() {
		return this.privilege_content;
	}

	public void setPrivilege_content(String[] privilege_content) {
		this.privilege_content = privilege_content;
	}

	public String getPrivilege_id() {
		return this.privilege_id;
	}

	public void setPrivilege_id(String privilege_id) {
		this.privilege_id = privilege_id;
	}

	public String getPrivilege_name() {
		return this.privilege_name;
	}

	public void setPrivilege_name(String privilege_name) {
		this.privilege_name = privilege_name;
	}

	public String getPrivilege_code() {
		return this.privilege_code;
	}

	public void setPrivilege_code(String privilege_code) {
		this.privilege_code = privilege_code;
	}

	public String getPrivilege_desc() {
		return this.privilege_desc;
	}

	public void setPrivilege_desc(String privilege_desc) {
		this.privilege_desc = privilege_desc;
	}

	public String getCrt_date() {
		return this.crt_date;
	}

	public void setCrt_date(String crt_date) {
		this.crt_date = crt_date;
	}
}