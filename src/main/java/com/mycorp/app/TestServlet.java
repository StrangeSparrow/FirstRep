package com.mycorp.app;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
<<<<<<< Updated upstream
        pw.println("<H1>Me edit</H1>");
        pw.println("<B>Hello!");
=======

        List<News> listNews = new NewsServiceImpl().fetchNews(Config.getInstance().getNewsPath());

        request.setAttribute("list", listNews);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("news.jsp");
        requestDispatcher.forward(request, response);

>>>>>>> Stashed changes
        pw.close();
    }
}
