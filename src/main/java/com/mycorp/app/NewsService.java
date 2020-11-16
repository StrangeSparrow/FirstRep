package com.mycorp.app;

import java.util.List;

public interface NewsService {
    List<News> fetchNews();
    News fetchSingleNews(int id);
    void addNews(News news);
    void deleteNews(int id);
    void editNews(News news);
}
