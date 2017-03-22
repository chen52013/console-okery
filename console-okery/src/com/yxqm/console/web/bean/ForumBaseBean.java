package com.yxqm.console.web.bean;

public class ForumBaseBean {
    protected static final int DEAULT_PER_PAGEROWS = 10;
    protected int curPage;
    protected int pageSize;
    protected int beginPage;
    protected int endPage;
    protected boolean is_paging = false;

    public int getCurPage() {
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
        beginPage = (this.curPage - 1) * this.pageSize;

        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        endPage = this.pageSize;

        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isIs_paging() {
        return is_paging;
    }

    public void setIs_paging(boolean is_paging) {
        this.is_paging = is_paging;
    }
}
