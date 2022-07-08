package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.service.impl.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl service;

}
