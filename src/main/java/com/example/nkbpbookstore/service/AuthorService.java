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

    @Transactional
    public Mono<Author> getOneByNameAndSurname(String name, String surname){
        return authorRepository.findOneByNameAndSurname(name, surname);
    }

    @Transactional
    public Mono<Author> addAuthor(Author author) {
        return (authorRepository.save(author));
    }

    public Mono<Void> deleteAuthorByNameAndSurname(String name, String surname) {
        System.out.println("hi");
        System.out.println(name + surname);
        return authorRepository.deleteByNameAndSurname(name, surname);
    }
}
