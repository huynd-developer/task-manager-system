package org.example.sneakerstoremanagement.service;

import org.example.sneakerstoremanagement.entity.Order;
import org.example.sneakerstoremanagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;

    public void createOrder(Order order) {
        order.setOrderTime(LocalDateTime.now());
        orderRepo.save(order);
    }

    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepo.findByUserId(userId);
    }
}
