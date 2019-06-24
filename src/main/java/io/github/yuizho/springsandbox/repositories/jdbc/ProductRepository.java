package io.github.yuizho.springsandbox.repositories.jdbc;

import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> find(int id);
    void batchInsert(List<Product> producs);
}
