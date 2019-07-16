package io.github.yuizho.springsandbox.controllers;

import io.github.yuizho.springsandbox.repositories.jooq.ProductRepository;
import io.github.yuizho.springsandbox.repositories.jooq.entities.Product;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jooq")
public class JooqRestController {
    private final Logger logger;
    private final ProductRepository productRepository;

    public JooqRestController(Logger logger, ProductRepository productRepository) {
        this.logger = logger;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> getAll() {
        logger.info("============= this is api/jooq/product");
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable Integer id) {
        logger.info("============= this is api/jooq/product/{}", id);
        return productRepository.find(id)
                .orElseThrow(() ->
                        new IllegalCallerException(
                                String.format("There is no target product (id: %d).", id)
                        )
                );
    }
}
