package com.lumine.dtos;

import com.lumine.helpers.ContactMethod;

import java.util.Map;

public record ContactAttemptResponseDTO(
        String mostPopularMethod,
        Map<ContactMethod, Long> methodDistribution,
        double conversionRate,
        Map<String, Long> hourlyTrends
) {
}
