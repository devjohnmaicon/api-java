package com.api.controllers;

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

    @Autowired // Disponibiliza a utilização do Repositorio para a classe
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User user(@PathVariable("id") Long id) {

        Optional<User> userFind = this.userRepository.findById(id);

        if (!userFind.isPresent()) {
            return null;
        }

        return userFind.get();
    }

    @GetMapping("/list")
    public List<User> list() {

        return this.userRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public List<User> listMoreThan(@PathVariable("id") Long id) {

        // return this.userRepository.findAllMoreThan(id); Method with Query

        // Method for search with id
        return this.userRepository.findByIdGreaterThan(id);
    }

    @GetMapping("/findByName/{name}")
    public List<User> findByName(@PathVariable("name") String name) {

        // return this.userRepository.findByName(name);

        return this.userRepository.findByNameIgnoreCase(name);
    }

    @PostMapping("/")
    public User user(@RequestBody User user) {

        return this.userRepository.save(user);

    }

}
