package org.example.shopapp.repository;

import org.example.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findAllByOrderByOrderDateDesc();

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalRevenue();

    List<Order> findTop5ByOrderByOrderDateDesc();
}