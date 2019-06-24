package io.github.yuizho.springsandbox.controllers;

import io.github.yuizho.springsandbox.repositories.jdbc.ProductRepository;
import io.github.yuizho.springsandbox.repositories.jdbc.entities.Product;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/jdbc")
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
