package com.lumine.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponseDTO
        (
        Long id,
        Long senderId,
        Long receiverId,
        UUID fileId,
        String content,
        LocalDateTime sentAt,
        boolean isRead

        )
{ }
