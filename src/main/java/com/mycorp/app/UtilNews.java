package com.mycorp.app;

import java.util.ArrayList;
import java.util.List;

public class UtilNews {
    private static ThreadLocal<Integer> totalPage;
    private static ThreadLocal<Integer> currentPage;
    private static ThreadLocal<Integer> pageSize;

    public static List<News> getNewsOnPage(List<News> newsList, int page) {
        return getNewsOnPage(newsList, page, 10);
    }

    public static List<News> getNewsOnPage(List<News> newsList, int page, int size) {
        int totalPages = getTotalPages(newsList, size);

        if (page > totalPages)
            throw new IllegalArgumentException();

        int start = size * (page - 1);
        int finish = (start + size) > newsList.size()? newsList.size() : start + size;

        List<News> newsOnPage = new ArrayList<>();
        for (int i = start; i < finish; i++) {
            newsOnPage.add(newsList.get(i));
        }

        pageSize = new ThreadLocal<>();
        pageSize.set(size);
        currentPage = new ThreadLocal<>();
        currentPage.set(page);

        return newsOnPage;
    }

    private static int getTotalPages(List<?> list, int pageSize) {
        totalPage = new ThreadLocal<>();

        if (pageSize > list.size()) {
            totalPage.set(1);
            return 1;
        }

        int result = list.size() / pageSize;

        if (list.size() % pageSize > 0)
            result++;

        totalPage.set(result);
        return result;
    }

    public static int getTotalPages() {
        return totalPage.get();
    }

    public static int getCurrentPage() {
        return currentPage.get();
    }

    public static int getPageSize() {
        return pageSize.get();
    }
}
