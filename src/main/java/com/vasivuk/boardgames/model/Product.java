package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Data
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int gameTime;
    private String numberOfPlayers;
    private double complexity;
    private double rating;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany
    private Set<Category> categories;

}
