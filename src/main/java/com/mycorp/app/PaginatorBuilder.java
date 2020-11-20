package com.mycorp.app;

import java.util.List;

public class PaginatorBuilder {
    private final Paginator paginator;

    public PaginatorBuilder() {
        paginator = new PaginatorImpl();
    }

    public PaginatorBuilder setSize(int size) {
        paginator.setPageSize(size);
        return this;
    }

    public PaginatorBuilder setCurrentPage(int page) {
        paginator.setCurrentPage(page);
        return this;
    }

    public PaginatorBuilder setDataList(List<?> list) {
        paginator.setDataList(list);
        return this;
    }

    public Paginator build() {
        return paginator;
    }
}
