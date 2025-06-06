package com.example.blog_backend.core.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class BaseDTO {

    private Long id;
    private UUID uuid;
    private Date creationDate;
    private Date updatedDate;

}
