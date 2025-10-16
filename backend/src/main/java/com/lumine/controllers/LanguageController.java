package com.lumine.controllers;

import com.lumine.dtos.LanguageRequestDTO;
import com.lumine.dtos.LanguageResponseDTO;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.services.LanguageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    // 1. Get all languages
    @GetMapping
    public List<LanguageResponseDTO> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    // 2. Get common languages only
    @GetMapping("/common")
    public List<LanguageResponseDTO> getCommonLanguages() {
        return languageService.getCommonLanguages();
    }

    // 3. Get specific language
    @GetMapping("/{id}")
    public LanguageResponseDTO getLanguage(@PathVariable Integer id) {
        return languageService.getLanguageById(id);
    }

    // 4. Create new language
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageResponseDTO createLanguage(@RequestBody LanguageRequestDTO request) {
        return languageService.createLanguage(request);
    }

    // 5. Update language
    @PutMapping("/{id}")
    public LanguageResponseDTO updateLanguage(
            @PathVariable Integer id,
            @RequestBody LanguageRequestDTO request) {
        return languageService.updateLanguage(id, request);
    }

    // 6. Check if language is rare (utility endpoint)
    @GetMapping("/{isoCode}/is-rare")
    public boolean isRareLanguage(@PathVariable LanguageIsoCode isoCode) {
        return languageService.isRareLanguage(isoCode);
    }
}
