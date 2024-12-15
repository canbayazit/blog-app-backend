package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.model.enums.PostStatus;
import com.example.blog_backend.util.validations.ValidEnum;
import lombok.Data;

@Data
public class PostStatusRequestDTO {
    @ValidEnum(enumClass = PostStatus.class, message = "Invalid post status")
    private String postStatus;

}
