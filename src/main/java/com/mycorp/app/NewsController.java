package com.mycorp.app;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/")
public class NewsController {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public void allNews(@Context HttpServletResponse response, @Context HttpServletRequest request) throws ServletException, IOException {
        NewsServiceImpl newsService = new NewsServiceImpl();
        List<News> listNews = newsService.fetchNews(Config.getInstance().getNewsPath());

        request.setAttribute("list", listNews);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("news.jsp");
        requestDispatcher.forward(request, response);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void getNews(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException {
        List<News> listNews = new NewsServiceImpl().fetchNews(Config.getInstance().getNewsPath());

        if (listNews.size() < id)
            throw new IllegalArgumentException();

        News news = listNews.get(id);
        request.setAttribute("news", news);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/byIndex.jsp");
        requestDispatcher.forward(request, response);
    }
}
