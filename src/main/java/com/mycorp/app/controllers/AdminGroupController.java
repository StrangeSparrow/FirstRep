package com.mycorp.app.controllers;

import com.mycorp.app.Config;
import com.mycorp.app.group.Group;
import com.mycorp.app.group.GroupService;
import com.mycorp.app.group.GroupServiceImpl;
import com.mycorp.app.paginator.Paginator;
import com.mycorp.app.paginator.PaginatorBuilder;
import org.apache.log4j.Logger;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/admin/group")
public class AdminGroupController {
    private final static Logger logger = Logger.getLogger(AdminGroupController.class);
    private static GroupService groupService;

    static {
        try {
            groupService = new GroupServiceImpl();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/page/{id}")
    public void getGroupPage(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException, SQLException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator groupPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(groupService.fetchGroup()).setSize(sizePage).build();
        groupPaginator.setCurrentPage(id);

        request.setAttribute("list", groupPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/groupAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    public Response addGroup(@FormParam("create group") String createG,
                           @FormParam("edit group") String editG,
                           @FormParam("delete group") String deleteG,
                           @FormParam("create news") String createN,
                           @FormParam("edit news") String editN,
                           @FormParam("delete news") String deleteN,
                           @FormParam("create user") String createU,
                           @FormParam("edit user") String editU,
                           @FormParam("delete user") String deleteU,
                           @FormParam("name") String name) throws SQLException {

        URI uri = UriBuilder.fromUri("admin/group/page/1").build();

        List<String> permission = new ArrayList<>();

        if (name == null) return Response.seeOther(uri).build();

        if (createG != null) permission.add("4");
        if (createN != null) permission.add("1");
        if (createU != null) permission.add("7");
        if (editG != null) permission.add("5");
        if (editN != null) permission.add("2");
        if (editU != null) permission.add("8");
        if (deleteG != null) permission.add("6");
        if (deleteN != null) permission.add("3");
        if (deleteU != null) permission.add("9");

        groupService.addGroup(new Group(name, permission));

        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteGroup(@PathParam("id") int id) throws SQLException {
        groupService.deleteGroup(id);

        URI uri = UriBuilder.fromUri("admin/group/page/1").build();
        return Response.seeOther(uri).build();
    }
}
