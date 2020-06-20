package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.web.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();
    Product findByName(String name);
    Product findById(Long id);
    Product addNewProduct(ProductDto prodD);
    void deleteProduct(Product product);
    List<Product> findAllGroupByCategory();
}
