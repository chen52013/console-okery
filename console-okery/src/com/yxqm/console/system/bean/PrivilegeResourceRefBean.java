package com.yxqm.console.system.bean;

public class PrivilegeResourceRefBean {
	private String ref_id;
	private String privilege_code;
	private String resource_code;
	private String resource_name;

	public String getResource_name() {
		return this.resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}

	public String getRef_id() {
		return this.ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getPrivilege_code() {
		return this.privilege_code;
	}

	public void setPrivilege_code(String privilege_code) {
		this.privilege_code = privilege_code;
	}

	public String getResource_code() {
		return this.resource_code;
	}

	public void setResource_code(String resource_code) {
		this.resource_code = resource_code;
	}
}