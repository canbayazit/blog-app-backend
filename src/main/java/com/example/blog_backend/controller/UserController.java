package com.example.blog_backend.controller;

import com.example.blog_backend.model.requestDTO.UserEmailRequestDTO;
import com.example.blog_backend.model.requestDTO.UserPasswordUpdateRequestDTO;
import com.example.blog_backend.model.requestDTO.UserProfileRequestDTO;
import com.example.blog_backend.model.responseDTO.UserProfileResponseDTO;
import com.example.blog_backend.model.responseDTO.UserResponseDTO;
import com.example.blog_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and @userService.isSelf(#uuid)")
    @PutMapping("/update/profile/{uuid}")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(@PathVariable UUID uuid, @Valid @RequestBody UserProfileRequestDTO requestDTO) {
        UserProfileResponseDTO updatedProfile = userService.updateProfile(uuid, requestDTO);
        if (updatedProfile != null) {
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') and @userService.isSelf(#id)")
    @PutMapping("/update/email/{id}")
    public ResponseEntity<Boolean> updateEmail(@PathVariable UUID id, @Valid @RequestBody UserEmailRequestDTO updateEmailDTO) {
        Boolean isUpdated = userService.updateEmail(id, updateEmailDTO);
        if (isUpdated != null && isUpdated) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') and @userService.isSelf(#uuid)")
    @PutMapping("/update/password/{uuid}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable UUID uuid, @Valid @RequestBody UserPasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        Boolean isUpdated = userService.updatePassword(uuid, passwordUpdateRequestDTO);
        if (isUpdated != null && isUpdated) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#uuid)")
    @GetMapping("/get/{uuid}")
    public ResponseEntity<UserResponseDTO> getByUUID(@PathVariable UUID uuid) {
        UserResponseDTO dto = userService.getByUUID(uuid);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#uuid)")
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid) {
        Boolean isDeleted = userService.deleteByUUID(uuid);
        if (isDeleted) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }


}
