package com.yxqm.console.system.bean;

public class LogBean extends SysBaseBean {
	private static final long serialVersionUID = 1L;
	private Integer logId;
	private String optUser;
	private String optUserCode;
	private String optFun;
	private String optFunDesc;
	private String optTime;
	private String optDesc;
	private String optType;
	private Integer optRes;
	private String optTimeBegin;
	private String optTimeEnd;

	private String optBefore;
	private String optAfter;
	public String getOptBefore() {
		return optBefore;
	}

	public void setOptBefore(String optBefore) {
		this.optBefore = optBefore;
	}

	public String getOptAfter() {
		return optAfter;
	}

	public void setOptAfter(String optAfter) {
		this.optAfter = optAfter;
	}

	public String getOptParam() {
		return optParam;
	}

	public void setOptParam(String optParam) {
		this.optParam = optParam;
	}

	private String optParam;
	
	public String getOptUserCode() {
		return this.optUserCode;
	}

	public void setOptUserCode(String optUserCode) {
		this.optUserCode = optUserCode;
	}

	public Integer getOptRes() {
		return this.optRes;
	}

	public void setOptRes(Integer optRes) {
		this.optRes = optRes;
	}

	public String getOptTimeBegin() {
		return this.optTimeBegin;
	}

	public void setOptTimeBegin(String optTimeBegin) {
		this.optTimeBegin = optTimeBegin;
	}

	public String getOptTimeEnd() {
		return this.optTimeEnd;
	}

	public void setOptTimeEnd(String optTimeEnd) {
		this.optTimeEnd = optTimeEnd;
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOptUser() {
		return this.optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public String getOptFun() {
		return this.optFun;
	}

	public void setOptFun(String optFun) {
		this.optFun = optFun;
	}

	public String getOptFunDesc() {
		return this.optFunDesc;
	}

	public void setOptFunDesc(String optFunDesc) {
		this.optFunDesc = optFunDesc;
	}

	public String getOptTime() {
		return this.optTime;
	}

	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}

	public String getOptDesc() {
		return this.optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public String getOptType() {
		return this.optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}
}