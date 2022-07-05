package com.vasivuk.boardgames.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Data
public class OrderItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int quantity;
    private BigDecimal itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_order_item"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_order_item"))
    private Order order;

}
