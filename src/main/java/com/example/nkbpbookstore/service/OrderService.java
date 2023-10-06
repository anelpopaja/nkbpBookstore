package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Book;
import com.example.nkbpbookstore.model.Customer;
import com.example.nkbpbookstore.model.Order;
import com.example.nkbpbookstore.model.OrderRequest;
import com.example.nkbpbookstore.repository.BookRepository;
import com.example.nkbpbookstore.repository.CustomerRepository;
import com.example.nkbpbookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Flux<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }


    public Mono<Order> createOrder(OrderRequest orderRequest) {
        List<String> bookIds = orderRequest.getBookIds();
        String customerId = orderRequest.getCustomerId();

        // Fetch books from the database based on their IDs
        Flux<Book> bookFlux = Flux.fromIterable(bookIds)
                .flatMap(bookId -> bookRepository.findById(bookId));

        // Fetch customer from the database based on their ID
        Mono<Customer> customerMono = customerRepository.findById(customerId);

        return Mono.zip(bookFlux.collectList(), customerMono)
                .flatMap(tuple -> {
                    List<Book> books = tuple.getT1();
                    Customer customer = tuple.getT2();


                    Order order = new Order();
                    order.setBooks(books);
                    order.setCustomerId(customerId);


                    Double total = orderRequest.getTotal();
                    order.setTotal(total);

                    // Save the order to the database
                    return orderRepository.save(order)
                            .flatMap(savedOrder -> {
                                // Add the order to the customer's list of orders
                                customer.getOrders().add(savedOrder);
                                return customerRepository.save(customer).thenReturn(savedOrder);
                            });


                });
    }

    public Mono<Boolean> deleteOrderById(String id) {
        return orderRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return orderRepository.deleteById(id).then(Mono.just(true));
                    } else {
                        return Mono.just(false);
                    }
                });
    }


}



