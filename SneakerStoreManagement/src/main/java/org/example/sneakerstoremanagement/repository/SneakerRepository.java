package org.example.sneakerstoremanagement.repository;

import org.example.sneakerstoremanagement.entity.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SneakerRepository extends JpaRepository<Sneaker,Integer> {
    List<Sneaker> findByUserId(Integer userId);
    List<Sneaker> findByNameContainingIgnoreCase(String name);
}
