package com.mycorp.app.controllers;

import com.mycorp.app.*;
import org.apache.log4j.Logger;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

@Path("/")
public class NewsController {
    private final static Logger logger = Logger.getLogger(NewsController.class);
    NewsService newsService = new NewsServiceImpl();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response allNews() {
        URI uri = UriBuilder.fromUri("page/1").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void getNews(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException {
        News news = newsService.fetchSingleNews(id);
        request.setAttribute("news", news);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/byIndex.jsp");
        requestDispatcher.forward(request, response);
    }

    @GET
    @Path("/page/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void newsOnPage(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator<News> newsPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(newsService.fetchNews()).setSize(sizePage).build();

        newsPaginator.setCurrentPage(id);

        request.setAttribute("list", newsPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/news.jsp");
        requestDispatcher.forward(request, response);
    }
}
