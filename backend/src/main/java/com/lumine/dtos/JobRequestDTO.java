package com.lumine.dtos;

import com.lumine.helpers.JobStatus;
import com.lumine.helpers.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobRequestDTO(

        Integer jobId,
        Long quoteId,
        JobStatus newStatus,// For status changes
        String s3Key// For document updates
        ) {}
