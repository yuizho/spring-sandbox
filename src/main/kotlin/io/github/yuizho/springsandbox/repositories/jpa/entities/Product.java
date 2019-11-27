package io.github.yuizho.springsandbox.repositories.jpa.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer division;
    private Date created;
    private String name;

    public Product() {}

    public Product(int division, Date created, String name) {
        this.division = division;
        this.created = created;
        this.name = name;
    }
}
