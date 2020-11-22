package com.mycorp.app.news;

public class News {
    private String head;
    private String briefly;
    private String full;
    private int id;
    private String author;

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
}
