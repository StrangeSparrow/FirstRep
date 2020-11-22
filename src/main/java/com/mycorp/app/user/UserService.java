package com.mycorp.app.user;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> fetchUser() throws SQLException;
    User fetchSingleUser(int id) throws SQLException;
    void addUser(User user) throws SQLException;
    void editUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
}