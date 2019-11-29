package io.github.yuizho.springsandbox.repositories.jdbc

import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository

interface KotlinProductRepository {
    fun findBy(id: Int): Product?
    fun findAll(): List<Product>?
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
}