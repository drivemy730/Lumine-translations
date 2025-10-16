package com.lumine.dtos;

import com.lumine.helpers.ClientFileTypeCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record UploadedFileTypeRequestDTO
        (
        ClientFileTypeCategory name,
        BigDecimal basePriceMultiplier,
        @Min(1) @Max(5) Integer difficultyLevel)
{ }
