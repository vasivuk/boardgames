package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Klasa predstavlja kategoriju proizvoda
 * @author Vale
 */

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    /**
     *  Identifikacioni broj kategorije, generisana vrednost
     */
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;

    /**
     * Ime kategorije kao String
     */
    @NotBlank(message = "Name is mandatory")
    @Size(max = 30)
    @Column(unique = true)
    private String name;

    /**
     * Opis kategorije kao String
     */
    @Size(max = 1000)
    private String description;

}
