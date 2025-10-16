package com.lumine.models;

import com.lumine.helpers.LanguageFamily;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.helpers.LanguageName;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "iso_code", unique = true, nullable = false, length = 10)
    private LanguageIsoCode isoCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private LanguageName name;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private LanguageFamily family;

    @Column(name = "is_rare", nullable = false)
    private boolean isRare = false;

    // Relationships
    @OneToMany(mappedBy = "sourceLanguage")
    private Set<ClientUploadedFile> sourceFiles = new HashSet<>();  // Renamed from documentsAsSource

    @OneToMany(mappedBy = "targetLanguage")
    private Set<ClientUploadedFile> targetFiles = new HashSet<>();  // Renamed from documentsAsTarget

    // ------------------- Constructors -------------------
    public Language() {}

    public Language(LanguageIsoCode isoCode, LanguageName name) {
        this.isoCode = isoCode;
        this.name = name;
    }

    // ------------------- Accessors -------------------
    public Integer getId() { return id; }
    public LanguageIsoCode getIsoCode() { return isoCode; }
    public LanguageName getName() { return name; }
    public LanguageFamily getFamily() { return family; }
    public boolean isRare() { return isRare; }
    public Set<ClientUploadedFile> getSourceFiles() { return Collections.unmodifiableSet(sourceFiles); }
    public Set<ClientUploadedFile> getTargetFiles() { return Collections.unmodifiableSet(targetFiles); }

    public void setIsoCode(LanguageIsoCode isoCode) { this.isoCode = isoCode; }
    public void setName(LanguageName name) { this.name = name; }
    public void setFamily(LanguageFamily family) { this.family = family; }
    public void setRare(boolean isRare) { this.isRare = isRare; }

    // ------------------- Business Methods -------------------
    public boolean isCommonLanguage() {
        return !isRare;
    }

    // ------------------- equals() / hashCode() -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;
        return isoCode == language.isoCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isoCode);
    }

    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "Language{" +
                "isoCode=" + isoCode +
                ", name=" + name +
                '}';
    }
}