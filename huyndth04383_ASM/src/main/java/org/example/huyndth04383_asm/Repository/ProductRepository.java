package org.example.huyndth04383_asm.Repository;

import org.example.huyndth04383_asm.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContaining(String q, Pageable pageable);
}

