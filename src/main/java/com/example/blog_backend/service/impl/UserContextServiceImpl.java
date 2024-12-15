package com.example.blog_backend.service.impl;

import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.repository.UserRepository;
import com.example.blog_backend.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextServiceImpl implements UserContextService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getCurrentAuthenticatedUser() {
        // Not. SecurityContextHolder başarılı login olmuş kullanıcının kimlik bilgilerini tutar. Eğer kullanıcı username ile giriş
        // yaptıysa getName() methodu username'i getirir email ile giriş yaptıysa email'ini getirir.
        String currentPrincipalEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentPrincipalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
