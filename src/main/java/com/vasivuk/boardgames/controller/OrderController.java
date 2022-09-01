package com.vasivuk.boardgames.controller;

import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.exception.ForbiddenResourceException;
import com.vasivuk.boardgames.exception.InvalidOrderException;
import com.vasivuk.boardgames.model.Order;
import com.vasivuk.boardgames.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.vasivuk.boardgames.configuration.Routes.ID;
import static com.vasivuk.boardgames.configuration.Routes.USER_COMMON;

/**
 * OrderController obradjuje zahteve vezane za porudzbinu
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:3000/**", allowCredentials = "true")
public class OrderController {

    /**
     * Servis koji sadrzi poslovnu logiku vezanu za porudzbine
     */
    private final OrderService orderService;

    /**
     * Metoda vraca sve porudzbine od nekog korisnika na osnovu dopremljenog id-a
     * @param userId id korisnika koji zahteva da vidi svoje porudzbine
     * @return lista porudzbina od korisnika
     */
    @GetMapping(USER_COMMON + ID + "/orders")
    public List<Order> fetchOrdersByUserId(@PathVariable("id") Long userId) {
        return orderService.fetchOrdersByUserId(userId);
    }

    /**
     * Vraca detalje porudzbine na osnovu identifikacionog broja
     * @param userId korisnicki identifikacioni broj
     * @param orderId identifikacioni broj porudzbine
     * @return porudzbina sa odredjenim identifikacionim brojem
     * @throws ForbiddenResourceException u slucaju da korisnik koji zahteva da vidi odredjenu porudzbinu nije njen vlasnik
     * @throws EntityNotFoundException u slucaju da porudzbina sa odredjenim id-jem ne postoji
     */
    @GetMapping(USER_COMMON + ID + "/orders" + "/{orderId}")
    public Order fetchOrderById(@PathVariable("id") Long userId, @PathVariable("orderId") Long orderId) throws ForbiddenResourceException, EntityNotFoundException {
        return orderService.fetchOrderById(userId, orderId);
    }

    /**
     * Metoda unosi u sistem novu porudzbinu
     * @param order nova porudzbina koju treba uneti u sistem
     * @return kreirana porudzbina
     * @throws InvalidOrderException u slucaju da neki deo porudzbine nije ispravan
     */
    @PostMapping("/api/orders")
    public Order createOrder(@Valid @RequestBody Order order) throws InvalidOrderException {
        return orderService.createOrder(order);
    }
}
