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


    @PostMapping("/add")
    public Mono<ResponseEntity<Author>> addAuthor(@RequestBody Author newAuthor) {

        return authorService.addAuthor(newAuthor).map(savedAuthor -> ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{name}/{surname}")
    public Mono<Author> getAuthorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return authorService.findAuthorByNameAndSurname(name, surname);
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthorById(@PathVariable String id) {
        return authorService.findAuthorById(id);
    }

    @DeleteMapping("/delete/{name}/{surname}")
    public Mono<ResponseEntity<String>> deleteByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return authorService.deleteAuthorByNameAndSurname(name, surname)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Author " + name + " " + surname + " deleted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author " + name + " " + surname + " not found.");
                    }
                });
    }


    @DeleteMapping("delete/{id}")
    public Mono<ResponseEntity<String>> deleteAuthorById(@PathVariable String id) {
        return authorService.deleteAuthorById(id)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Author with id " + id + " deleted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id " + id + " not found.");
                    }
                });
    }

}