package io.github.yuizho.springsandbox.repositories.jooq.entities;

import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;
import java.time.Instant;

@Getter
@ToString
public class Product {
    private final int id;
    private final int divisionId;
    private final Instant created;
    private final String name;

    @ConstructorProperties({ "id", "divisionId", "created", "name" })
    public Product(int id, int divisionId, Instant created, String name) {
        this.id = id;
        this.divisionId = divisionId;
        this.created = created;
        this.name = name;
    }
}
