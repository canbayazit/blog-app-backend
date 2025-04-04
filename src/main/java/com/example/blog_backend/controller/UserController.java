package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.ApiResponseDTO;
import com.example.blog_backend.model.responseDTO.UserDTO;
import com.example.blog_backend.service.UserService;
import com.example.blog_backend.util.response.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("api/user")
public class UserController extends AbstractBaseCrudControllerImpl<
        UserEntity,
        UserDTO,
        UserRequestDTO,
        UserService
        > {
    private final UserService userService;
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PutMapping("/update/email")
    public ResponseEntity<ApiResponseDTO<Boolean>> updateEmail(@Valid @RequestBody UserEmailUpdateRequestDTO updateEmailDTO) {
        Boolean isUpdated = userService.updateEmail(updateEmailDTO);
        if (isUpdated != null && isUpdated) {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.success(true, "Email updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.error("Email update failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // RequestMapping anatasyonuna sahip herhangi bir class içindeki metotta principal nesnesini geçebiliriz,
    // spring security otomatik olarak Principal nesnesini inject eder. Ama biz daha model bir yapıyla yaklaşacaz
    // ve UserContextServiceImpl kullanacaz
    //public ResponseEntity<?> updatePassword(@Valid @RequestBody UserPasswordUpdateRequestDTO passwordUpdateRequestDTO,
    //                                            Principal connectedUser)
    @PutMapping("/update/password")
    public ResponseEntity<ApiResponseDTO<Boolean>> updatePassword(@Valid @RequestBody UserPasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        userService.updatePassword(passwordUpdateRequestDTO);
        ApiResponseDTO<Boolean> response = ApiResponseUtil.success(true, "Password updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("@authService.isSelf(#userId)")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteUserSelf(@PathVariable UUID userId) {
        Boolean isDeleted = userService.deleteByUUID(userId);
        if (isDeleted) {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.success(true, "User deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.error("User deletion failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
