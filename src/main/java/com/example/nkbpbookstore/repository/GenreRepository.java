package com.example.nkbpbookstore.repository;


import com.example.nkbpbookstore.model.Genre;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

public interface GenreRepository  extends ReactiveNeo4jRepository<Genre, String> {
    @Query("MATCH (g:Genre {name: $name}) RETURN g")
    Mono<Genre> findOneByName(String name);


    @Query("MATCH (g:Genre {name: $name}) DELETE g")
    Mono<Void> deleteByName(String name);
}
