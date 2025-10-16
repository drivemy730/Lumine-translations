package com.lumine.repositories;

import com.lumine.models.ContactAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ContactAttemptRepository extends JpaRepository<ContactAttempt, Integer> {

    // 1. Count attempts by method (WhatsApp/Email/Chat)
    @Query("SELECT ca.contactMethod as method, COUNT(ca) as total " +
            "FROM ContactAttempt ca " +
            "WHERE ca.occurredAt >= :startDate " +
            "GROUP BY ca.contactMethod")
    List<Map<String, Object>> countAttemptsByMethodSince(
            @Param("startDate") LocalDateTime startDate
    );

    // 2. Client-specific attempt tracking
    @Query("SELECT COUNT(ca) FROM ContactAttempt ca " +
            "WHERE ca.client.id = :clientId " +
            "AND ca.occurredAt BETWEEN :start AND :end")
    long countClientAttemptsInRange(
            @Param("clientId") Long clientId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // 3. Popular document types for contact (future analytics)
    @Query("SELECT ca.file.fileType.name as type, COUNT(ca) as total " +
            "FROM ContactAttempt ca " +
            "GROUP BY ca.file.fileType.name")
    List<Map<String, Object>> countAttemptsByFileType();




}