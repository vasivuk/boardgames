package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.ReviewRepository;
import com.vasivuk.boardgames.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }
}
