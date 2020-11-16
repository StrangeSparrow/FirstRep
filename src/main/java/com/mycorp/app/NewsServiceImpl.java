package com.mycorp.app;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
        Collections.reverse(newsList);
        return newsList;
    }

    @Override
    public void addNews(String head, String briefly, String full) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("webapps/my-app-3.5/WEB-INF/classes/news.csv", true), StandardCharsets.UTF_8))) {
            writer.println();
            writer.println(Constants.DELIMETER);
            writer.printf("%s%s\n%s%s\n%s", head, Constants.SPLIT, briefly, Constants.SPLIT, full);
            writer.flush();
        } catch (FileNotFoundException e) {
            logger.error(e);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void addNews(News news) {
        addNews(news.getHead(), news.getBriefly(), news.getFull());
    }

    @Override
    public void deleteNews(int id) {
        List<News> newsList = fetchNews();

        if (id > newsList.size()) {
            logger.error("Запрос на несуществующий id");
            return;
        }
        newsList.remove(id);
        Collections.reverse(newsList);

        fillNews(newsList);
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

    public void fillNews(List<News> newsList) {
        try (PrintWriter writer = new PrintWriter("webapps/my-app-3.5/WEB-INF/classes/news.csv", "UTF-8")) {
            for (int i = 0; i < newsList.size(); i++) {
                News news = newsList.get(i);
                writer.print(Constants.DELIMETER);
                writer.printf("%s%s%s%s%s", news.getHead(), Constants.SPLIT, news.getBriefly(), Constants.SPLIT, news.getFull());
            }
            writer.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            logger.error(e);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void editNews(News news, int id) {
        List<News> newsList = fetchNews();

        newsList.remove(id);
        newsList.add(id, news);
        Collections.reverse(newsList);

        fillNews(newsList);
    }
}
