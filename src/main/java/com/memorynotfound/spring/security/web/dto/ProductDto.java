package com.memorynotfound.spring.security.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ProductDto {

    @NotEmpty
    private String name;

    @NotNull
    private long cost;

    private String Category;
    /*@Lob
    private byte[] itemImage;


    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(MultipartFile itemImage) throws IOException {
        this.itemImage = itemImage.getBytes();
    } */

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
}
