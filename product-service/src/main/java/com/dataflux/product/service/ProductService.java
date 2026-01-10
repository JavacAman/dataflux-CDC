package com.dataflux.product.service;

import com.dataflux.product.model.Product;
import com.dataflux.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> createAll(List<Product> products) {
        return repository.saveAll(products);
    }

}
