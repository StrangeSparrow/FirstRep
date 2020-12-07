package com.mycorp.app.auth;

import com.google.common.net.HttpHeaders;
import com.mycorp.app.Constants;
import com.mycorp.app.dao.DbManager;
import com.mycorp.app.user.User;
import com.mycorp.app.user.UserService;
import com.mycorp.app.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.SQLException;

@Path("/authentication")
public class AuthenticationEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);
    private static UserService userService;
    private static DbManager dbManager = null;

    static {
        try {
            dbManager = new DbManager();
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            logger.error("AuthenticationEndpoint error. DbManager error");
        }
    }

    private User user;

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {

        try {
            user = userService.authenticate(username, password);

            String token = userService.issueToken(user.getId());

            URI uri = UriBuilder.fromUri(Constants.APP_NAME + "admin").build();
            NewCookie cookieToken = new NewCookie(HttpHeaders.AUTHORIZATION, "Bearer" + " " + token);
            return Response.seeOther(uri).cookie(cookieToken).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}