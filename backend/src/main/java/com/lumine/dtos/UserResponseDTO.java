package com.lumine.dtos;

import com.lumine.helpers.UserRole;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String email,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime lastLogin
) {}

/**
 * UserRequestDTO and UserResponseDTO define the basic user input/output structures.
 *
 * Current Simplifications / Postponed Enhancements:
 * 1. No custom validation annotations beyond standard Spring ones (e.g., no @StrongPassword).
 * 2. Validation messages are hardcoded in English — no i18n support yet.
 * 3. No additional DTO fields for security or masking (e.g., password hash excluded).
 * 4. No detailed Swagger/OpenAPI @Schema annotations for all fields (only minimal).
 * 5. Role validation is handled in service layer, not via annotations.
 * 6. No dedicated ErrorDTO for user-related errors yet.
 *
 * These can be added later as the project evolves and needs grow.
 */