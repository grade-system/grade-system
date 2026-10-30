package com.grade.util;

/**
 * 分页工具类
 */
public class PageUtil {

    private int pageNum;      // 当前页码
    private int pageSize;     // 每页记录数
    private int total;        // 总记录数
    private int totalPages;   // 总页数

    public PageUtil(int pageNum, int pageSize, int total) {
        this.pageNum = Math.max(pageNum, 1);
        this.pageSize = pageSize > 0 ? pageSize : 10;
        this.total = total;
        this.totalPages = (total + this.pageSize - 1) / this.pageSize;
        if (this.pageNum > this.totalPages && this.totalPages > 0) {
            this.pageNum = this.totalPages;
        }
    }

    /** 起始偏移量（用于 SQL LIMIT） */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    /** 是否有上一页 */
    public boolean hasPrev() {
        return pageNum > 1;
    }

    /** 是否有下一页 */
    public boolean hasNext() {
        return pageNum < totalPages;
    }

    public int getPageNum() { return pageNum; }
    public int getPageSize() { return pageSize; }
    public int getTotal() { return total; }
    public int getTotalPages() { return totalPages; }
}
