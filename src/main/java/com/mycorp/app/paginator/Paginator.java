package com.mycorp.app.paginator;

import java.util.List;

public interface Paginator<T> {
    boolean isFirstPage();
    boolean isLastPage();
    int getCurrentPage();
    int getPageSize();
    int getTotalPage();
    void setCurrentPage(int page);
    void setPageSize(int size);
    void setDataList(List<T> list);
    List<T> getDataPage();
}
