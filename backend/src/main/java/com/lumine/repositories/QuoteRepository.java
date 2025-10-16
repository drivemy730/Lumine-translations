package com.lumine.repositories;


import com.lumine.helpers.QuoteStatus;
import com.lumine.models.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    // 1. Batch expire quotes (uses alias 'q' for consistency)
    @Modifying
    @Transactional
    @Query("UPDATE Quote q SET q.status = 'EXPIRED' WHERE q.expiresAt < :now AND q.status = 'PENDING'")
    int expireQuotes(@Param("now") LocalDateTime now);

    // 2. Paginated active quotes (alias enables JOIN-ready syntax)
    @Query("SELECT q FROM Quote q WHERE q.status = :status AND q.expiresAt > :now " +
            "ORDER BY q.expiresAt ASC")
    Page<QuoteRepository> findActiveQuotes(
            @Param("status") QuoteStatus status,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    // 3. Client-specific quotes (uses 'q' alias for joins)
    @Query("SELECT q FROM Quote q WHERE q.client.id = :clientId " +
            "AND q.createdAt BETWEEN :start AND :end")
    List<QuoteRepository> findClientQuotesInDateRange(
            @Param("clientId") Long clientId,
            @Param("start") LocalDateTime startDate,
            @Param("end") LocalDateTime endDate
    );

    // 4. Count quotes by status (alias keeps it concise)
    @Query("SELECT COUNT(q) FROM Quote q WHERE q.status = :status")
    long countByStatus(@Param("status") QuoteStatus status);
}