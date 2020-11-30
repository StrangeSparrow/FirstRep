package com.mycorp.app.auth;

import com.google.common.net.HttpHeaders;
import com.mycorp.app.dao.DbManager;
import com.mycorp.app.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/authentication")
public class AuthenticationEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);
    private static DbManager dbManager = null;

    static {
        try {
            dbManager = new DbManager();
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
            authenticate(username, password);

            String token = issueToken(username);

            URI uri = UriBuilder.fromUri("/my-app-3.5/app/admin").build();
            NewCookie cookieToken = new NewCookie(HttpHeaders.AUTHORIZATION, "Bearer" + " " + token);
            return Response.seeOther(uri).cookie(cookieToken).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        String query = "SELECT * FROM news_db.users u WHERE u.login=? AND u.password=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, username);
            prStmt.setString(2, password);
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();

            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3));
                return;
            } else {
                logger.error("User not found {}", username);
                throw new Exception();
            }
        }
    }

    private String issueToken(String username) {
        String query = "UPDATE news_db.users SET auth_token=? WHERE (id=?)";
        String token = RandomStringUtils.random(20, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, token);
            prStmt.setInt(2, user.getId());
            prStmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return token;
    }
}