package com.mycorp.app.controllers;

import com.mycorp.app.Config;
import com.mycorp.app.auth.Secured;
import com.mycorp.app.group.Group;
import com.mycorp.app.group.GroupDao;
import com.mycorp.app.group.GroupService;
import com.mycorp.app.paginator.Paginator;
import com.mycorp.app.paginator.PaginatorBuilder;
import com.mycorp.app.permission.Permission;
import com.mycorp.app.permission.PermissionDao;
import com.mycorp.app.permission.PermissionService;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
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

@Secured
@RolesAllowed("edit_group")
@Path("/admin/group")
public class AdminGroupController {
    private final static Logger logger = Logger.getLogger(AdminGroupController.class);
    private static final GroupService groupService;

    static {
        groupService = new GroupDao();
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
    public Response addGroup(@FormParam("permission[]") List<Integer> permissions,
                             @FormParam("name") String name) throws SQLException {

        URI uri = UriBuilder.fromUri("admin/group/page/1").build();

        if (name == null || name.isEmpty()) return Response.seeOther(uri).build();

        List<Permission> permissionList = new ArrayList<>();
        PermissionService ps = new PermissionDao();
        for (int id : permissions) {
            permissionList.add(ps.fetchSinglePermission(id));
        }

        groupService.addGroup(new Group(name, permissionList));

        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteGroup(@PathParam("id") int id) throws SQLException {
        groupService.deleteGroup(id);

        URI uri = UriBuilder.fromUri("admin/group/page/1").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/edit/{id}")
    public void editGroup(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws SQLException, ServletException, IOException {
        Group group = groupService.fetchSingleGroup(id);

        request.setAttribute("group", group);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/editGroup.jsp");
        requestDispatcher.forward(request, response);
    }

    @POST
    @Path("/add_edit_group")
    public Response addEditGroup(@FormParam("permission[]") List<Integer> permissions,
                                 @FormParam("id") int id,
                                 @FormParam("name") String name) throws SQLException {
        URI uri = UriBuilder.fromUri("admin/group/page/1").build();

        if (name == null || name.isEmpty()) return Response.seeOther(uri).build();

        List<Permission> permissionList = new ArrayList<>();
        PermissionService ps = new PermissionDao();
        for (int idPerm : permissions) {
            permissionList.add(ps.fetchSinglePermission(idPerm));
        }

        groupService.editGroup(new Group(id, name, permissionList));

        return Response.seeOther(uri).build();
    }
}
