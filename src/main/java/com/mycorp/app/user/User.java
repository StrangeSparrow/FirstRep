package com.mycorp.app.user;

public class User {
    private int id;
    private String login;
    private String group;

    public User(int id, String login, String group) {
        this.id = id;
        this.login = login;
        this.group = group;
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
}
