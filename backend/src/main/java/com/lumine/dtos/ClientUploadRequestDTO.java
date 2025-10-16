package com.lumine.dtos;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;


public record ClientUploadRequestDTO
        (

        @NotNull(message = "File must not be null")
        MultipartFile file,

        @NotNull(message = "Client ID is required")
        Long clientId,

        @NotNull(message = "Source language ID is required")
        Integer sourceLanguageId,

        @NotNull(message = "Target language ID is required")
        Integer targetLanguageId,

        @NotNull(message = "File type ID is required")
        Integer fileTypeId
)
{}
