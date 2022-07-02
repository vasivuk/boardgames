package com.vasivuk.boardgames.repository;

import com.vasivuk.boardgames.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
