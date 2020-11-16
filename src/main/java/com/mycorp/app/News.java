package com.mycorp.app;

public class News {
    private String head;
    private String briefly;
    private String full;
    private int id;

    public News(String head, String briefly, String full) {
        this.head = head;
        this.briefly = briefly;
        this.full = full;
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

}
