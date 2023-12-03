package com.example.bankaccount.controller;

import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.User;
import com.example.bankaccount.model.request.UserCreateDTO;
import com.example.bankaccount.service.AccountService;
import com.example.bankaccount.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankAccount/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(service.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDTO user) {
        service.createUser(user);
    }
}
