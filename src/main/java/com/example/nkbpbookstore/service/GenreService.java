package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Genre;
import com.example.nkbpbookstore.repository.GenreRepository;
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
    public Mono<Genre> findGenreById(String id) {
        return genreRepository.findById(id);
    }


    @Transactional
    public Mono<Genre> addGenre(Genre newGenre) {
        // Check if a genre with the same name already exists
        return genreRepository.findOneByName(newGenre.getName())
                .flatMap(existingGenre -> Mono.just(existingGenre)) // Return existing genre
                .switchIfEmpty(genreRepository.save(newGenre)); // Save new genre if not found
    }

    @Transactional
    public Mono<Boolean> deleteGenreByName(String name) {
        return genreRepository.findByName(name)
                .flatMap(genre -> genreRepository.delete(genre).thenReturn(true))
                .defaultIfEmpty(false);
    }

}
