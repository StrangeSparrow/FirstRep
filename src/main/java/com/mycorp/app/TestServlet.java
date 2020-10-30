package com.mycorp.app;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TestServlet extends HttpServlet {
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType(Constants.CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        NewsServiceImpl newsService = new NewsServiceImpl();
        List<News> listNews = newsService.fetchNews(Config.getInstance().getNewsPath());

        request.setAttribute("list", listNews);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("news.jsp");
        requestDispatcher.forward(request, response);

        pw.close();
    }
}
