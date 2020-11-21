package com.mycorp.app;

import java.sql.SQLException;
import java.util.List;

public interface NewsService {
    List<News> fetchNews() throws SQLException;
    News fetchSingleNews(int id) throws SQLException;
    void addNews(News news) throws SQLException;
    void deleteNews(int id) throws SQLException;
    void editNews(News news) throws SQLException;
}
