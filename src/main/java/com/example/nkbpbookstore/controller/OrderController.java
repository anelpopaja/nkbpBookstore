package com.example.nkbpbookstore.controller;

import com.example.nkbpbookstore.model.Order;
import com.example.nkbpbookstore.model.OrderRequest;
import com.example.nkbpbookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Order>> addOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Entering order controller");
        return orderService.createOrder(orderRequest)
                .map(savedOrder -> ResponseEntity.status(HttpStatus.CREATED).body(savedOrder))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteOrderById(@PathVariable String id) {
        return orderService.deleteOrderById(id)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Order with ID " + id + " deleted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + id + " not found.");
                    }
                });
    }

    // Add more endpoints as needed (e.g., getOrders, getOrderById, etc.)
}
