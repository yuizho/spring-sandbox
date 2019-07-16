package io.github.yuizho.springsandbox.repositories.jooq;

import io.github.yuizho.springsandbox.repositories.jooq.entities.Product;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.github.yuizho.springsandbox.repositories.jooq.schema.tables.Product.PRODUCT;

@Repository(value = "JooqProductRepository")
public class ProductRepository {
    private final DSLContext create;

    // 多分Spring Starter入れたからだけど、dslContextはすんなりInjectできた
    public ProductRepository(final DSLContext dsl) {
        this.create = dsl;
    }

    public List<Product> findAll() {
        // 自前のPojoへのMapping (Immutableのクラスにもいける)
        // https://www.jooq.org/doc/3.11/manual-single-page/#pojos
        return create
                .select()
                .from(PRODUCT)
                .fetchInto(Product.class);
    }
}
