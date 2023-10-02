package com.example.nkbpbookstore.repository;


import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.model.Customer;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveNeo4jRepository<Customer, String> {
    @Query("MATCH (c:Customer {username: $username}) RETURN c")
    Mono<Customer> findOneByUsername(String username);

    @Query("MATCH (c:Customer) RETURN c")
    Flux<Customer> findAll();

    @Query("MATCH (c:Customer {username: $username}) DELETE c")
    Mono<Void> deleteByUsername(String username);
}
