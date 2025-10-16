package com.lumine.dtos;

import com.lumine.helpers.ClientFileTypeCategory;

import java.math.BigDecimal;

public record UploadedFileTypeResponseDTO
        (
                Integer id,
                ClientFileTypeCategory name,
                BigDecimal basePriceMultiplier,
                Integer difficultyLevel

)
{}
