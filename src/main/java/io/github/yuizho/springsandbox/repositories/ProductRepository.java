package io.github.yuizho.springsandbox.repositories;

import io.github.yuizho.springsandbox.repositories.entities.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> find(int id);
}
