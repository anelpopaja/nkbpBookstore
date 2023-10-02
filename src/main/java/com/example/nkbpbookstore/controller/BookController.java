package com.example.nkbpbookstore.controller;

import com.example.nkbpbookstore.model.BookRequest;
import com.example.nkbpbookstore.service.BookService;
import com.example.nkbpbookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = {"", "/"})
    Flux<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(value = {"", "/{title}"})
    Mono<Book> getOneByTitle(@PathVariable String title) {
        System.out.println("Getting book by title:");
        return bookService.getOneByTitle(title);
    }

    @PostMapping("/add1")
    public Void addBookWithGenresAndAuthors1(@RequestBody BookRequest bookRequest) {
        System.out.println(bookRequest);

        bookService.addBookWithGenresAndAuthors(bookRequest);


        return null;
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Book>> addBookWithGenresAndAuthors(@RequestBody BookRequest bookRequest) {
        return bookService.addBookWithGenresAndAuthors(bookRequest)
                .map(book -> ResponseEntity.ok(book))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    @DeleteMapping("/delete/{title}")
    public Mono<ResponseEntity<Void>> deleteBookByTitle(@PathVariable String title) {
        return bookService.deleteBookByTitle(title)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                });
    }
}