package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Author;
import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.model.BookRequest;
import com.example.nkbpbookstore.model.Genre;
import com.example.nkbpbookstore.repository.AuthorRepository;
import com.example.nkbpbookstore.repository.BookRepository;
import com.example.nkbpbookstore.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    AuthorRepository authorRepository;


    @Transactional
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Mono<Book> getOneByTitle(String title){
        return bookRepository.findByTitle(title);
    }


    @Transactional
    public Mono<Boolean> deleteBookByTitle(String title) {
        return bookRepository.deleteByTitle(title).thenReturn(true) // Return true if deletion was successful
                .defaultIfEmpty(false); // Return false if book wasn't found
    }

    @Transactional
    public Flux<Book> findAllByAuthorsNameAndAuthorsSurname(String authorName, String authorSurname) {
        return bookRepository.findAllByAuthorsNameAndAuthorsSurname(authorName, authorSurname);
    }

    @Transactional
    public Mono<Book> addBookWithGenresAndAuthors(BookRequest bookRequest) {
        // Check if the book already exists
        return bookRepository.findOneByTitle(bookRequest.getTitle())
                .flatMap(existingBook -> {
                    System.out.println("Book with title " + bookRequest.getTitle() + " already exists.");
                    return Mono.just(existingBook);
                })
                .switchIfEmpty(
                        // If the book doesn't exist, proceed with creating it
                        Flux.fromIterable(bookRequest.getAuthors())
                                .flatMap(temp -> {
                                    return authorRepository.findByNameAndSurname(temp.getName(), temp.getSurname())
                                            .switchIfEmpty(Mono.defer(() -> {
                                                Author author = new Author();
                                                author.setName(temp.getName());
                                                author.setSurname(temp.getSurname());
                                                return authorRepository.save(author);
                                            }));
                                })
                                .collectList()
                                .flatMap(authors -> {
                                    return Flux.fromIterable(bookRequest.getGenres())
                                            .flatMap(genreName -> {
                                                return genreRepository.findOneByName(genreName)
                                                        .switchIfEmpty(Mono.defer(() -> {
                                                            Genre genre = new Genre(genreName);
                                                            return genreRepository.save(genre);
                                                        }));
                                            })
                                            .collectList()
                                            .flatMap(genres -> {
                                                Book book = new Book(
                                                        bookRequest.getTitle(),
                                                        bookRequest.getIsbn(),
                                                        bookRequest.getPublishingDate(),
                                                        bookRequest.getPrice(),
                                                        bookRequest.getDescription()
                                                );
                                                book.setAuthors(new ArrayList<>(authors));
                                                book.setGenres(new ArrayList<>(genres));

                                                return bookRepository.save(book);
                                            });
                                })
                );
    }






}
