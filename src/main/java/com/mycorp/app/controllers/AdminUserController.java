package com.mycorp.app.controllers;

import com.mycorp.app.Config;
import com.mycorp.app.paginator.Paginator;
import com.mycorp.app.paginator.PaginatorBuilder;
import com.mycorp.app.user.UserService;
import com.mycorp.app.user.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.sql.SQLException;

@Path("/admin/user")
public class AdminUserController {
    private final static Logger logger = Logger.getLogger(AdminGroupController.class);
    private static UserService userService;

    static {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @GET
    @Path("/page/{id}")
    public void getUsersPage(@Context HttpServletResponse response, @Context HttpServletRequest request, @PathParam("id") int id) throws ServletException, IOException, SQLException {
        int sizePage = Config.getInstance().getPageSize();

        Paginator groupPaginator = new PaginatorBuilder().setCurrentPage(1).setDataList(userService.fetchUser()).setSize(sizePage).build();
        groupPaginator.setCurrentPage(id);

        request.setAttribute("users", groupPaginator);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/usersAdmin.jsp");
        requestDispatcher.forward(request, response);
    }
}
