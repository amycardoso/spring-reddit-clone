package com.clone.reddit.service;

import static java.time.Instant.now;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.reddit.dto.RegisterRequest;
import com.clone.reddit.models.User;
import com.clone.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthService {
 
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
 
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);
 
        userRepository.save(user);
    }
 
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}