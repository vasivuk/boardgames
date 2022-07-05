package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Predstavlja recenziju korisnika nad nekim proizvodom.
 */
@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Data
public class Review {

    /**
     * Identifikacioni broj recenzije, automatski se generiše
     */
    @GeneratedValue
    @Id
    private Long id;
    /**
     * Broj zvezdica izražen u vrednosti od 1 do 5
     */
    private Integer rating;
    /**
     * Tekst recenzije, koji korisnik može da izabere da ispiše
     */
    private String reviewText;
    /**
     * Datum recenzije
     */
    private Date reviewDate;

    /**
     * Korisnik koji piše recenziju
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_review"))
    private User user;

    /**
     * Proizvod nad kojim se piše recenzija
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_review"))
    private Product product;

}
