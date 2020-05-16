package com.frgk.flowable.entity;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PageEntity {
    /**
     * 总条数
     */
    private long totalSize;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 每页数据条数
     */
    private long pageSize;
    /**
     * 当前页码
     */
    private long nowPage;
    /**
     * 返回数据
     */
    private Object list;
    public PageEntity(){

    }

    public PageEntity(long totalSize, long totalPages, long pageSize, long nowPage, Object list) {
        this.totalSize = totalSize;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.nowPage = nowPage;
        this.list = list;
    }
}
