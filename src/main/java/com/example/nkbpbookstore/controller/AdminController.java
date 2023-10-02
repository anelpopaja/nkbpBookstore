package com.example.nkbpbookstore.controller;


import com.example.nkbpbookstore.model.Admin;
import com.example.nkbpbookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admins")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = {"", "/"})
    Flux<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping(value = {"", "/{name}"})
    Mono<Admin> getOneByUsername(@PathVariable String name) {
        return adminService.getOneByUsername(name);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Admin>> addAdmin(@RequestBody Admin newAdmin) {

        return adminService.addAdmin(newAdmin).map(savedAdmin -> ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{username}")
    public Mono<ResponseEntity<String>> deleteAdminByUsername(@PathVariable String username) {
        return adminService.deleteAdminByUsername(username)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Admin with username " + username + " deleted successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin with username " + username + " not found.");
                    }
                });
    }
    
}