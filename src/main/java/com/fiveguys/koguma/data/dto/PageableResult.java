package com.fiveguys.koguma.data.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableResult implements Pageable {
    private final int pageNumber;
    private final int pageSize;
    private final long total;

    public PageableResult(int pageNumber, int pageSize, long total) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return (long) pageNumber * pageSize;
    }

    @Override
    public Sort getSort() {
        // 정렬이 필요한 경우 정의
        return Sort.unsorted();
    }

    @Override
    public Pageable next() {
        return new PageableResult(pageNumber + 1, pageSize, total);
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}