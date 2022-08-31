package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Klasa predstavlja proizvod, tačnije društvenu igru u sistemu.
 * @author Vale
 */

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    /**
     * Identifikacioni broj proizvoda, generisana vrednost
     */
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
    @Size(max = 23)
    /**
     * Ime drustvene igre kao String
     */
    private String name;
    /**
     * URL Friendly ime drustvene igre
     */
    @Size(max = 23)
    private String slug;
    /**
     * URL ka slici drustvene igre koja se nalazi na serveru
     */
    private String imageUrl;
    /**
     * Cena drustvene igre kao BigDecimal
     */
    @Positive
    private BigDecimal price;
    /**
     * Opis drustvene igre kao String
     */
    @Size(max = 1000)
    private String description;
    /**
     * Prosecno vreme trajanja partije drustvene igre, izrazeno u minutima
     */
    @Positive
    private int gameTime;
    /**
     * Broj igraca drustvene igre, izrazen kao String (npr. 2-5, 3, 1-4)
     */
    private String numberOfPlayers;
    /**
     * Kompleksnost drustvene igre, od 0 do 5, na dve decimale.
     */
    @Min(0) @Max(5)
    private double complexity;
    /**
     * Ocena drustvene igre izrazena kao double od 0 do 5
     */
    @Min(0) @Max(5)
    private double rating;
    /**
     * Kolicina ovog proizvoda u zalihama
     */
    @Min(0)
    private int stockQuantity;

    /**
     * Kategorije koje su vezane za ovu drustenu igru
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
