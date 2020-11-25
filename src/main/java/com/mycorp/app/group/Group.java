package com.mycorp.app.group;

import com.mycorp.app.permission.Permission;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<Permission> permission = null;

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, List<Permission> permission) {
        this.name = name;
        this.permission = permission;
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(int id, String name, List<Permission> permission) {
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

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }
}
