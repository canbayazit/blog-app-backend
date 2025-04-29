package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.UserDTO;
import com.example.blog_backend.service.UserService;
import com.example.blog_backend.util.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Boolean>> updateEmail(@Valid @RequestBody UserEmailUpdateRequestDTO updateEmailDTO) {
        Boolean isUpdated = userService.updateEmail(updateEmailDTO);
        if (isUpdated != null && isUpdated) {
            ApiResponse<Boolean> response = ApiResponse.success(true, "Email updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Boolean> response = ApiResponse.error(HttpStatus.NOT_FOUND, "Email update failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // RequestMapping anatasyonuna sahip herhangi bir class içindeki metotta principal nesnesini geçebiliriz,
    // spring security otomatik olarak Principal nesnesini inject eder. Ama biz daha model bir yapıyla yaklaşacaz
    // ve UserContextServiceImpl kullanacaz
    //public ResponseEntity<?> updatePassword(@Valid @RequestBody UserPasswordUpdateRequestDTO passwordUpdateRequestDTO,
    //                                            Principal connectedUser)
    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse<Boolean>> updatePassword(@Valid @RequestBody UserPasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        userService.updatePassword(passwordUpdateRequestDTO);
        ApiResponse<Boolean> response = ApiResponse.success(true, "Password updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("@authService.isSelf(#userId)")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteUserSelf(@PathVariable UUID userId) {
        Boolean isDeleted = userService.deleteByUUID(userId);
        if (isDeleted) {
            ApiResponse<Boolean> response = ApiResponse.success(true, "User deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Boolean> response = ApiResponse.error(HttpStatus.NOT_FOUND, "User deletion failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
