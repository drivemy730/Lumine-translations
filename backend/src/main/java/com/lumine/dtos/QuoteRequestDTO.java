package com.lumine.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QuoteRequestDTO(
        @NotNull(message = "File ID is required")
        UUID fileId,

        @NotNull(message = "Client ID is required")
        Long clientId
) {}