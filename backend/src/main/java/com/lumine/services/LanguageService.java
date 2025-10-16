package com.lumine.services;

import com.lumine.dtos.LanguageRequestDTO;
import com.lumine.dtos.LanguageResponseDTO;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.models.Language;
import com.lumine.repositories.LanguageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Transactional(readOnly = true)
    public Language getEntityById(Integer id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Language not found with ID: " + id));
    }


    @Transactional(readOnly = true)
    public List<LanguageResponseDTO> getAllLanguages() {
        return languageRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public LanguageResponseDTO getLanguageById(Integer id) {
        return languageRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Language not found"));
    }

    @Transactional(readOnly = true)
    public List<LanguageResponseDTO> getCommonLanguages() {
        return languageRepository.findByIsRareFalse().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public LanguageResponseDTO createLanguage(LanguageRequestDTO request) {
        Language language = new Language(request.isoCode(), request.name());
        language.setFamily(request.family());
        language.setRare(request.isRare());

        return toDto(languageRepository.save(language));
    }

    @Transactional
    public LanguageResponseDTO updateLanguage(Integer id, LanguageRequestDTO request) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Language not found"));

        language.setName(request.name());
        language.setFamily(request.family());
        language.setRare(request.isRare());

        return toDto(languageRepository.save(language));
    }

    @Transactional(readOnly = true)
    public boolean isRareLanguage(LanguageIsoCode isoCode) {
        return languageRepository.findByIsoCode(isoCode)
                .map(Language::isRare)
                .orElseThrow(() -> new IllegalArgumentException("Language not found"));
    }

    private LanguageResponseDTO toDto(Language language) {
        return new LanguageResponseDTO(
                language.getId(),
                language.getIsoCode(),
                language.getName(),
                language.getFamily(),
                language.isRare(),
                language.isCommonLanguage()
        );
    }
}
