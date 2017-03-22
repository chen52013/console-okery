package com.yxqm.console.web.bean;

public class SysBaseBean implements java.io.Serializable {
    private static final long serialVersionUID = -5037930064361122052L;
    protected static final int DEAULT_PER_PAGEROWS = 10; //默认显示行数
    protected int curPage; //当前�?
    protected int pageSize; //每页显示的条�?
                            //limit beginPage, endPage
    protected int beginPage;
    protected int endPage;
    protected String is_pagination;

    public String isIs_pagination() {
        return is_pagination;
    }

    public void setIs_pagination(String is_pagination) {
        this.is_pagination = is_pagination;
    }

    public int getCurPage() {
        if (this.curPage == 0) {
            this.curPage = 1;
        }

        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        if (pageSize == 0) {
            pageSize = DEAULT_PER_PAGEROWS;
        }

        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginPage() {
        beginPage = (this.curPage - 1) * this.getPageSize();

        return (beginPage < 0) ? 0 : beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        endPage = this.getPageSize();

        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
