package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Bucket;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketServiceImpl implements BucketService{

    @Autowired
    ProductService productService;
    @Autowired
    BucketRepository bucketRepository;
    @Override
    public Bucket save(Bucket bucket) {
        return null;
    }

    @Override
    public Bucket addProductToBucket(Bucket bucket, User user, Product product) {
        System.out.println("addProductToBucket");
        bucket.setUser(user);
        System.out.println("setUser " + user.getId());
        bucket.setProduct(product);
        System.out.println("setProd " + product.getId());
        return bucketRepository.save(bucket);
    }

    @Override
    public List<Bucket> getUserBucket(User user) {

        List<Bucket> userBucket = bucketRepository.findAllByUser(user);

        return userBucket;
    }

    @Override
    public Bucket findByUserAndProduct(User user, Product product) {
        return bucketRepository.findByUserAndProduct(user,product);
    }

    @Override
    public Bucket inkrementBucket(Bucket bucket) {
        bucket.setAmount(bucket.getAmount()+1);
        return bucketRepository.saveAndFlush(bucket);
    }

    @Override
    public long sumElements(User user) {
        List<Bucket> userBucket = bucketRepository.findAllByUser(user);
        long sum =0;
        for (Bucket bckt : userBucket){
            sum += (bckt.getAmount() * bckt.getProduct().getCost());
        }
        return sum;
    }

    @Override
    public void deleteAllByUser(User user) {
        bucketRepository.deleteAllByUser(user);
    }

    @Override
    public List<Bucket> findAllByUser(User user) {
        return bucketRepository.findAllByUser(user);
    }

    @Override
    public void delete(Bucket bucket) {
        bucketRepository.delete(bucket);
    }
}
