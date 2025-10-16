package com.lumine.services;
import com.lumine.dtos.JobRequestDTO;
import com.lumine.dtos.JobResponseDTO;
import com.lumine.helpers.JobExceptionMessages;
import com.lumine.helpers.JobStatus;
import com.lumine.models.*;
import com.lumine.repositories.JobRepository;
import com.lumine.repositories.QuoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


public class JobService
{
    private final JobRepository jobRepository;
    private final QuoteRepository quoteRepository;

    public JobService(JobRepository jobRepository, QuoteRepository quoteRepository) {
        this.jobRepository = jobRepository;
        this.quoteRepository = quoteRepository;
    }

    // Constructor injection...

    @Transactional
    public JobResponseDTO createJob(JobRequestDTO request)
    {
        Quote quote = validateQuote(request.quoteId());
        Job job = new Job(quote, quote.getClient());
        job.setStatus(JobStatus.IN_PROGRESS);

        return toDto(jobRepository.save(job));

    }

    @Transactional
    public JobResponseDTO updateJobStatus(JobRequestDTO request)
    {
        Job job = validateJob(request.jobId());
        validateStatusTransition(job.getStatus(), request.newStatus());

        job.setStatus(request.newStatus());
        if (request.newStatus() == JobStatus.COMPLETED)
        {
            job.setCompletedAt(OffsetDateTime.now());
        }
        return toDto(jobRepository.save(job));
    }

    @Transactional
    public JobResponseDTO updateFinalDocument(JobRequestDTO request)
    {
        Job job = validateJob(request.jobId());
        if (job.getStatus() != JobStatus.IN_PROGRESS) {
            throw new EntityNotFoundException(JobExceptionMessages.JOB_IN_PROGRESS_CAN_ONLY_BE_ADDED.message());
        }
        job.setFinalS3Key(request.s3Key());
        return toDto(jobRepository.save(job));
    }

    // Helper methods
    private Quote validateQuote(Long quoteId)
    {
        return quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(JobExceptionMessages.JOB_INVALID_ID.message()));
    }

    private Job validateJob(Integer jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException(JobExceptionMessages.JOB_INVALID_ID.message()));
    }

    private JobResponseDTO toDto(Job job) {
        return new JobResponseDTO(
                job.getQuote().getId(),
                job.getClient().getId(),
                job.getStatus(),
                job.getFinalS3Key(),
                job.getStartedAt(),
                job.getCompletedAt()
        );

    }

    private void validateStatusTransition(JobStatus current, JobStatus newStatus) {
        if (current == JobStatus.COMPLETED) {
            throw new EntityNotFoundException(JobExceptionMessages.JOB_COMPLETED_JOBS_CANNOT_BE_MODIFIED.message());
        }

        if (current == JobStatus.CANCELLED && newStatus != JobStatus.CANCELLED) {
            throw new EntityNotFoundException(JobExceptionMessages.JOB_CANCELED_JOBS_CANNOT_BE_MODIFIED.message());
        }
    }

}
