package com.mycorp.app.user;

import com.mycorp.app.dao.DbManager;

import java.util.List;

public class UserServiceImpl implements UserService {
    private DbManager dbManager;

    public UserServiceImpl(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<User> fetchUser() {
        return null;
    }

    @Override
    public User fetchSingleUser(int id) {
        return null;
    }

    @Override
    public void addUser() {

    }

    @Override
    public void editUser(int id) {

    }

    @Override
    public void deleteUser(int id) {

    }
}
