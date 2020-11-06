package com.mycorp.app;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsServiceImpl implements NewsService{
    private final static Logger logger = Logger.getLogger(NewsServiceImpl.class);

    public List<News> fetchNews() {
        List<News> newsList = new ArrayList<>();

        String newsFileName = Config.getInstance().getNewsFileName();

        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(newsFileName), StandardCharsets.UTF_8)) {
            scanner.useDelimiter(Constants.DELIMETER);

            while (scanner.hasNext()) {
                String[] data;
                data = scanner.next().split(Constants.SPLIT);
                newsList.add(new News(data[0], data[1], data[2]));
            }
        }
        return newsList;
    }

    @Override
    public News fetchSingleNews(int id) {
        List<News> newsList = fetchNews();
        if (id > newsList.size()) {
            logger.error("Запрос несуществующей новости по id:" + id);
            throw new IllegalArgumentException();
        }

        return newsList.get(id);
    }
}
