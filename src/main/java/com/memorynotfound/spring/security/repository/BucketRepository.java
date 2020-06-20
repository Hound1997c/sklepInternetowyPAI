package com.memorynotfound.spring.security.repository;

import com.memorynotfound.spring.security.model.Bucket;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket,Long> {
    List<Bucket> findAll();
    List<Bucket> findAllByUser(User user);
    Bucket save(Bucket bucket);
    Bucket findByUserAndProduct(User user, Product product);
    void deleteAllByUser(User user);
    void delete(Bucket bucket);
}
