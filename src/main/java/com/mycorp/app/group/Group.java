package com.mycorp.app.group;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<String> permission = null;

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, List<String> permission) {
        this.name = name;
        this.permission = permission;
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(int id, String name, List<String> permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public String permissionToString() {
        if (permission == null)
            return null;

        String result = "";

        for (String s : permission) {
            result = result + s + "\n";
        }
        return result;
    }
}
