package com.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.models.User;
import com.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @Autowired // Disponibiliza a utilização do Repositorio para a classe
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User user(@PathVariable("id") Long id) {

        Optional<User> userFind = users.stream().filter((user) -> user.getId() == id).findFirst();

        if (userFind.isPresent()) {
            return userFind.get();
        }

        return null;
    }

    @GetMapping("/list")
    public List<User> list() {

        return users;
    }

    @PostMapping("/")
    public User user(@RequestBody User user) {

        return this.userRepository.save(user);

    }

}
