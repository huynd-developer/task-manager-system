package org.example.sneakerstoremanagement.service;
import org.example.sneakerstoremanagement.entity.*;
import org.example.sneakerstoremanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartRepo;

    public List<CartItem> getCartItemsByUser(Integer userId) {
        return cartRepo.findByUserId(userId);
    }

    public void addToCart(CartItem item) {
        cartRepo.save(item);
    }

    public void clearCartByUser(Integer userId) {
        cartRepo.deleteByUserId(userId);
    }
}
