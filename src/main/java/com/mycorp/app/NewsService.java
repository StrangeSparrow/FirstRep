package com.mycorp.app;

import java.util.List;

public interface NewsService {
    List<News> fetchNews();
    News fetchSingleNews(int id);
    void addNews(String head, String briefly, String full);
    void addNews(News news);
    void deleteNews(int id);
    void editNews(int id);
    public void fillNews(List<News> newsList);
}
