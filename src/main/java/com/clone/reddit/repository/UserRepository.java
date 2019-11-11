package com.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.reddit.models.User;

import java.util.Optional;
 
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}