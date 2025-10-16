package com.lumine.models;

import com.lumine.helpers.QuoteStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)  //
    private ClientUploadedFile file;  //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)  //
    private User client;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int estimatedHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuoteStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    // ------------------- Constructors -------------------
    public Quote() {}

    public Quote(ClientUploadedFile file, User client, BigDecimal price,
                 int estimatedHours, QuoteStatus status, LocalDateTime expiresAt) {
        this.file = file;
        this.client = client;
        this.price = price;
        this.estimatedHours = estimatedHours;
        this.status = status;
        this.expiresAt = expiresAt;
    }

    // ------------------- Getters and Setters -------------------
    public Long getId() { return id; }
    public ClientUploadedFile getFile() { return file; }  // Renamed
    public User getClient() { return client; }
    public BigDecimal getPrice() { return price; }
    public int getEstimatedHours() { return estimatedHours; }
    public QuoteStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void setFile(ClientUploadedFile file) { this.file = file; }  // Renamed
    public void setClient(User client) { this.client = client; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setEstimatedHours(int estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setStatus(QuoteStatus status) { this.status = status; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote quote)) return false;
        return Objects.equals(id, quote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}