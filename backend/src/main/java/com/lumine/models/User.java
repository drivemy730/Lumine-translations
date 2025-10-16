package com.lumine.models;

import com.lumine.helpers.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_role", columnList = "role")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Email
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", length = 60)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // ------------------- Relationships -------------------
    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<ClientUploadedFile> files = new HashSet<>();

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY
    )
    private Set<Quote> quotes = new HashSet<>();

    @OneToMany(
            mappedBy = "sender",
            fetch = FetchType.LAZY
    )
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(
            mappedBy = "receiver",
            fetch = FetchType.LAZY
    )
    private Set<Message> receivedMessages = new HashSet<>();

    // ------------------- Constructors -------------------
    protected User() {} // JPA requires protected constructor

    public User(String email, UserRole role) {
        this.email = email;
        this.role = role;
    }

    // ------------------- Accessors -------------------
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public Set<ClientUploadedFile> getFiles() { return Collections.unmodifiableSet(files); }
    public Set<Quote> getQuotes() { return Collections.unmodifiableSet(quotes); }
    public Set<Message> getSentMessages() { return Collections.unmodifiableSet(sentMessages); }
    public Set<Message> getReceivedMessages() { return Collections.unmodifiableSet(receivedMessages); }

    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(UserRole role) { this.role = role; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    // ------------------- Relationship Helpers -------------------
    public void addFile(ClientUploadedFile file) {
        files.add(file);
        file.setClient(this);
    }

    public void addQuote(Quote quote) {
        quotes.add(quote);
        quote.setClient(this);
    }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}