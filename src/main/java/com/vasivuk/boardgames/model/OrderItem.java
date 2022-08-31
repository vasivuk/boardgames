package com.vasivuk.boardgames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Klasa predstavlja stavku proudžbine
 * @author Vale
 */

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {


    /**
     * Identifikacioni broj stavke porudzbine, generisana vrednost
     */
    @Id
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_sequence"
    )
    private Long id;
    /**
     * Broj kupljenih proizvoda ovog tipa
     */
    private int quantity;


    /**
     * Ime proizvoda sa kojim je vezana stavka prikazano kao String
     */
    private String productName;
    /**
     * Ukupna cena stavke, zbog perzistencije ubacena, kao BigDecimal
     */
    @NotBlank(message = "Order item must have a price")
    @Positive
    private BigDecimal subTotal;

    /**
     * Proizvod koji je vezan za stavku porudzbine
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotBlank(message = "Order Item has to have a product attached to it.")
    private Product product;

    /**
     * Referenca ka porudzbini
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Order order;
}
