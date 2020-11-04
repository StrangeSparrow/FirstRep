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

        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("news.csv"), StandardCharsets.UTF_8)) {
            scanner.useDelimiter(Constants.DELIMETER);

            while (scanner.hasNext()) {
                String[] data;
                data = scanner.next().split(Constants.SPLIT);
                newsList.add(new News(data[0], data[1], data[2]));
            }
        }
        return newsList;
    }
}
