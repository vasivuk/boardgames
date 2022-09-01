package com.vasivuk.boardgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Klasa predstavlja korisnikovu porudžbinu.
 * @author Vale
 */

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    /**
     * Identifikacioni broj porudzbine, generisana vrednost
     */
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long id;
    /**
     * Datum prihvatanja porudzbine
     */
    private Date dateSubmitted;
    /**
     * Ukupna cena porudzbine kao BigDecimal
     */
    private BigDecimal totalPrice;

    /**
     * Korisnik koji je napravio porudzbinu
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "AppUser is mandatory")
    private AppUser user;

    /**
     * Stavke porudzbine kao lista
     */
    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    @NotEmpty(message = "Can't have order without order items")
    private List<OrderItem> orderItems;

    /**
     * Detalji koje je korisnik poslao, vezani za adresu na koju se salje porudzbina i licni detalji za kontakt
     */
    @Embedded
    private OrderUserDetails userDetails;
}
