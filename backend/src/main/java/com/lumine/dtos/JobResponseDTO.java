package com.lumine.dtos;

import com.lumine.helpers.JobStatus;

import java.time.OffsetDateTime;

public record JobResponseDTO
        (

        Long quoteId,
        Long userId,
        JobStatus status,
        String s3Key,
        OffsetDateTime startedAt,
        OffsetDateTime completedAt
        )
{ }
