package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
