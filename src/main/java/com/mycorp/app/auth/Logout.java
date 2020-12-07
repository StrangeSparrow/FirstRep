package com.mycorp.app.auth;

import com.mycorp.app.Constants;
import com.mycorp.app.user.UserService;
import com.mycorp.app.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.SQLException;

@Secured
@Path("/logout")
public class Logout {
    private static final Logger logger = LoggerFactory.getLogger(Logout.class);
    private static UserService userService = null;

    static {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @GET
    public Response logout(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getCookies().get(HttpHeaders.AUTHORIZATION).getValue();
        String token = authorizationHeader.substring(Constants.AUTHENTICATION_SCHEME.length()).trim();

        userService.deleteToken(token);

        URI uri = UriBuilder.fromUri(Constants.APP).build();
        return Response.seeOther(uri).build();
    }
}
