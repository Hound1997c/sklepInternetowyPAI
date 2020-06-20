package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Order;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceIml implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void deleteOrderbyProductAndUser(Product product, User user) {
        Order order = orderRepository.findOrderByUserAndProduct(user,product);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllActive() {
        List<Order> orderList = orderRepository.findAll();
        List<Order> outOrderList = new ArrayList<>();
        for(Order order : orderList){
            System.out.println("czy on jest aktyeny: " + order.getState());
            if(order.getState().equals("waiting")){
                outOrderList.add(order);
            }
        }
        return outOrderList;
    }

    @Override
    public List<Order> makeOrders(User user) {

        return null;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderByUserAndProduct(User user, Product product) {
        return orderRepository.findOrderByUserAndProduct(user,product);
    }

    @Override
    public Order findOrderByUserAndProductAndState(User user, Product product, String state) {
        return orderRepository.findOrderByUserAndProductAndState(user,product,state);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order saveAndFlush(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    /*@Override
    public Order makeNewOrder(Order order, Bucket bucket) {

        order.setUser(bucket.getUser());
        order.setProduct(bucket.getProduct());
        order.setAmount(bucket.getAmount());
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }*/
}
