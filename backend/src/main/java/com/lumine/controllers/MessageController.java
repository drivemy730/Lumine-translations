package com.lumine.controllers;

import com.lumine.dtos.MessageRequestDTO;
import com.lumine.dtos.MessageResponseDTO;
import com.lumine.models.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.lumine.services.MessageService;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController
{
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 1. Send Message
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO sendMessage(@RequestBody MessageRequestDTO request) {
        return messageService.sendMessage(request);
    }


    // 2. Get Message
    /*
    @GetMapping("/{id}")
    public MessageResponse getMessage(@PathVariable Message id) {
        return new MessageResponse(messageService.getConversation(id));
    }
*/

    // Simple DTO Classes
    public static class MessageRequest {
        private Long senderId;
        private Long receiverId;
        private String content;

        // Getters and Setters
        public Long getSenderId() { return senderId; }
        public void setSenderId(Long senderId) { this.senderId = senderId; }
        public Long getReceiverId() { return receiverId; }
        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    public static class MessageResponse {
        private Long id;
        private Long senderId;
        private Long receiverId;
        private String content;
        private LocalDateTime sentAt;

        public MessageResponse(Message message) {
            this.id = message.getId();
            this.senderId = message.getSender().getId();
            this.receiverId = message.getReceiver().getId();
            this.content = message.getContent();
            this.sentAt = message.getSentAt();
        }

        // Getters
        public Long getId() { return id; }
        public Long getSenderId() { return senderId; }
        public Long getReceiverId() { return receiverId; }
        public String getContent() { return content; }
        public LocalDateTime getSentAt() { return sentAt; }
    }
}