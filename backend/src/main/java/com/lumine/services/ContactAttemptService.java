package com.lumine.services;

import com.lumine.dtos.ContactAttemptRequestDTO;
import com.lumine.dtos.ContactAttemptResponseDTO;
import com.lumine.helpers.ContactMethod;
import com.lumine.helpers.FileUploadedExceptionMessages;
import com.lumine.helpers.UserExceptionMessages;
import com.lumine.models.ClientUploadedFile;
import com.lumine.models.ContactAttempt;
import com.lumine.models.User;
import com.lumine.repositories.ClientUploadedFileRepository;
import com.lumine.repositories.ContactAttemptRepository;
import com.lumine.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class ContactAttemptService
{

    private final ContactAttemptRepository contactAttemptRepository;
    private final UserRepository userRepository;
    private final ClientUploadedFileRepository fileRepository;

    public ContactAttemptService(ContactAttemptRepository contactAttemptRepository, UserRepository userRepository, ClientUploadedFileRepository fileRepository)
    {
        this.contactAttemptRepository = contactAttemptRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    // Constructor injection...

    public void recordContactAttempt(ContactAttemptRequestDTO request)
    {
        User client = userRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException(UserExceptionMessages.USER_NOT_FOUND.message()));

        ClientUploadedFile file = fileRepository.findById(request.fileId())
                .orElseThrow(() -> new EntityNotFoundException(FileUploadedExceptionMessages.FILE_NOT_FOUND.message())) ;

        contactAttemptRepository.save(
                new ContactAttempt(client, file, request.contactMethod())
        );
    }

    @Transactional
    public ContactAttemptResponseDTO analyzeContactAttempts() {
        List<ContactAttempt> attempts = contactAttemptRepository.findAll();  // No time filtering

        Map<ContactMethod, Long> distribution = attempts.stream()
                .collect(Collectors.groupingBy(
                        ContactAttempt::getContactMethod,
                        Collectors.counting()
                ));

        return new ContactAttemptResponseDTO(
                calculatePopularMethod(distribution),
                distribution,
                calculateConversionRate(attempts, distribution),
                calculateHourlyTrends(attempts)
        );
    }

    // Private helper methods...
    private String calculatePopularMethod(Map<ContactMethod, Long> distribution) {
        return distribution.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().name())
                .orElse("NO_DATA");
    }

    private double calculateConversionRate(List<ContactAttempt> attempts,
                                           Map<ContactMethod, Long> distribution) {
        return attempts.isEmpty() ? 0 :
                distribution.getOrDefault(ContactMethod.SIGNUP, 0L) / (double) attempts.size();
    }

    private Map<String, Long> calculateHourlyTrends(List<ContactAttempt> attempts) {
        return attempts.stream()
                .collect(Collectors.groupingBy(
                        a -> String.format("%02d:00", a.getOccurredAt().getHour()),
                        Collectors.counting()
                ));
    }
}