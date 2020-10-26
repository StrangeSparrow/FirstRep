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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        List<News> listNews = new ListNews().fetchNews("webapps/my-app-2.0-SNAPSHOT/WEB-INF/classes/news.csv");

        request.setAttribute("list", listNews);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("news.jsp");
        requestDispatcher.forward(request, response);

        pw.close();
    }
}
