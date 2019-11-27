package io.github.yuizho.springsandbox.repositories.jdbc.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Product {
    private int id;
    private int division;
    private Date created;
    private String name;
}
