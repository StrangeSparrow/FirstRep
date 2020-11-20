package com.mycorp.app;

import java.util.List;

public class PaginatorImpl<T> implements Paginator<T> {
    private int pageSize;
    private int currentPage;
    private int totalPage;
    private List<T> dataList;

    public PaginatorImpl(){
        pageSize = 5;
        currentPage = 1;
        totalPage = 1;
    }

    @Override
    public boolean isFirstPage() {
        return currentPage == 1;
    }

    @Override
    public boolean isLastPage() {
        return currentPage == totalPage;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getTotalPage() {
        return totalPage;
    }

    @Override
    public void setCurrentPage(int page) {
        if (page > totalPage)
            throw  new IllegalArgumentException();
        currentPage = page;
    }

    @Override
    public void setPageSize(int size) {
        pageSize = size;
        findTotalPage();
    }

    @Override
    public void setDataList(List<T> list) {
        dataList = list;
        findTotalPage();
    }

    @Override
    public List<T> getDataPage() {
        if (dataList == null)
            return null;

        int offset = pageSize * (currentPage - 1);

        return dataList.subList(offset, (Math.min(offset + pageSize, dataList.size())));
    }

    private void findTotalPage() {
        if (dataList == null || pageSize > dataList.size()) {
            totalPage = 1;
            return;
        }

        int result = dataList.size() / pageSize;
        if (dataList.size() % pageSize > 0)
            result++;

        totalPage = result;
    }
}
