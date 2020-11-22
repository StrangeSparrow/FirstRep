package com.mycorp.app.group;

import java.sql.SQLException;
import java.util.List;

public interface GroupService {
    List<Group> fetchGroup() throws SQLException;
    Group fetchSingleGroup(int id) throws SQLException;
    void addGroup(Group group) throws SQLException;
    void editGroup(int id);
    void deleteGroup(int id) throws SQLException;
}
