package com.mycorp.app.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> fetchUsers() throws SQLException;
    User fetchSingleUser(int id) throws SQLException;
    void addUser(User user) throws SQLException;
    void editUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    Set<String> getUserRoles(int id);
    String issueToken(int id);
    User authenticate(String username, String password) throws Exception;
    void deleteToken(String token);
}