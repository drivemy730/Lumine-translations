package com.lumine.controllers;/*
package controlers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lumine.services.ContactAttemptService;

@RestController
@RequestMapping("/api/v1/contact-analytics")

@Validated
public class ContactAnalyticsController {

    private final ContactAttemptService contactAttemptService;
    private final SecurityUtils securityUtils;

    public ContactAnalyticsController(ContactAttemptService contactAttemptService, SecurityUtils securityUtils) {
        this.contactAttemptService = contactAttemptService;
        this.securityUtils = securityUtils;
    }

    // 1. Core Tracking Endpoint
    @PostMapping("/attempts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactAttempt logContactAttempt(
            @RequestBody @Valid ContactAttemptRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        securityUtils.validateClientAccess(jwt, request.clientId());

        return contactAttemptService.logAttempt(
                request.clientId(),
                request.fileId(),
                request.method()
        );
    }

    // 2. Client Preference Analysis
    @GetMapping("/clients/{clientId}/preferred-method")
    public ContactMethodResponse getPreferredContactMethod(
            @PathVariable Long clientId,
            @AuthenticationPrincipal Jwt jwt) {

        securityUtils.validateClientAccess(jwt, clientId);

        return new ContactMethodResponse(
                contactAttemptService.getPreferredContactMethod(clientId)
        );
    }

    // 3. Dashboard Statistics (Admin-only)
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getContactAnalytics(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime since) {

        if (since == null) {
            return contactAttemptService.getContactAnalytics();
        }

        // Custom date range implementation would go here
        throw new UnsupportedOperationException("Custom date ranges coming in v2");
    }

    // 4. Client Activity Check
    @GetMapping("/clients/{clientId}/activity-status")
    public ActivityStatusResponse checkClientActivity(
            @PathVariable Long clientId,
            @AuthenticationPrincipal Jwt jwt) {

        securityUtils.validateClientAccess(jwt, clientId);

        return new ActivityStatusResponse(
                contactAttemptService.isActiveClient(clientId)
        );
    }

    // DTO Records
    public record ContactAttemptRequest(
            @NotNull Long clientId,
            @NotNull UUID fileId,
            @NotNull ContactMethod method) {}

    public record ContactMethodResponse(ContactMethod preferredMethod) {}

    public record ActivityStatusResponse(boolean isActive) {}
}

*/


import com.lumine.dtos.ContactAttemptRequestDTO;
import com.lumine.dtos.ContactAttemptResponseDTO;
import com.lumine.helpers.ContactMethod;
import com.lumine.services.ContactAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact-attempts")
public class ContactAttemptController {
    private final ContactAttemptService contactAttemptService;

    public ContactAttemptController(ContactAttemptService contactAttemptService) {
        this.contactAttemptService = contactAttemptService;
    }

    // 1. Record New Contact Attempt
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void recordAttempt(@RequestBody ContactAttemptRequestDTO request) {
        contactAttemptService.recordContactAttempt(request);
    }

    // 2. Get Contact Analytics
    @GetMapping("/analytics")
    public ContactAttemptResponseDTO getAnalytics() {
        return contactAttemptService.analyzeContactAttempts();
    }

    // Simple DTOs (can be moved to separate files later)
    public static class BasicAttemptRequest {
        private Long clientId;
        private UUID fileId;
        private ContactMethod contactMethod;

        // Getters and setters
        public Long getClientId() { return clientId; }
        public void setClientId(Long clientId) { this.clientId = clientId; }
        public UUID getFileId() { return fileId; }
        public void setFileId(UUID fileId) { this.fileId = fileId; }
        public ContactMethod getContactMethod() { return contactMethod; }
        public void setContactMethod(ContactMethod contactMethod) {
            this.contactMethod = contactMethod;
        }
    }
}