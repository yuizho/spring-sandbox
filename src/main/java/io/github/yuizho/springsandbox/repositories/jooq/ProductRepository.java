package io.github.yuizho.springsandbox.repositories.jooq;

import io.github.yuizho.springsandbox.repositories.jooq.entities.Product;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.github.yuizho.springsandbox.repositories.jooq.schema.tables.Division.DIVISION;
import static io.github.yuizho.springsandbox.repositories.jooq.schema.tables.Product.PRODUCT;

@Repository(value = "JooqProductRepository")
public class ProductRepository {
    private final DSLContext create;

    // 多分Spring Starter入れたからだけど、dslContextはすんなりInjectできた
    public ProductRepository(final DSLContext dsl) {
        this.create = dsl;
    }

    public Optional<Product> find(int id) {
        // 1対1のケースのjoinとその結果のMapping
        Product result = create
                .select(
                        PRODUCT.ID,
                        DIVISION.NAME.as("division"),
                        PRODUCT.CREATED,
                        PRODUCT.NAME
                )
                .from(PRODUCT)
                .join(DIVISION).on(DIVISION.ID.eq(PRODUCT.DIVISION))
                .where(PRODUCT.ID.eq(UInteger.valueOf(id)))
                .fetchAnyInto(Product.class);
        return Optional.ofNullable(result);
    }

    public List<Product> findAll() {
        // 自前のPojoへのMapping (Immutableのクラスにもいける)
        // https://www.jooq.org/doc/3.11/manual-single-page/#pojos
        return create
                .select(
                        PRODUCT.ID,
                        DIVISION.NAME.as("division"),
                        PRODUCT.CREATED,
                        PRODUCT.NAME
                )
                .from(PRODUCT)
                .join(DIVISION).on(DIVISION.ID.eq(PRODUCT.DIVISION))
                .fetchInto(Product.class);
    }
}
