package com.mycorp.app.user;

import javax.persistence.*;
import java.security.Principal;
import java.util.Objects;

@Entity
@Table (name = "users")
public class User implements Principal {
    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @Column (name = "login")
    private String login;

    @Column (name = "group")
    private String group;

    @Column (name = "password")
    private String password;

    @Column (name = "auth_token")
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getGroup(), user.getGroup()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getToken(), user.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getGroup(), getPassword(), getToken());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", group='" + group + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public String getName() {
        return getLogin();
    }
}
