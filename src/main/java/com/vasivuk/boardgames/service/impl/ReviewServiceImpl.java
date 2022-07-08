package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.repository.ReviewRepository;
import com.vasivuk.boardgames.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }
}
