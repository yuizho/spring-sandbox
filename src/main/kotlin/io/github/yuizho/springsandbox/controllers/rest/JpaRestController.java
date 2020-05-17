package io.github.yuizho.springsandbox.controllers.rest;

import io.github.yuizho.springsandbox.repositories.jpa.ProductRepository;
import io.github.yuizho.springsandbox.repositories.jpa.entities.Product;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/jpa")
public class JpaRestController {
    private final Logger logger;
    private final ProductRepository productRepository;

    public JpaRestController(Logger logger, ProductRepository productRepository) {
        this.logger = logger;
        this.productRepository = productRepository;
    }

    @GetMapping("/products/{id}")
    public String get(@PathVariable Integer id) {
        logger.info("============= this is api/jpa/product/{}", id);
        return productRepository.findById(id)
                .map(Product::toString)
                .orElse(String.format(
                        "There is no target product (id: %d).",
                        id));
    }

    @PostMapping("/products")
    @Transactional
    public Iterable<Product> post(@RequestParam List<String> names) {
        List<Product> products = names.stream()
                .map(n -> {
                    Product p = new Product();
                    p.setName(n);
                    p.setCreated(new Date());
                    p.setDivision(1);
                    return p;
                })
                .collect(Collectors.toList());

        Iterable<Product> saved = productRepository.saveAll(products);
        return saved;
    }
}
