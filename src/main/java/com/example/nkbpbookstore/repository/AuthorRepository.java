package com.example.nkbpbookstore.repository;

import com.example.nkbpbookstore.model.Author;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveNeo4jRepository<Author, String> {

    Mono<Author> findByNameAndSurname(String name, String surname);

    //Mono<Void> deleteByNameAndSurname(String name, String surname);


    @Query("MATCH (a:Author {name: $name, surname: $surname}) DELETE a")
    Mono<Void> deleteByNameAndSurname(String name, String surname);

    Mono<Void> deleteById(String id);

}
