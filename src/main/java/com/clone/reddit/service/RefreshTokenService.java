package com.clone.reddit.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.Instant;
import java.util.UUID;

import com.clone.reddit.exception.SpringRedditException;
import com.clone.reddit.models.RefreshToken;
import com.clone.reddit.repository.RefreshTokenRepository;
 
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
 
    @Autowired RefreshTokenRepository refreshTokenRepository;
 
    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
 
        return refreshTokenRepository.save(refreshToken);
    }
 
    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }
 
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}