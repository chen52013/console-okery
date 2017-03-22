package com.yxqm.console.system.bean;

import java.io.Serializable;

public class SysBaseBean implements Serializable {
	private static final long serialVersionUID = -5037930064361122052L;
	protected static final int DEAULT_PER_PAGEROWS = 10;
	protected int curPage;
	protected int pageSize;
	protected int beginPage;
	protected int endPage;
	protected String is_pagination;

	public String isIs_pagination() {
		return this.is_pagination;
	}

	public void setIs_pagination(String is_pagination) {
		this.is_pagination = is_pagination;
	}

	public int getCurPage() {
		if (this.curPage == 0) {
			this.curPage = 1;
		}
		return this.curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		if (this.pageSize == 0) {
			this.pageSize = 10;
		}
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBeginPage() {
		this.beginPage = ((this.curPage - 1) * getPageSize());
		return this.beginPage < 0 ? 0 : this.beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		this.endPage = getPageSize();
		return this.endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}