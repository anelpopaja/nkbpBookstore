package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Author;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.repository.AuthorRepository;
import com.example.nkbpbookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Transactional
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Mono<Author> findAuthorByNameAndSurname(String name, String surname) {
        return authorRepository.findByNameAndSurname(name, surname);
    }


    public Mono<Author> findAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public Mono<Author> addAuthor(Author author) {
        return (authorRepository.save(author));
    }

    public Mono<Boolean> deleteAuthorByNameAndSurname(String name, String surname) {
        return authorRepository.findByNameAndSurname(name, surname)
                .flatMap(author -> authorRepository.delete(author).thenReturn(true))
                .defaultIfEmpty(false);
    }



    @Transactional
    public Mono<Boolean> deleteAuthorById(String id) {
        return authorRepository.findById(id)
                .flatMap(author -> authorRepository.delete(author).thenReturn(true))
                .defaultIfEmpty(false);
    }




}
