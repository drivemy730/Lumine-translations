package com.lumine.dtos;

import com.lumine.helpers.LanguageFamily;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.helpers.LanguageName;

public record LanguageRequestDTO(
        LanguageIsoCode isoCode,
        LanguageName name,
        LanguageFamily family,
        boolean isRare
) {}
