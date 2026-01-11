package com.dataflux.product.controller;

import com.dataflux.product.model.Product;
import com.dataflux.product.service.ProductService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product,
                          @AuthenticationPrincipal Jwt jwt) {

        // Example: access user info from token
        String userId = jwt.getSubject();
        System.out.println("User creating product: " + userId);

        return service.create(product);
    }

    @PostMapping("/bulk")
    public List<Product> createBulk(@RequestBody List<Product> products,
                                    @AuthenticationPrincipal Jwt jwt) {

        System.out.println("User: " + jwt.getSubject());
        return service.createAll(products);
    }


    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }
}
