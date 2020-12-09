package com.mycorp.app.controllers;

import com.mycorp.app.*;
import com.mycorp.app.auth.Secured;
import com.mycorp.app.news.*;
import com.mycorp.app.paginator.Paginator;
import com.mycorp.app.paginator.PaginatorBuilder;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Set;

@Secured
@RolesAllowed("edit_news")
@Path("/admin")
public class AdminController {
    private final static Logger logger = Logger.getLogger(AdminController.class);

    private static NewsService newsService = null;
    static {
        if (Config.getInstance().getSource().equals("database")) {
            newsService = new NewsDao();
        } else {
            newsService = new NewsServiceImpl();
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public void adminNews(@Context HttpServletResponse response, @Context HttpServletRequest request, ContainerRequestContext requestContext) throws ServletException, IOException {
        Set<String> permissions = (Set<String>) requestContext.getProperty("permissions");

        request.setAttribute("userPermissions", permissions);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin.jsp");
        requestDispatcher.forward(request, response);
    }

    @GET
    @Path("/news/page/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void editorNews(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException, SQLException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator newsPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(newsService.fetchNews()).setSize(sizePage).build();

        newsPaginator.setCurrentPage(id);

        request.setAttribute("list", newsPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/newsAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/news/delete/{id}")
    public Response deleteNews(@PathParam("id") int id) throws SQLException {
        newsService.deleteNews(id);

        URI uri = UriBuilder.fromUri("admin/news/page/1").build();
        return Response.seeOther(uri).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Path("/news/add")
    public Response addNews(@FormParam("head") String head, @FormParam("briefly") String briefly, @FormParam("full") String full) throws SQLException {
        newsService.addNews(new News(head, briefly, full));

        URI uri = UriBuilder.fromUri("admin/news/page/1").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/news/edit/{id}")
    public void editNews(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException, SQLException {
        News news = newsService.fetchSingleNews(id);
        request.setAttribute("news", news);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/editNews.jsp");
        requestDispatcher.forward(request, response);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/news/addEditNews")
    public Response addEditNews(@FormParam("head") String head, @FormParam("briefly") String briefly, @FormParam("full") String full, @FormParam("id") int id) throws SQLException {
        News news = new News(head, briefly, full, id);
        newsService.editNews(news);

        URI uri = UriBuilder.fromUri("admin/news/page/1").build();
        return Response.seeOther(uri).build();
    }
}
