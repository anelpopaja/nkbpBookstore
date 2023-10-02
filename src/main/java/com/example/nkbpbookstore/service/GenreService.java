package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Genre;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.repository.GenreRepository;
import com.example.nkbpbookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Transactional
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    public Mono<Genre> getOneByName(String name ){
        return genreRepository.findOneByName(name);
    }

    @Transactional
    public Mono<Genre> addGenre(Genre newGenre) {
        // Check if a genre with the same name already exists
        return genreRepository.findOneByName(newGenre.getName())
                .flatMap(existingGenre -> Mono.just(existingGenre)) // Return existing genre
                .switchIfEmpty(genreRepository.save(newGenre)); // Save new genre if not found
    }

    public Mono<Void> deleteGenreByName(String name) {
        System.out.println("hi");
        System.out.println(name);
        return genreRepository.deleteByName(name);
    }
}
