package net.sebinson.sample.web.common.bean.pagination;

import java.util.List;

/**
 * 分页封装函数
 */
public class Page {
    private List<?> records;

    private int page; // 对应Easyui当前页

    private int rows; // 对应Easyui每页显示记录数

    private long pageCount; // 总页数

    private int pageSize = 10; // 每页显示几条记录

    private int pageNow = 1; // 默认 当前页 为第一页

    private long rowCount; // 总记录数

    private int startPage; // 从第几条记录开始

    private final int pagecode = 5; // 规定显示5个页码

    private PageIndex pageIndex;

    public Page() {
    }

    public Page(int pageNow) {
        this.pageNow = pageNow;
        this.startPage = (this.pageNow - 1) * this.pageSize;
    }

    public Page(int pageNow, int pageSize) {
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        this.startPage = (this.pageNow - 1) * this.pageSize;
    }

    public List<?> getRecords() {
        return this.records;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNow() {
        return this.pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public PageIndex getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(PageIndex pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
        this.pageNow = page;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.pageSize = rows;
    }

    public int getPagecode() {
        return this.pagecode;
    }

    public long getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
        this.setPageCount(this.rowCount % this.pageSize == 0 ? this.rowCount / this.pageSize : this.rowCount
                / this.pageSize + 1);
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
        long startpage = this.pageNow - (this.pagecode % 2 == 0 ? this.pagecode / 2 - 1 : this.pagecode / 2);
        long endpage = this.pageNow + this.pagecode / 2;
        if (startpage < 1) {
            startpage = 1;
            if (this.pageCount >= this.pagecode) {
                endpage = this.pagecode;
            } else {
                endpage = this.pageCount;
            }
        }
        if (endpage > this.pageCount) {
            endpage = this.pageCount;
            if ((endpage - this.pagecode) > 0) {
                startpage = endpage - this.pagecode + 1;
            } else {
                startpage = 1;
            }
        }
        this.pageIndex = new Page.PageIndex(startpage, endpage);
    }

    public void setQueryResult(long rowCount, List<?> records) {
        this.setRowCount(rowCount);
        this.setRecords(records);
    }

    public class PageIndex {
        private long startindex;
        private long endindex;

        public PageIndex(long startindex, long endindex) {
            this.startindex = startindex;
            this.endindex = endindex;
        }

        public long getStartindex() {
            return this.startindex;
        }

        public void setStartindex(long startindex) {
            this.startindex = startindex;
        }

        public long getEndindex() {
            return this.endindex;
        }

        public void setEndindex(long endindex) {
            this.endindex = endindex;
        }
    }
}