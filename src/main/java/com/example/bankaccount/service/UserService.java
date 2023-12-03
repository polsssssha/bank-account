package com.example.bankaccount.service;

import com.example.bankaccount.model.User;
import com.example.bankaccount.model.request.UserCreateDTO;
import com.example.bankaccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    public User getUserByUsername(String username){
        var entity = repository.findByUsername(username);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new RuntimeException("user not found");
        }
    }
    public User getUserById(Long id) {
        var entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new RuntimeException("user not found");
        }
    }
    public List<User> getUsers(){
        var list = new ArrayList<User>();
        repository.findAll().forEach(list::add);
        return list;
    }
    public void createUser(UserCreateDTO user) {
        var password = passwordEncoder.encode(user.getPassword());
        repository.save(new User(user.getUsername(), password));
    }
}
