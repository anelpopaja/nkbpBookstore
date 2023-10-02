package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.repository.AdminRepository;
import com.example.nkbpbookstore.repository.BookRepository;
import com.example.nkbpbookstore.repository.CustomerRepository;
import org.neo4j.cypherdsl.core.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Mono<Customer> getOneByUsername(String username) {
        return customerRepository.findOneByUsername(username);
    }

    @Transactional
    public Mono<Customer> addCustomer(Customer customer) {

        if (customerRepository.findOneByUsername(customer.getUsername()).hasElement().block()) {
            System.out.println("Operation Failed, Customer already exists.");
            return Mono.just(new Customer("Operation Failed", "", null, "", "Book already exists."));
        } else {
            System.out.println("Good to go");
            return (customerRepository.save(customer));
        }

    }

    public Mono<Void> deleteCustomerByUsername(String username) {
        return customerRepository.deleteByUsername(username);
    }
}
