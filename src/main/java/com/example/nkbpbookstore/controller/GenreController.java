package com.example.nkbpbookstore.controller;

import com.example.nkbpbookstore.model.Genre;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.service.GenreService;
import com.example.nkbpbookstore.service.BookService;
import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.service.GenreService;
import com.example.nkbpbookstore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/genres")
public class GenreController {
    GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path = {"", "/"})
    Flux<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping(value = {"", "/{name}"})
    Mono<Genre> getOneByName(@PathVariable String name) {
        return genreService.getOneByName(name);
    }

    @GetMapping("/{id}")
    public Mono<Genre> getGenreById(@PathVariable String id) {
        return genreService.findGenreById(id);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Genre>> addGenre(@RequestBody Genre newGenre) {

        return genreService.addGenre(newGenre).map(savedGenre -> ResponseEntity.status(HttpStatus.CREATED).body(savedGenre))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/delete/{name}")
    public Mono<ResponseEntity<String>> deleteGenreByName(@PathVariable String name) {
        return genreService.deleteGenreByName(name)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Genre " + name + " deleted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre " + name + " not found.");
                    }
                });


    }


}