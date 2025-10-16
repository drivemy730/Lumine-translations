package com.lumine.models;

import com.lumine.helpers.ContactMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "client_files")  // Changed from "client_documents"
public class ClientUploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_language_id", nullable = false)
    private Language sourceLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_language_id", nullable = false)
    private Language targetLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_type_id", nullable = false)  // Changed from document_type_id
    private UploadedFileType fileType;  // Renamed from documentType

    @Column(name = "s3_key", nullable = false, length = 512)
    private String s3Key;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_extension", nullable = false, length = 20)  // Changed from file_type
    private String fileExtension;  // Renamed from fileType

    @Column(name = "word_count", nullable = false)
    private int wordCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_method")
    private ContactMethod contactMethod;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @OneToMany(
            mappedBy = "file",  // Changed from "document"
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Quote> quotes = new HashSet<>();

    // ------------------- Constructors -------------------
    public ClientUploadedFile() {}

    public ClientUploadedFile(User client, Language sourceLanguage, Language targetLanguage,
                              UploadedFileType fileType, String s3Key, String fileName,
                              String fileExtension, int wordCount) {
        this.client = client;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.fileType = fileType;
        this.s3Key = s3Key;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.wordCount = wordCount;
    }

    // ------------------- Accessors -------------------
    // Getters
    public UUID getId() { return id; }
    public User getClient() { return client; }
    public Language getSourceLanguage() { return sourceLanguage; }
    public Language getTargetLanguage() { return targetLanguage; }
    public UploadedFileType getFileType() { return fileType; }
    public String getS3Key() { return s3Key; }
    public String getFileName() { return fileName; }
    public String getFileExtension() { return fileExtension; }
    public int getWordCount() { return wordCount; }
    public ContactMethod getContactMethod() { return contactMethod; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public Set<Quote> getQuotes() { return quotes; }

    // Setters
    public void setClient(User client) { this.client = client; }
    public void setSourceLanguage(Language sourceLanguage) { this.sourceLanguage = sourceLanguage; }
    public void setTargetLanguage(Language targetLanguage) { this.targetLanguage = targetLanguage; }
    public void setFileType(UploadedFileType fileType) { this.fileType = fileType; }
    public void setS3Key(String s3Key) { this.s3Key = s3Key; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
    public void setContactMethod(ContactMethod contactMethod) { this.contactMethod = contactMethod; }

    // ------------------- Relationship Helpers -------------------
    public void addQuote(Quote quote) {
        quotes.add(quote);
        quote.setFile(this);  // Changed from setDocument
    }

    public void removeQuote(Quote quote) {
        quotes.remove(quote);
        quote.setFile(null);  // Changed from setDocument
    }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientUploadedFile that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "ClientUploadedFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                '}';
    }
}