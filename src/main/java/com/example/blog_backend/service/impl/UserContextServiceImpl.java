package com.example.blog_backend.service.impl;

import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.repository.UserRepository;
import com.example.blog_backend.service.UserContextService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserContextServiceImpl implements UserContextService {
    private final UserRepository userRepository;

    public UserContextServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserEntity getRequiredAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new UsernameNotFoundException("User not authenticated!");
        }
        String currentPrincipalEmail = auth.getName();
        return userRepository.findByEmail(currentPrincipalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public Optional<UserEntity> getOptionalAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        String currentPrincipalEmail = auth.getName();
        return userRepository.findByEmail(currentPrincipalEmail);
    }
}
