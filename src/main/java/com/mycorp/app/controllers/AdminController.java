package com.mycorp.app.controllers;

import com.mycorp.app.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

@Path("/admin")
public class AdminController {
    NewsService newsService = new NewsServiceImpl();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response adminNews() {
        URI uri = UriBuilder.fromUri("admin/page/1").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/page/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void editorNews(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator<News> newsPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(newsService.fetchNews()).setSize(sizePage).build();

        newsPaginator.setCurrentPage(id);

        request.setAttribute("list", newsPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/newsAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/delete/{id}")
    public Response deleteNews(@PathParam("id") int id) throws InterruptedException {
        Thread thread = new Thread(() -> newsService.deleteNews(id));
        thread.start();
        Thread.sleep(5000);

        URI uri = UriBuilder.fromUri("admin/page/1").build();
        return Response.seeOther(uri).build();
    }
}
