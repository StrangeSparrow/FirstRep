package com.mycorp.app.permission;

import javax.persistence.*;

@Entity
@Table (name = "permission")
public class Permission {
    @Id
    @GeneratedValue
    @Column (name = "id")
    private int id;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    public Permission() {
    }

    public Permission(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object permission) {
        if (permission == this)
            return true;

        if (! (permission instanceof  Permission))
            return false;

        if (id == ((Permission) permission).getId())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
