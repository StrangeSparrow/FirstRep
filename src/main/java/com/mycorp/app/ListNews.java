package com.mycorp.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListNews {
    private List<News> newsList;
    private File source;

    public ListNews() {
        newsList = new ArrayList<>();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    //В этой функции заполняем список новостей из источника
    public void fillFromFile(String file) throws FileNotFoundException {
        source = new File(file);
        Scanner scanner = new Scanner(source, "UTF-8");
        scanner.useDelimiter("!!!");

        String [] data;               //Массив в который мы будем получать данные из источника и сразу разделять на составляющие по разделителю ';'
        News news;                    //В эту переменную будем заносить наши данные и добавлять её к нашему списку

        while (scanner.hasNext()) {
            String s = scanner.next();
            data = s.split(";");
            news = new News(data[0], data[1], data[2]);
            newsList.add(news);
        }

        scanner.close();
    }
}
