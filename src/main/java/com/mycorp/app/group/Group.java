package com.mycorp.app.group;

import com.mycorp.app.permission.Permission;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "group")
public class Group {
    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @Column (name = "name")
    private String name;
    private List<Permission> permission = null;

    public Group() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return id == group.id &&
                Objects.equals(name, group.name) &&
                Objects.equals(permission, group.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permission);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission=" + permission +
                '}';
    }
}
