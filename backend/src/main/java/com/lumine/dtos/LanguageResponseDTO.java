package com.lumine.dtos;

import com.lumine.helpers.LanguageFamily;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.helpers.LanguageName;

public record LanguageResponseDTO(
        Integer id,
        LanguageIsoCode isoCode,
        LanguageName name,
        LanguageFamily family,
        boolean isRare,
        boolean isCommon
) {}
