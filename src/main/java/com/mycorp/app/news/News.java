package com.mycorp.app.news;

import com.mycorp.app.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "news")
public class News {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "head")
    private String head;

    @Column (name = "briefly")
    private String briefly;

    @Column (name = "full")
    private String full;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "author")
    private User authorNews;

    public News() {
    }

    public News(String head, String briefly, String full) {
        this.head = head;
        this.briefly = briefly;
        this.full = full;
    }
    public News(String head, String briefly, String full, int id) {
        this.head = head;
        this.briefly = briefly;
        this.full = full;
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBriefly() {
        return briefly;
    }

    public void setBriefly(String briefly) {
        this.briefly = briefly;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthorNews() {
        return authorNews;
    }

    public void setAuthorNews(User authorNews) {
        this.authorNews = authorNews;
    }
}
