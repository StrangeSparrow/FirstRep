package com.mycorp.app.group;

import com.mycorp.app.dao.DbManager;
import com.mycorp.app.permission.Permission;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupServiceImpl implements GroupService {
    private final static Logger logger = Logger.getLogger(GroupServiceImpl.class);
    private DbManager dbManager;

    public GroupServiceImpl() throws SQLException {
        dbManager = new DbManager();
    }

    @Override
    public List<Group> fetchGroup() throws SQLException {
        List<Group> groupList = new ArrayList<>();

        String queryIdAndName = "SELECT * FROM news_db.user_group";
        String queryPermission = "SELECT p.* FROM news_db.user_group g " +
                "JOIN news_db.group_to_permission gp ON g.id=gp.group_id " +
                "JOIN news_db.permission p ON p.id=gp.permission WHERE g.id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(queryIdAndName);
             PreparedStatement prStmtPermission = connection.prepareStatement(queryPermission)) {
            ResultSet resultSet = prStmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                prStmtPermission.setInt(1, id);
                ResultSet resultPermission = prStmtPermission.executeQuery();
                List<Permission> permission = new ArrayList<>();
                while (resultPermission.next()) {
                    int index = resultPermission.getInt(1);
                    String namePerm = resultPermission.getString(2);
                    String description = resultPermission.getString(3);

                    permission.add(new Permission(index, namePerm, description));
                }

                groupList.add(new Group(id, name, permission));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
        return groupList;
    }

    @Override
    public Group fetchSingleGroup(int id) throws SQLException {
        Group group = null;

        String query = "SELECT name FROM news_db.user_group WHERE id=?";

        String queryPermission = "SELECT p.* FROM news_db.user_group g " +
                "JOIN news_db.group_to_permission gp ON g.id=gp.group_id " +
                "JOIN news_db.permission p ON p.id=gp.permission WHERE g.id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query);
             PreparedStatement prStmtPermission = connection.prepareStatement(queryPermission)) {
            prStmt.setInt(1, id);
            ResultSet resultSet = prStmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString(1);

                prStmtPermission.setInt(1, id);
                ResultSet resultPermission = prStmtPermission.executeQuery();
                List<Permission> permission = new ArrayList<>();
                while (resultPermission.next()) {
                    int index = resultPermission.getInt(1);
                    String namePerm = resultPermission.getString(2);
                    String description = resultPermission.getString(3);

                    permission.add(new Permission(index, namePerm, description));
                }

                group = new Group(id, name, permission);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
        return group;
    }

    @Override
    public void addGroup(Group group) throws SQLException {
        String query = "INSERT INTO news_db.user_group (name) VALUES (?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query, new String[] {"id"})) {
            prStmt.setString(1, group.getName());
            prStmt.executeUpdate();

            ResultSet genId = prStmt.getGeneratedKeys();
            if (genId.next()) {
                int id = genId.getInt(1);
                addPermission(id, group.getPermission());
            }

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void editGroup(Group group) throws SQLException {
        String query = "UPDATE news_db.user_group SET name=? WHERE (id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            deletePermission(group.getId());

            prStmt.setString(1, group.getName());
            prStmt.setInt(2, group.getId());
            prStmt.executeUpdate();

            addPermission(group.getId(), group.getPermission());
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void deleteGroup(int id) throws SQLException {
        String deleteGroup = "DELETE FROM news_db.user_group WHERE (id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmtDeleteGroup = connection.prepareStatement(deleteGroup)) {
            deletePermission(id);

            prStmtDeleteGroup.setInt(1, id);
            prStmtDeleteGroup.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    private void deletePermission(int id) throws SQLException {
        String deletePermission = "DELETE FROM news_db.group_to_permission WHERE (group_id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(deletePermission)) {

            prStmt.setInt(1, id);
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    private void addPermission(int id, List<Permission> permission) throws SQLException {
        String groupToPermission = "INSERT INTO news_db.group_to_permission (group_id, permission) VALUES (?, ?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmtPermission = connection.prepareStatement(groupToPermission)) {

            for (Permission perm : permission) {
                prStmtPermission.setInt(1, id);
                prStmtPermission.setInt(2, perm.getId());
                prStmtPermission.executeUpdate();
            }

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }
}
