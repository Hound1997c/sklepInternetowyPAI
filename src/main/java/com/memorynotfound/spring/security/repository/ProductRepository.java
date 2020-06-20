package com.memorynotfound.spring.security.repository;

import com.memorynotfound.spring.security.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
    List<Product> findAll();
    //List<Product> findAllByCategory(String c);
}
