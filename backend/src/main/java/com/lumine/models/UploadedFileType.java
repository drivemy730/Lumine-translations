package com.lumine.models;

import  com.lumine.helpers.ClientFileTypeCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "document_types")
public class UploadedFileType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private ClientFileTypeCategory name;

    @Column(name = "base_price_multiplier", nullable = false, precision = 3, scale = 2)
    private BigDecimal basePriceMultiplier = BigDecimal.ONE;

    @Column(nullable = false)
    @Min(1) @Max(5)  // Validation for 1-5 scale
    private Integer difficultyLevel = 1;

    @OneToMany(mappedBy = "fileType")  // Fixed mapping to match ClientUploadedFile
    private Set<ClientUploadedFile> files = new HashSet<>();  // Renamed from clientUploadedFiles

    // ------------------- Constructors -------------------
    public UploadedFileType() {}

    public UploadedFileType(ClientFileTypeCategory name) {
        this.name = name;
    }

    // ------------------- Accessors -------------------
    public Integer getId() { return id; }
    public ClientFileTypeCategory getName() { return name; }
    public BigDecimal getBasePriceMultiplier() { return basePriceMultiplier; }
    public Integer getDifficultyLevel() { return difficultyLevel; }
    public Set<ClientUploadedFile> getFiles() { return Collections.unmodifiableSet(files); }

    public void setName(ClientFileTypeCategory name) { this.name = name; }
    public void setBasePriceMultiplier(BigDecimal multiplier) {
        this.basePriceMultiplier = multiplier != null ? multiplier : BigDecimal.ONE;
    }
    public void setDifficultyLevel(Integer level) {
        this.difficultyLevel = (level != null) ? Math.min(5, Math.max(1, level)) : 1;
    }


    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadedFileType that)) return false;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "UploadedFileType{" +
                "name=" + name +
                ", difficulty=" + difficultyLevel +
                '}';
    }
}