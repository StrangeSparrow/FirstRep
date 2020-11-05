package com.mycorp.app;

import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        List<News> listNews = new NewsServiceImpl().fetchNews();
        UtilNews.getNewsOnPage(listNews, 1, 1);

        System.out.println(UtilNews.getTotalPages());
        System.out.println(UtilNews.getCurrentPage());
    }
}
