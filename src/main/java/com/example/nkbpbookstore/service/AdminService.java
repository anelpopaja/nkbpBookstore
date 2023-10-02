package com.example.nkbpbookstore.service;

import com.example.nkbpbookstore.model.Admin;
import com.example.nkbpbookstore.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Transactional
    public Flux<Admin> getAdmins() {
        return adminRepository.findAll();
    }

    @Transactional
    public Mono<Admin> getOneByUsername(String username){
        return adminRepository.findOneByUsername(username);
    }

    @Transactional
    public Mono<Admin> addAdmin(Admin admin) {
        return (adminRepository.save(admin));
    }

    @Transactional
    public Mono<Boolean> deleteAdminByUsername(String username) {
        return adminRepository.findOneByUsername(username)
                .flatMap(admin -> adminRepository.delete(admin).thenReturn(true))
                .defaultIfEmpty(false);
    }

}
