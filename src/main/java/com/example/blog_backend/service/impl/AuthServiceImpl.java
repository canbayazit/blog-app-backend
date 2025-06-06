package com.example.blog_backend.service.impl;

import com.example.blog_backend.entity.RefreshTokenEntity;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.exception.AccountDeactivatedException;
import com.example.blog_backend.exception.AccountSuspendedException;
import com.example.blog_backend.exception.AlreadyExistsException;
import com.example.blog_backend.exception.TokenRefreshException;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.enums.RoleType;
import com.example.blog_backend.model.enums.UserStatus;
import com.example.blog_backend.model.requestDTO.LoginRequestDTO;
import com.example.blog_backend.model.requestDTO.RefreshTokenRequestDTO;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.model.responseDTO.JwtResponseDTO;
import com.example.blog_backend.model.responseDTO.RefreshTokenResponseDTO;
import com.example.blog_backend.repository.*;
import com.example.blog_backend.service.AuthService;
import com.example.blog_backend.service.RefreshTokenService;
import com.example.blog_backend.util.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authManager;

    private final JWTUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    @Override
    public void saveUserByRole(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new AlreadyExistsException("Email already exists in the database!");
        }

        UserEntity user = userMapper.requestDTOToEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleType.USER.name()).get());
        user.setRoles(roles);
        userRepository.save(user);
    }
    @Override
    public void saveAdminByRole(RegisterRequestDTO registerRequestDTO) {

        UserEntity user = userMapper.requestDTOToEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleType.ADMIN.name()).get());
        user.setRoles(roles);
        userRepository.save(user);
    }
    @Override
    public JwtResponseDTO login(LoginRequestDTO body){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
        Authentication authentication = authManager.authenticate(authInputToken);
        if (authentication.isAuthenticated()){
            // Kullanıcıyı veritabanından al
            UserEntity user = userRepository.findByEmail(body.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find User with email =" + body.getEmail()));

            // Kullanıcı durumunu kontrol et
            if (user.getStatus() == UserStatus.INACTIVE) {
                throw new AccountDeactivatedException("Your account is deactivated. Reactivate it to log in.");
            }

            if (user.getStatus() == UserStatus.SUSPENDED) {
                throw new AccountSuspendedException("Your account is suspended. Please contact support.");
            }

            RefreshTokenResponseDTO refreshTokenResponseDTO = refreshTokenService.createRefreshToken(body.getEmail());
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
            jwtResponseDTO.setAccessToken(jwtUtil.generateToken(body.getEmail()));
            jwtResponseDTO.setRefreshToken(refreshTokenResponseDTO.getUuid());
            return jwtResponseDTO;
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    // NOT refresh token rotation yapmayı öneriyorlar.
    @Override
    public JwtResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenRepository.findByUuid(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(userEntity -> {
                    String accessToken = jwtUtil.generateToken(userEntity.getEmail());
                    JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
                    jwtResponseDTO.setRefreshToken(refreshTokenRequestDTO.getRefreshToken());
                    jwtResponseDTO.setAccessToken(accessToken);
                    return jwtResponseDTO;
                })
                .orElseThrow(() -> new TokenRefreshException("Refresh Token is not in DB..!!"));
    }
}
