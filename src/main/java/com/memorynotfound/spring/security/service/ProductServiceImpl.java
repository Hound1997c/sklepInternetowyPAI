package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.repository.ProductRepository;
import com.memorynotfound.spring.security.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public Product findByName(String name){
        return productRepository.findByName(name);
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product addNewProduct(ProductDto prodDto) {
        String namePD = prodDto.getName();
        if(namePD==null || namePD.length()<=3) return null;
        Product anotherProd = productRepository.findByName(namePD);
        if(anotherProd!=null) return null;
        long costPD = prodDto.getCost();
        if(costPD<=0) return null;
        String catPD = prodDto.getCategory();
        if(catPD==null) return null;
        /*byte [] imagePD = prodDto.getItemImage();
        product.setProductImage(imagePD);
        if(imagePD == null) return null;*/
        Product product = new Product();
        product.setName(namePD);
        product.setCost(costPD);
        product.setCategory(catPD);
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<Product> findAllGroupByCategory() {
        /*List<Product> productList = productRepository.findAll();
        Map<String,List<Product>> groupByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
        List<Product> resualtList = new ArrayList<>();
        for(String cat : groupByCategory.keySet()){
            List<Product> catList = productRepository.findAllByCategory(cat);
            for(Product prod : catList)
            resualtList.add(prod);
        }
        return resualtList;*/
        return null;
    }
}
