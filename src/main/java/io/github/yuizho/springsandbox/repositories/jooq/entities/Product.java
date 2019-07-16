package io.github.yuizho.springsandbox.repositories.jooq.entities;

import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;
import java.util.Date;

@Getter
@ToString
public class Product {
    private final int id;
    private final int division;
    private final Date created;
    private final String name;

    @ConstructorProperties({ "id", "division", "created", "name" })
    public Product(int id, int division, Date created, String name) {
        this.id = id;
        this.division = division;
        this.created = created;
        this.name = name;
    }
}
