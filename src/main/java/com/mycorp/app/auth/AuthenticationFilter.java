package com.mycorp.app.auth;

import com.mycorp.app.Constants;
import com.mycorp.app.dao.DbManager;
import com.mycorp.app.user.User;
import com.mycorp.app.user.UserService;
import com.mycorp.app.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import static com.mycorp.app.Constants.AUTHENTICATION_SCHEME;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static DbManager dbManager = null;

    static {
        try {
            dbManager = new DbManager();
        } catch (SQLException throwables) {
            logger.error("Error AuthenticationFilter. DbManager Error");
        }
    }

    UserService userService;
    private User user;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Map<String, Cookie> cookies = requestContext.getCookies();
        Cookie cookie = cookies.get(HttpHeaders.AUTHORIZATION);

        if (cookie == null) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String authorizationHeader = cookie.getValue();

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }

        if (user != null) {
            try {
                userService = new UserServiceImpl();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            boolean isSecure = requestContext.getSecurityContext().isSecure();
            Set<String> permissions = userService.getUserRoles(user.getId());
            requestContext.setSecurityContext(new Authorizer(permissions, user, isSecure));
            requestContext.setProperty("permissions", permissions);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(Constants.AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, Constants.AUTHENTICATION_SCHEME + " realm=\"" + Constants.REALM + "\"")
                        .build());
    }

    private void validateToken(String token) throws Exception {
        String query = "SELECT * FROM news_db.users WHERE auth_token=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, token);
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();

            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2));
                return;
            } else {
                logger.error("Error validate token");
                throw new Exception();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
