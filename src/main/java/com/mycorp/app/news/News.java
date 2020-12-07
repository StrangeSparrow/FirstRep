package com.mycorp.app.news;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "news")
public class News {
    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @Column (name = "head")
    private String head;

    @Column (name = "briefly")
    private String briefly;

    @Column (name = "full")
    private String full;

    @Column (name = "author")
    private String author;

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
        this.author = null;
    }

    public News(String head, String briefly, String full, int id, String author) {
        this.head = head;
        this.briefly = briefly;
        this.full = full;
        this.id = id;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(head, news.head) &&
                Objects.equals(briefly, news.briefly) &&
                Objects.equals(full, news.full) &&
                Objects.equals(author, news.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, head, briefly, full, author);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", head='" + head + '\'' +
                ", briefly='" + briefly + '\'' +
                ", full='" + full + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
