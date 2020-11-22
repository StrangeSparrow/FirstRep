package com.mycorp.app.group;

import com.mycorp.app.dao.DbManager;
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

        String queryIdAndName = "SELECT * FROM news_db.group";
        String queryPermission = "SELECT p.name FROM news_db.group g " +
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
                List<String> permission = new ArrayList<>();
                while (resultPermission.next()) {
                    permission.add(resultPermission.getString(1));
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

        String query = "SELECT * FROM news_db.group WHERE id=?";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            ResultSet resultSet = prStmt.executeQuery();

            while (resultSet.next()) {
                int index = resultSet.getInt(1);
                String name = resultSet.getString(2);
                group = new Group(id, name);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
        return group;
    }

    @Override
    public void addGroup(Group group) throws SQLException {
        String query = "INSERT INTO news_db.group (name) VALUES (?)";
        String groupToPermission = "INSERT INTO news_db.group_to_permission (group_id, permission) VALUES (?, ?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query, new String[] {"id"});
             PreparedStatement prStmtPermission = connection.prepareStatement(groupToPermission)) {
            prStmt.setString(1, group.getName());
            prStmt.executeUpdate();

            ResultSet genId = prStmt.getGeneratedKeys();
            if (genId.next()) {
                int id = genId.getInt(1);

                for (String permission : group.getPermission()) {
                    prStmtPermission.setInt(1, id);
                    prStmtPermission.setInt(2, Integer.parseInt(permission));
                    prStmtPermission.executeUpdate();
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void editGroup(int id) {

    }

    @Override
    public void deleteGroup(int id) throws SQLException {
        String deletePermission = "DELETE FROM news_db.group_to_permission WHERE (group_id=?)";
        String deleteGroup = "DELETE FROM news_db.group WHERE (id=?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(deletePermission);
             PreparedStatement prStmtDeleteGroup = connection.prepareStatement(deleteGroup)) {

            prStmt.setInt(1, id);
            prStmt.executeUpdate();
            prStmtDeleteGroup.setInt(1, id);
            prStmtDeleteGroup.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }
}
