package com.memorynotfound.spring.security.entities;

import com.memorynotfound.spring.security.model.Product;

public class BucketProduct {
    private Product product;
    private long amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
