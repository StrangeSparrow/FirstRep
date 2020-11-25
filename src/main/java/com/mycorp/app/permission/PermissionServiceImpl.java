package com.mycorp.app.permission;

import com.mycorp.app.dao.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionServiceImpl implements PermissionService {
    private final DbManager dbManager;

    public PermissionServiceImpl() throws SQLException {
        dbManager = new DbManager();
    }

    @Override
    public List<Permission> fetchPermissions() throws SQLException {
        String query = "SELECT * FROM news_db.permission";
        List<Permission> permissionList = null;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();
            permissionList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);

                permissionList.add(new Permission(id, name, description));
            }
        } catch (SQLException e) {
            throw e;
        }
        return permissionList;
    }

    @Override
    public Permission fetchSinglePermission(int id) throws SQLException {
        Permission permission = null;
        String query = "SELECT * FROM news_db.permission WHERE id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeQuery();

            ResultSet resultSet = prStmt.getResultSet();

            if (resultSet.next()) {
                int index = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);

                permission = new Permission(id, name, description);
            }
        } catch (SQLException e) {
            throw e;
        }

        return permission;
    }
}
