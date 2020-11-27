package com.mycorp.app.auth;

import com.mycorp.app.dao.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Secured
@Path("/logout")
public class Logout {
    private static final Logger logger = LoggerFactory.getLogger(Logout.class);
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @GET
    public Response logout(ContainerRequestContext requestContext) {
        String query = "UPDATE news_db.users SET auth_token = NULL WHERE (auth_token=?)";

        String authorizationHeader = requestContext.getCookies().get(HttpHeaders.AUTHORIZATION).getValue();
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try (Connection connection = new DbManager().getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, token);
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Logout ERROR");
        }

        URI uri = UriBuilder.fromUri("/my-app-3.5/").build();
        return Response.seeOther(uri).build();
    }
}
