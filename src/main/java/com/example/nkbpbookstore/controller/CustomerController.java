package com.example.nkbpbookstore.controller;

import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.service.BookService;
import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = {"", "/"})
    Flux<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(value = {"", "/{name}"})
    Mono<Customer> getOneByUsername(@PathVariable String name) {
        return customerService.getOneByUsername(name);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Customer>> addCustomer(@RequestBody Customer newCustomer) {

        return customerService.addCustomer(newCustomer).map(savedCustomer -> ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{username}")
    public Mono<ResponseEntity<Object>> deleteCustomerByUsername(@PathVariable String username) {
        System.out.println("Got the request");

        return customerService.deleteCustomerByUsername(username)
                .then(Mono.just(ResponseEntity.ok().build()))
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));


    }

}