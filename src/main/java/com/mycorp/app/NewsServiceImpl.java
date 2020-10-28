package com.mycorp.app;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsServiceImpl implements NewsService{
    private final String DELIMETER = "!!!";
    private final String SPLIT = ";";
    private final static Logger logger = Logger.getLogger(NewsServiceImpl.class);

    public List<News> fetchNews(String filePath) {
        List<News> newsList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            scanner.useDelimiter(DELIMETER);

            while (scanner.hasNext()) {
                String[] data;
                data = scanner.next().split(SPLIT);
                newsList.add(new News(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        if (!newsList.isEmpty())
            return newsList;
        return null;
    }
}
