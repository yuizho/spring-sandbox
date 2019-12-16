package io.github.yuizho.springsandbox.repositories.jdbc

import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository

interface KotlinProductRepository {
    fun findBy(id: Int): Product?
    fun findAll(): List<Product>?
    fun add(product: Product): Boolean
    fun addAll(products: List<Product>): Boolean
}

@Repository
class KotlinProductRepositoryImpl(
        // https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html
        val jdbcOperations: NamedParameterJdbcOperations
) : KotlinProductRepository {
    override fun findBy(id: Int): Product? {
        return jdbcOperations.queryForObject("""
            SELECT * FROM product WHERE id = :id
        """.trimIndent(), mapOf<String, Any>("id" to id)) { rs, _ ->
            Product().apply {
                setId(rs.getInt("id"))
                setDivision(rs.getInt("division"))
                setCreated(rs.getDate("created").toLocalDate())
                setName(rs.getString("name"))
                this
            }
        }
    }

    override fun findAll(): List<Product>? {
        return return jdbcOperations.query("""
            SELECT * FROM product
        """.trimIndent()) { rs, _ ->
            Product().apply {
                setId(rs.getInt("id"))
                setDivision(rs.getInt("division"))
                setCreated(rs.getDate("created").toLocalDate())
                setName(rs.getString("name"))
                this
            }
        }
    }

    override fun add(product: Product): Boolean {
        val affectedRowCount = jdbcOperations.update("""
            INSERT INTO product 
            (division, created, name) 
            VALUES(:division, :created, :name)
        """.trimIndent(), mapOf(
                "division" to product.division,
                "created" to product.created,
                "name" to product.name
        ))
        return affectedRowCount > 0
    }

    override fun addAll(products: List<Product>): Boolean {
        val affectedRowCounts = jdbcOperations.batchUpdate(
                """
                INSERT INTO product
                (division, created, name)
                VALUES(:division, :created, :name)
                """.trimIndent(),
                products.map {
                    mapOf<String, Any>(
                            "division" to it.division,
                            "created" to it.created,
                            "name" to it.name
                    )
                }.toTypedArray()
        )
        return affectedRowCounts.sum() > 0
    }
}