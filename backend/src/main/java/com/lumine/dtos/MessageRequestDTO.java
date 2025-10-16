package com.lumine.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageRequestDTO
        (
                Long senderId,
                Long receiverId,
                UUID fileId,  // Optional
                String content
        )
{}
