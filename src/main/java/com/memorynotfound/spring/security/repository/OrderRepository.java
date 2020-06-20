package com.memorynotfound.spring.security.repository;

import com.memorynotfound.spring.security.model.Order;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository /**/
public interface OrderRepository  extends JpaRepository<Order, Long>{
    Order findOrderByUserAndProduct(User user, Product product);
    Order findOrderByUserAndProductAndState(User user, Product product,String state);
    List<Order> findAllByUserAndProduct(User user,Product product);

}
