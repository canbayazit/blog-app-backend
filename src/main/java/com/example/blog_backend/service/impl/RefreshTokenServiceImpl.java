package com.example.blog_backend.service.impl;

import com.example.blog_backend.entity.RefreshTokenEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.exception.TokenRefreshException;
import com.example.blog_backend.mapper.RefreshTokenMapper;
import com.example.blog_backend.model.responseDTO.RefreshTokenResponseDTO;
import com.example.blog_backend.repository.RefreshTokenRepository;
import com.example.blog_backend.repository.UserRepository;
import com.example.blog_backend.service.RefreshTokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   UserRepository userRepository,
                                   RefreshTokenMapper refreshTokenMapper) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenMapper = refreshTokenMapper;
    }

    public RefreshTokenResponseDTO createRefreshToken(String email){
        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Could not find User with email =" + email));

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(600000));// set expiry of refresh token to 10 minutes - you can configure it application.properties file
        refreshTokenRepository.save(refreshTokenEntity);
        return refreshTokenMapper.entityToDTO(refreshTokenEntity);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getUuid() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
