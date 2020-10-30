package com.mycorp.app;

<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

=======
>>>>>>> Conf-and-Log
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

}
