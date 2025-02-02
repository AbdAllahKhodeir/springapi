package com.example.nobsv2.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // @NotNull(message = "Name is required")
    @Column(name = "name")
    private String name;

    // @Size(min = 20, message = "Description must be 20 characters")
    @Column(name = "description")
    private String description;

    // @PositiveOrZero(message = "Price cannot be negative")
    @Column(name = "price")
    private Double price;
}
