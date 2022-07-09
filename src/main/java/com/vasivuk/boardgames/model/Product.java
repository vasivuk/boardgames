package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Data
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long productId;
    private String name;
    private BigDecimal price;
    private String description;
    private int gameTime;
    private String numberOfPlayers;
    private double complexity;
    private double rating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "productId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "categoryId"
            )
    )
    private Set<Category> categories;

}
