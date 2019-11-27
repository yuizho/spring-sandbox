package io.github.yuizho.springsandbox.repositories.jooq;

import io.github.yuizho.springsandbox.repositories.jooq.entities.Product;
import io.github.yuizho.springsandbox.repositories.jooq.schema.tables.records.ProductRecord;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                        DIVISION.ID.as("divisionId"),
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
                        DIVISION.ID.as("divisionId"),
                        PRODUCT.CREATED,
                        PRODUCT.NAME
                )
                .from(PRODUCT)
                .join(DIVISION).on(DIVISION.ID.eq(PRODUCT.DIVISION))
                .fetchInto(Product.class);
    }

    public void add(Product product) {
        create.insertInto(
                PRODUCT,
                PRODUCT.DIVISION,
                PRODUCT.CREATED,
                PRODUCT.NAME)
                .values(
                        UInteger.valueOf(product.getDivisionId()),
                        Timestamp.from(product.getCreated()),
                        product.getName()
                )
                // https://www.jooq.org/doc/3.11/manual-single-page/#insert-on-duplicate-key
                .onDuplicateKeyUpdate()
                .set(PRODUCT.DIVISION, UInteger.valueOf(product.getDivisionId()))
                .set(PRODUCT.CREATED, Timestamp.from(product.getCreated()))
                .set(PRODUCT.NAME, product.getName())
                .execute();
    }
}
