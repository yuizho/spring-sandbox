package io.github.yuizho.springsandbox.repositories.jdbc;

import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
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

    public void batchInsert(List<Product> producs) {
        String sql = "INSERT INTO product " +
                "(division, created, name) " +
                "VALUES(:division, :created, :name)";
        jdbcTemplate.batchUpdate(sql,
                producs.stream()
                        .map(p ->
                                new MapSqlParameterSource()
                                        .addValue("division", p.getDivision(), Types.INTEGER)
                                        .addValue("created", p.getCreated(), Types.TIMESTAMP)
                                        .addValue("name", p.getName(), Types.CHAR)
                        ) .toArray(SqlParameterSource[]::new)
        );
    }
}
