package com.lumine.repositories;

import com.lumine.models.ClientUploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientUploadedFileRepository extends JpaRepository<ClientUploadedFile, UUID> {


    //Modifying se usa para decirle a JPA que la anotacion Query no es solo de select si no
    // de operaciones CRUD en la base de datos

    //Transactional se usa como un try y catch mas avanzado
    //QUERY se usa para igualar nombres de parametros


    // 1. Auto-purge guest files (30-day cutoff)
    @Modifying
    @Transactional
    @Query("DELETE FROM ClientUploadedFile f WHERE f.uploadedAt < :cutoff AND f.client.role = 'GUEST'")
    int purgeGuestFilesOlderThan(@Param("cutoff") LocalDateTime cutoff);

    // 2. Get recent files for a client (dashboard)
    @Query("SELECT f FROM ClientUploadedFile f WHERE f.client.id = :clientId AND f.uploadedAt >= :since ORDER BY f.uploadedAt DESC")
    List<ClientUploadedFileRepository> findRecentFilesByClient
    (
            @Param("clientId") Long clientId,
            @Param("since") LocalDateTime since
    );

    // 3. Secure S3 key retrieval (for authorized access)
    @Query("SELECT f.s3Key FROM ClientUploadedFile f WHERE f.id = :id AND f.client.id = :clientId")
    Optional<String> findS3KeyByDocumentAndClient
    (
            @Param("id") UUID id,
            @Param("clientId") Long clientId
    );

    // 4. Count files without linked quotes (orphan detection)
    @Query("SELECT COUNT(f) FROM ClientUploadedFile f WHERE f.quotes IS NULL AND f.uploadedAt < :cutoff")
    long countOrphanedFiles(@Param("cutoff") LocalDateTime cutoff);
}