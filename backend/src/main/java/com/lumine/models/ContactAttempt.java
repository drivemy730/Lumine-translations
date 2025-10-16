package com.lumine.models;

import com.lumine.helpers.ContactMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "contact_attempts")
public class ContactAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Simplified from attemptId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private ClientUploadedFile file;


    @Enumerated(EnumType.STRING)
    @Column(name = "contact_method", nullable = false)
    private ContactMethod contactMethod;  // NEW: Tracks WhatsApp/Email/Signup

    @CreationTimestamp
    @Column(name = "occurred_at", updatable = false)
    private OffsetDateTime occurredAt;  // Auto-generated

    // ------------------- Constructors -------------------
    public ContactAttempt() {}

    public ContactAttempt(User client, ClientUploadedFile file, ContactMethod contactMethod) {
        this.client = client;
        this.file = file;
        this.contactMethod = contactMethod;
    }

    // ------------------- Accessors -------------------
    public Integer getId() { return id; }
    public User getClient() { return client; }
    public ClientUploadedFile getFile() { return file; }
    public ContactMethod getContactMethod() { return contactMethod; }
    public OffsetDateTime getOccurredAt() { return occurredAt; }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactAttempt that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "ContactAttempt{" +
                "id=" + id +
                ", contactMethod=" + contactMethod +
                '}';
    }
}