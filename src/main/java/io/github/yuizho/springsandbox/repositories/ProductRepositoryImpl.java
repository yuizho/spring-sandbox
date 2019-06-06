package io.github.yuizho.springsandbox.repositories;

import io.github.yuizho.springsandbox.repositories.entities.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> find(int id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Product.class))
            );
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
