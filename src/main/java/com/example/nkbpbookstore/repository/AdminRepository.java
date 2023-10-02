package com.example.nkbpbookstore.repository;


import com.example.nkbpbookstore.model.Admin;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminRepository extends ReactiveNeo4jRepository<Admin, String> {
    @Query("MATCH (c:Admin {title: $username}) RETURN c")
    Mono<Admin> findOneByUsername(String username);

    @Query("MATCH (c:Admin) RETURN c")
    Flux<Admin> findAll();
}
