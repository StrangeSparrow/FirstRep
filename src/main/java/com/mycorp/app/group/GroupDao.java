package com.mycorp.app.group;

import java.sql.SQLException;
import java.util.List;

public class GroupDao implements GroupService
{
    @Override
    public List<Group> fetchGroup() throws SQLException {
        return null;
    }

    @Override
    public Group fetchSingleGroup(int id) throws SQLException {
        return null;
    }

    @Override
    public void addGroup(Group group) throws SQLException {

    }

    @Override
    public void editGroup(Group group) throws SQLException {

    }

    @Override
    public void deleteGroup(int id) throws SQLException {

    }
}
