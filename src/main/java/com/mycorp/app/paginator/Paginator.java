package com.mycorp.app.paginator;

import java.util.List;

public interface Paginator<T> {
    boolean isFirstPage();

    boolean isLastPage();

    int getCurrentPage();

    void setCurrentPage(int page);

    int getPageSize();

    void setPageSize(int size);

    int getTotalPage();

    void setDataList(List<T> list);

    List<T> getDataPage();
}
