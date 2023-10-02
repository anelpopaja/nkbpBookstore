package com.example.nkbpbookstore.repository;

import com.example.nkbpbookstore.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {



}
