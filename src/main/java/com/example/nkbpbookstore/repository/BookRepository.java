package com.example.nkbpbookstore.repository;


import com.example.nkbpbookstore.model.Book;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BookRepository extends ReactiveNeo4jRepository<Book, String> {
    @Query("MATCH (m:Book {title: $title}) RETURN m")
    Mono<Book> findOneByTitle(String title);

    @Query("MATCH (m:Book) RETURN m")
    Flux<Book> findAll();

    Mono<Void> deleteByTitle(String title);
}
