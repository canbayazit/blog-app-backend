package com.example.blog_backend.service;

import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.requestDTO.UserEmailRequestDTO;
import com.example.blog_backend.model.requestDTO.UserPasswordUpdateRequestDTO;
import com.example.blog_backend.model.requestDTO.UserProfileRequestDTO;
import com.example.blog_backend.model.responseDTO.UserProfileResponseDTO;
import com.example.blog_backend.model.responseDTO.UserResponseDTO;
import com.example.blog_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAll() {
        return userMapper.entityListToDTOList(userRepository.findAll());
    }

    @Transactional
    public UserProfileResponseDTO updateProfile(UUID uuid, UserProfileRequestDTO requestDTO) {
        UserEntity entity = userRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            entity = userMapper.requestDtoToExistEntity(entity, requestDTO);
            userRepository.save(entity);
            return userMapper.entityToUpdateResponseDTO(entity);
        } else {
            return null;
        }
    }

    @Transactional
    public Boolean updateEmail(UUID id, UserEmailRequestDTO userEmailRequestDTO) {
        UserEntity entity = userRepository.findByUuid(id).orElse(null);
        if (entity != null) {
            entity = userMapper.requestDtoToExistEntity(entity, userEmailRequestDTO);
            userRepository.save(entity);
            return true;
        } else {
            return null;
        }
    }

    @Transactional
    public Boolean updatePassword(UUID id, UserPasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        UserEntity entity = userRepository.findByUuid(id).orElse(null);
        if (entity != null) {
            // Mevcut şifreyi doğrulama
            if (!passwordEncoder.matches(passwordUpdateRequestDTO.getCurrentPassword(), entity.getPassword())) {
                throw new IllegalArgumentException("Mevcut şifre hatalı");
            } else {
                System.out.printf(passwordEncoder.encode(passwordUpdateRequestDTO.getNewPassword()));
                entity.setPassword(passwordEncoder.encode(passwordUpdateRequestDTO.getNewPassword()));
                userRepository.save(entity);
                return true;
            }
        } else {
            return null;
        }
    }

    public UserResponseDTO getByUUID(UUID uuid) {
        UserEntity entity = userRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            return userMapper.entityToDTO(entity);
        } else {
            return null;
        }
    }

    @Transactional
    public Boolean deleteByUUID(UUID uuid) {
        UserEntity entity = userRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            userRepository.delete(entity);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public boolean isSelf(UUID id) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail).orElse(null);
        return currentUser != null && currentUser.getUuid().equals(id);
    }

}
