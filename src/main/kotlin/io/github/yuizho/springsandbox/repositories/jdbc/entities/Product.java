package io.github.yuizho.springsandbox.repositories.jdbc.entities;

import java.util.Date;

public class Product {
    private int id;
    private int division;
    private Date created;
    private String name;

    public int getId() {
        return this.id;
    }

    public int getDivision() {
        return this.division;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", division=" + this.getDivision() + ", created=" + this.getCreated() + ", name=" + this.getName() + ")";
    }
}
