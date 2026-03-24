package org.example.sneakerstoremanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderItem")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sneaker_id")
    private Sneaker sneaker;

    private Integer quantity;
    private Double price;
}
