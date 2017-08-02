package com.mySBoot.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable{

    private static final long serialVersionUID = 7529681633436017185L;

    private int pageNo = 1;
    private int pageSize = 10;
    private List<T> rows = new ArrayList<T>();
    private long total = 0L;

    public Page() {
    }

    public Page(int pageSize) {
        this(1, pageSize);
    }

    public Page(int pageNo, int pageSize) {
        this(null, 0, pageSize, pageNo);
    }

    /**
     * 构造函数
     *
     * @param rows
     * @param total
     * @param pageSize
     * @param pageNo
     */
    public Page(List<T> rows, int total, int pageSize, int pageNo) {
        this.rows = rows;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo < 0 ? 1 : pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量, 默认为10.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始. 用于Mysql
     */
    public int getOffset() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始. 用于Oracle.
     */
    public int getStartRow() {
        return getOffset() + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置, 序号从1开始. 用于Oracle.
     */
    public int getEndRow() {
        return pageSize * pageNo;
    }

    /**
     * 是否最后一页.
     */
    @JsonIgnore
    public boolean isLastPage() {
        return pageNo == getTotalPages();
    }

    /**
     * 是否还有下一页.
     */
    @JsonIgnore
    public boolean isHasNextPage() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    @JsonIgnore
    public int getNextPage() {
        if (isHasNextPage()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否第一页.
     */
    @JsonIgnore
    public boolean isFirstPage() {
        return pageNo == 1;
    }

    /**
     * 是否还有上一页.
     */
    @JsonIgnore
    public boolean isHasPrePage() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    @JsonIgnore
    public int getPrePage() {
        if (isHasPrePage()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    @JsonIgnore
    public long getTotalPages() {
        if (total <= 0) {
            return 0;
        }
        long count = total / pageSize;
        if (total % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 取得List的第N页的subList
     *
     * @param list     要分页的list
     * @param pageSize
     * @param pageNo
     * @return List
     */
    public static <T> List<T> subList(List<T> list, int pageSize, int pageNo) {
        pageSize = (pageSize <= 0 ? 10 : pageSize);
        pageNo = (pageNo <= 0 ? 1 : pageNo);
        int begin = (pageSize * (pageNo - 1) > list.size() ? list.size() : pageSize * (pageNo - 1));
        int end = (pageSize * pageNo > list.size() ? list.size() : pageSize * pageNo);
        return list.subList(begin, end);
    }

    /**
     * 取得List的第N页的Page对象
     *
     * @param list     要分页的list
     * @param pageSize
     * @param pageNo
     * @return List
     */
    public static <T> Page<T> getByList(List<T> list, int pageSize, int pageNo) {
        List<T> l = subList(list, pageSize, pageNo);
        return new Page<T>(l, list.size(), pageSize, pageNo);
    }

}
