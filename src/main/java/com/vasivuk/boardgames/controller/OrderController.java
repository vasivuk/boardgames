package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl service;

}
