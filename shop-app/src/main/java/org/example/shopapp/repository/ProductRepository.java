package org.example.shopapp.repository;

import org.example.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity < 10 ORDER BY p.stockQuantity ASC")
    List<Product> findLowStockProducts();
}