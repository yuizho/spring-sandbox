package io.github.yuizho.springsandbox.controllers;

import io.github.yuizho.springsandbox.repositories.ProductRepository;
import io.github.yuizho.springsandbox.repositories.entities.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DemoRestController {
    private final ProductRepository productRepository;

    public DemoRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/{id}")
    public String get(@PathVariable Integer id) {
        return productRepository.find(id)
                .map(Product::toString)
                .orElse(String.format(
                        "There is no target product (id: %d).",
                        id));
    }
}
