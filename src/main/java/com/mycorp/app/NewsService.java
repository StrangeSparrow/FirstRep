package com.mycorp.app;

import java.util.List;

public interface NewsService {
    List<News> fetchNews(String filePath);
}
