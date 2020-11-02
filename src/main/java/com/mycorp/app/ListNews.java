package com.mycorp.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListNews implements NewsService{

    //В этой функции заполняем список новостей из источника
    public List<News> fetchNews(String filePath) {
        List<News> newsList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("!!!");

            while (scanner.hasNext()) {
                String[] data;               //Массив в который мы будем получать данные из источника и сразу разделять на составляющие по разделителю ';'
                News news;
                String s = scanner.next();
                data = s.split(";");
                news = new News(data[0], data[1], data[2]);
                newsList.add(news);
            }
        } catch (IOException e) {
            System.out.println("Error " + e);
        }

        if (!newsList.isEmpty())
            return newsList;
        return null;
    }
}
