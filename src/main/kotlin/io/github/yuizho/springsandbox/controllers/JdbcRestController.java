package io.github.yuizho.springsandbox.controllers;

import io.github.yuizho.springsandbox.repositories.jdbc.KotlinProductRepository;
import io.github.yuizho.springsandbox.repositories.jdbc.ProductRepository;
import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/jdbc")
public class JdbcRestController {
    private final Logger logger;
    private final ProductRepository productRepository;
    private final KotlinProductRepository kotlinProductRepository;

    public JdbcRestController(Logger logger,
                              ProductRepository productRepository,
                              KotlinProductRepository kotlinProductRepository) {
        this.logger = logger;
        this.productRepository = productRepository;
        this.kotlinProductRepository = kotlinProductRepository;
    }

    @GetMapping("/products/{id}")
    public String get(@PathVariable Integer id) {
        logger.info("============= this is api/jdbc/product/{}", id);
        return productRepository.find(id)
                .map(Product::toString)
                .orElse(String.format(
                        "There is no target product (id: %d).",
                        id));
    }

    @GetMapping("/kotlin/products")
    public String getByKotlin() {
        logger.info("============= this is api/jdbc/kotlin/products/");
        return Optional.ofNullable(kotlinProductRepository.findAll())
                .map(List::toString)
                .orElse(String.format(
                        "There is no product."));
    }

    @GetMapping("/kotlin/products/{id}")
    public String getByKotlin(@PathVariable Integer id) {
        logger.info("============= this is api/jdbc/kotlin/products/{}", id);
        return Optional.ofNullable(kotlinProductRepository.findBy(id))
                .map(Product::toString)
                .orElse(String.format(
                        "There is no target product (id: %d).",
                        id));
    }

    @PostMapping("/products")
    @Transactional
    public void post(@RequestParam List<String> names) {
        List<Product> products = names.stream()
                .map(n -> {
                    Product p = new Product();
                    p.setName(n);
                    p.setCreated(new Date());
                    p.setDivision(1);
                    // dateずらすためにsleep
                    try { Thread.sleep(1500); } catch (Exception ex) {}
                    return p;
                })
                .collect(Collectors.toList());
        productRepository.batchInsert(products);
    }
}
