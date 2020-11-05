package com.mycorp.app;

import java.util.ArrayList;
import java.util.List;

public class PaginatorImpl<T> implements Paginator<T> {
    private int pageSize;
    private int currentPage;
    private int totalPage;
    private List<T> dataList;

    public PaginatorImpl(){
        pageSize = 1;
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
    }

    @Override
    public List<T> getDataPage() {
        int offset = pageSize * (currentPage - 1);

        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            resultList.add(dataList.get(i + offset));
        }

        return resultList;
    }

    private void findTotalPage() {
        if (pageSize > dataList.size()) {
            totalPage = 1;
            return;
        }

        int result = dataList.size() / pageSize;
        if (dataList.size() % pageSize > 0)
            result++;

        totalPage = result;
    }
}
