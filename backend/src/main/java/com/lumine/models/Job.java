package com.lumine.models;

import com.lumine.helpers.JobStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false, unique = true)
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobStatus status;

    @Column(name = "final_s3_key", length = 512)
    private String finalS3Key;

    @CreationTimestamp
    @Column(name = "started_at", nullable = false, updatable = false)
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    // ------------------- Constructors -------------------
    public Job() {}

    public Job(Quote quote, User client) {
        this.quote = quote;
        this.client = client;
    }

    // ------------------- Accessors -------------------
    public Integer getId() { return id; }
    public Quote getQuote() { return quote; }
    public User getClient() { return client; }
    public JobStatus getStatus() { return status; }
    public String getFinalS3Key() { return finalS3Key; }
    public OffsetDateTime getStartedAt() { return startedAt; }
    public OffsetDateTime getCompletedAt() { return completedAt; }

    public void setQuote(Quote quote) { this.quote = quote; }
    public void setClient(User client) { this.client = client; }
    public void setStatus(JobStatus status) { this.status = status; }
    public void setFinalS3Key(String finalS3Key) { this.finalS3Key = finalS3Key; }
    public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }

    // ------------------- Business Logic -------------------
    public boolean isCompleted() {
        return status == JobStatus.COMPLETED;
    }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job job)) return false;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", status=" + status +
                ", startedAt=" + startedAt +
                '}';
    }
}