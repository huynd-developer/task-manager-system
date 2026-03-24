package org.example.sneakerstoremanagement.repository;

import org.example.sneakerstoremanagement.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}
