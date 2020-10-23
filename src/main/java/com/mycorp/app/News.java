package com.mycorp.app;

public class News {
    private String head;
    private String briefly;
    private String full;

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

    //Формирует готовую строку для отображения в WEB
    public String creatForWeb() {
        String web = String.format("<h3>%s</h3>" +
                "<i>%s</i><p>" +
                "%s", head, briefly, full);

        return web;
    }
}
