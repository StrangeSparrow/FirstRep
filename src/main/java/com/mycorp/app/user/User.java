package com.mycorp.app.user;

import com.mycorp.app.group.Group;
import com.mycorp.app.news.News;

import javax.persistence.*;
import java.security.Principal;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Principal {
    @OneToMany(mappedBy = "authorNews", cascade = CascadeType.ALL)
    List<News> news;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_group")
    private Group userGroup;
    @Column(name = "password")
    private String password;
    @Column(name = "auth_token")
    private String token;

    public User() {
    }

    public User(int id, String login, String group) {
        this.id = id;
        this.login = login;
    }

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(String login, String group, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String group, String password) {
        this.id = id;
        this.login = login;
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Group getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Group userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String getName() {
        return getLogin();
    }
}
