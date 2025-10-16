package com.lumine.dtos;

import com.lumine.helpers.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteResponseDTO(
        Long quoteId,
        UUID fileId,
        Long clientId,
        BigDecimal price,
        int estimatedHours,
        QuoteStatus status,
        LocalDateTime expiresAt
) {}