package com.mycorp.app.user;

import com.mycorp.app.dao.DbManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final DbManager dbManager;

    public UserServiceImpl() throws SQLException {
        this.dbManager = new DbManager();
    }

    @Override
    public List<User> fetchUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.id, u.login, g.name FROM news_db.users u LEFT OUTER JOIN news_db.group g ON u.group=g.id";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
        return users;
    }

    @Override
    public User fetchSingleUser(int id) throws SQLException {
        User user = null;
        String query = "SELECT u.id, u.login, g.name, u.password FROM news_db.users u LEFT OUTER JOIN news_db.group g ON u.group=g.id WHERE u.id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();

            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO news_db.users (login, password, users.group) VALUES (?, ?, ?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, user.getLogin());
            prStmt.setString(2, user.getPassword());
            prStmt.setInt(3, Integer.parseInt(user.getGroup()));
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void editUser(User user) throws SQLException {
        String query = "UPDATE news_db.users SET login=?, password=?, users.group=? WHERE (id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, user.getLogin());
            prStmt.setString(2, user.getPassword());
            prStmt.setInt(3, Integer.parseInt(user.getGroup()));
            prStmt.setInt(4, user.getId());
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM news_db.users WHERE (id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public Set<String> getRolesUser(int id) {
        String query = "SELECT p.name FROM news_db.users u " +
                "JOIN news_db.group g ON g.id=u.group " +
                "JOIN news_db.group_to_permission gp ON g.id=gp.group_id " +
                "JOIN news_db.permission p ON p.id=gp.permission WHERE u.id=?";

        Set<String> roles = new HashSet<>();

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeQuery();
            ResultSet resultSet = prStmt.getResultSet();

            while (resultSet.next()) {
                roles.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.error("Error get roles in filter");
        }
        return roles;
    }
}
