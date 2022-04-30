package com.api.repositories;

import java.util.List;

import com.api.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where u.id > :id")
    public List<User> findAllMoreThan(@Param("id") Long id);

    public List<User> findByIdGreaterThan(Long id);

    // CamelCase Active
    public List<User> findByName(String name);

    // CamelCase Enable
    public List<User> findByNameIgnoreCase(String name);

}
