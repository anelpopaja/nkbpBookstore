package com.example.nkbpbookstore.controller;

import com.example.nkbpbookstore.model.Author;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.service.AuthorService;
import com.example.nkbpbookstore.service.BookService;
import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = {"", "/"})
    Flux<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping(value = {"", "/{name}"})
    Mono<Author> getOneByNameAndSurname(@PathVariable String name, String surname) {
        return authorService.getOneByNameAndSurname(name, surname);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Author>> addAuthor(@RequestBody Author newAuthor) {

        return authorService.addAuthor(newAuthor).map(savedAuthor -> ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<Object>> deleteAuthorByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        System.out.println("Got the request");

        return authorService.deleteAuthorByNameAndSurname(name, surname)
                .then(Mono.just(ResponseEntity.ok().build()))
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));


    }

}