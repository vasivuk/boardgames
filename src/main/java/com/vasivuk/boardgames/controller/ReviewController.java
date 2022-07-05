package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.service.impl.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

}
