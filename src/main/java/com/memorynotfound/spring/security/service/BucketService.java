package com.memorynotfound.spring.security.service;


import com.memorynotfound.spring.security.model.Bucket;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BucketService {
    //List<Bucket>
    Bucket save(Bucket bucket);
    Bucket addProductToBucket(Bucket bucket, User user, Product product);
    List<Bucket> getUserBucket(User user);
    Bucket findByUserAndProduct(User user, Product product);
    Bucket inkrementBucket(Bucket bucket);
    long sumElements(User user);
    void deleteAllByUser(User user);
    List<Bucket> findAllByUser(User user);
    void delete(Bucket bucket);
}
