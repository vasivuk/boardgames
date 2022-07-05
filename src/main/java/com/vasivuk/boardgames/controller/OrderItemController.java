package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.service.impl.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService service;

}
