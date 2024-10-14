package com.example.blog_backend.util.security;

import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmailWithRoles(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find User with email =" + email);
        }

        Set<GrantedAuthority> authorities = user.get().getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        return new User(email, user.get().getPassword(), authorities);
    }
}
