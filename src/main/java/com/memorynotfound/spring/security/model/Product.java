package com.memorynotfound.spring.security.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private long cost;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "productImage_id", referencedColumnName = "id")
    //private DBFile productImage = null;

    private String Category;


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @OneToMany(targetEntity = Bucket.class,cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<Bucket> bucketList;

    @OneToMany(targetEntity = Order.class,cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "product")
    private Set<Order> orderList;

    /*@Lob
    private byte[] productImage;*/


    public Set<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(Set<Order> orderList) {
        this.orderList = orderList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Set<Bucket> getBucketList() {
        return this.bucketList;
    }

    public void setUserBucketList(Set<Bucket> bucketList) {
        this.bucketList = bucketList;
    }

    public void setBucketList(Set<Bucket> bucketList) {
        this.bucketList = bucketList;
    }

    /*public DBFile getProductImage() {
        return productImage;
    }

    public void setProductImage(DBFile productImage) {
        this.productImage = productImage;
    }*/

    /*public byte [] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte [] productImage) {
        this.productImage = productImage;
    }*/
}
