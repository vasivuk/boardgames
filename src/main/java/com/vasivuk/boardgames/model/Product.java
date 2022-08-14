package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 40)
    private String name;
    @Size(max = 20)
    private String slug;
    private String imageUrl;
    @Positive
    private BigDecimal price;
    @Size(max = 1000)
    private String description;
    @Positive
    private int gameTime;
    private String numberOfPlayers;
    @Min(0) @Max(5)
    private double complexity;
    @Min(0) @Max(5)
    private double rating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(
                    name = "product_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id"
            )
    )
    private Set<Category> categories;

}
