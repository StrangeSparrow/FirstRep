package com.mycorp.app.user;

import java.security.Principal;

public class User implements Principal {
    private int id;
    private String login;
    private String group;
    private String password;

    public User(int id, String login, String group) {
        this.id = id;
        this.login = login;
        this.group = group;
    }

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(String login, String group, String password) {
        this.login = login;
        this.group = group;
        this.password = password;
    }

    public User(int id, String login, String group, String password) {
        this.id = id;
        this.login = login;
        this.group = group;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return getLogin();
    }
}
