package com.mycorp.app.controllers;

import com.mycorp.app.Config;
import com.mycorp.app.auth.Secured;
import com.mycorp.app.dao.HibernateUtil;
import com.mycorp.app.group.Group;
import com.mycorp.app.paginator.Paginator;
import com.mycorp.app.paginator.PaginatorBuilder;
import com.mycorp.app.user.User;
import com.mycorp.app.user.UserDao;
import com.mycorp.app.user.UserService;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

@Secured
@RolesAllowed("edit_user")
@Path("/admin/user")
public class AdminUserController {
    private final static Logger logger = Logger.getLogger(AdminUserController.class);
    private static final UserService userService;

    static {
        userService = new UserDao();
    }

    private final EntityManager manager = HibernateUtil.getManager();

    @GET
    @Path("/page/{id}")
    public void getUsersPage(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException, SQLException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator groupPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(userService.fetchUsers()).setSize(sizePage).build();
        groupPaginator.setCurrentPage(id);

        request.setAttribute("users", groupPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/usersAdmin.jsp");
        requestDispatcher.forward(request, response);
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") int id) throws SQLException {
        userService.deleteUser(id);

        URI uri = UriBuilder.fromUri("admin/user/page/1").build();
        return Response.seeOther(uri).build();
    }

    @POST
    @Path("/add")
    public Response addUser(@FormParam("login") String login,
                            @FormParam("password") String password,
                            @FormParam("group") String group) throws SQLException {
        URI uri = UriBuilder.fromUri("admin/user/page/1").build();

        if (login == null || password == null || login.isEmpty() || password.isEmpty())
            return Response.seeOther(uri).build();

        User user = new User(login, group, password);
        user.setUserGroup(manager.find(Group.class, Integer.parseInt(group)));
        userService.addUser(user);

        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/edit/{id}")
    public void editUser(@PathParam("id") int id, @Context HttpServletResponse response, @Context HttpServletRequest request) throws SQLException, ServletException, IOException {
        User user = userService.fetchSingleUser(id);

        request.setAttribute("user", user);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/editUser.jsp");
        requestDispatcher.forward(request, response);
    }

    @POST
    @Path("/add_edit_user")
    public Response addEditUser(@FormParam("id") int id,
                                @FormParam("login") String login,
                                @FormParam("group") String group,
                                @FormParam("password") String password) throws SQLException {
        User user = new User(id, login, group, password);
        user.setUserGroup(manager.find(Group.class, Integer.parseInt(group)));
        userService.editUser(user);

        URI uri = UriBuilder.fromUri("admin/user/page/1").build();
        return Response.seeOther(uri).build();
    }
}