package com.lumine.services;

import com.lumine.dtos.ConversationDTO;
import com.lumine.dtos.MessageRequestDTO;
import com.lumine.dtos.MessageResponseDTO;
import com.lumine.models.ClientUploadedFile;
import com.lumine.models.Message;
import com.lumine.models.User;
import com.lumine.repositories.ClientUploadedFileRepository;
import com.lumine.repositories.MessageRepository;
import com.lumine.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ClientUploadedFileRepository fileRepository;
    private static final int MESSAGE_PAGE_SIZE = 20;

    public MessageService(MessageRepository messageRepository,
                          UserRepository userRepository,
                          ClientUploadedFileRepository fileRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public MessageResponseDTO sendMessage(MessageRequestDTO request)

    {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        User receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        ClientUploadedFile file = request.fileId() != null ?
                fileRepository.findById(request.fileId()).orElse(null) : null;

        Message message = new Message(sender, receiver, request.content());
        message.setFile(file);

        return toDto(messageRepository.save(message));
    }

    @Transactional(readOnly = true)
    public ConversationDTO getConversation(Long user1Id, Long user2Id, int page) {
        List<Message> messages = messageRepository.findConversation(
                user1Id,
                user2Id,
                PageRequest.of(page, MESSAGE_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "sentAt"))
        );

        boolean hasMore = messages.size() == MESSAGE_PAGE_SIZE;

        return new ConversationDTO(
                messages.stream()
                        .map(this::toDto)
                        .toList(),
                hasMore
        );
    }


    private MessageResponseDTO toDto(Message message)
    {
        return new MessageResponseDTO(
                message.getId(),
                message.getSender().getId(),
                message.getReceiver().getId(),
                message.getFile() != null ? message.getFile().getId() : null,
                message.getContent(),
                message.getSentAt(),
                message.isRead()
        );
    }
}