package com.mycorp.app.user;

import java.util.List;

public interface UserService {
    List<User> fetchUser();
    User fetchSingleUser(int id);
    void addUser();
    void editUser(int id);
    void deleteUser(int id);
}
