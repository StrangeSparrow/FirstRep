package com.mycorp.app;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        ListNews listNews = new ListNews();
        listNews.fillFromFile("webapps\\my-app-1.0-SNAPSHOT\\WEB-INF\\classes\\news.csv");

        for(News news : listNews.getNewsList()) {
            pw.println(news.creatForWeb());
        }

        pw.close();
    }
}
