package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
