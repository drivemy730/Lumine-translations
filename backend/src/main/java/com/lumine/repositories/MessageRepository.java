package com.lumine.repositories;

import com.lumine.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {



    // Consulta personalizada para conversación entre dos usuarios (bidireccional)
    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender.id = :user1Id AND m.receiver.id = :user2Id)
           OR (m.sender.id = :user2Id AND m.receiver.id = :user1Id)
        ORDER BY m.sentAt DESC
    """)
    List<Message> findConversation(Long user1Id, Long user2Id, Pageable pageable);
}
