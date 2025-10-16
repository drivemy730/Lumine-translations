package com.lumine.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ClientUploadResponseDTO(
        @NotNull UUID fileId,

        @NotNull String previewUrl,

        @Positive int wordCount,

        @NotNull String fileName,

        @NotNull String fileExtension
) {
}
