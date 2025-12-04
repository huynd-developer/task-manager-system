package org.example.sneakerstoremanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Sneaker")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String brand;
    private String color;
    private Double price;
    private String condition; // new, used
    private String image;     // link ảnh
    private String note;

    private Boolean favorite = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // người sở hữu
}
