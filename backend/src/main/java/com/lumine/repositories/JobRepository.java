package com.lumine.repositories;

import com.lumine.helpers.JobStatus;
import com.lumine.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable; // For pagination
import org.springframework.data.domain.Page;    // For return type

import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    // 1. Update job status (uses alias 'j' for Job)
    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.status = :status, j.startedAt = :startedAt WHERE j.id = :id")
    int updateJobStatus(
            @Param("id") Integer id,
            @Param("status") JobStatus status,
            @Param("completedAt") LocalDateTime completedAt
    );

    /*
    // 2. Find jobs by status with pagination
    @Query("SELECT j FROM Job j WHERE j.status = :status ORDER BY j.quote.startedAt DESC")
    Page<JobRepository> findByStatus(
            @Param("status") JobStatus status,
            Pageable pageable
    );
    */

    /*
    // 3. Count overdue jobs (status=IN_PROGRESS past deadline)
    @Query("SELECT COUNT(j) FROM Job j WHERE j.status = 'IN_PROGRESS' AND j.quote.expiresAt < :now")
    long countOverdueJobs(@Param("now") LocalDateTime now);
    */


    // 4. Client's job history
    @Query("SELECT j FROM Job j WHERE j.client.id = :clientId ORDER BY j.startedAt DESC")
    List<JobRepository> findClientJobHistory(@Param("clientId") Long clientId);
}