package com.lumine.dtos;

import java.util.List;

public record ConversationDTO(
        List<MessageResponseDTO> messages,
        boolean hasMore
) {
}
