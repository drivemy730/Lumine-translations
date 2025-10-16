package com.lumine.controllers;

import com.lumine.dtos.QuoteRequestDTO;
import com.lumine.dtos.QuoteResponseDTO;
import com.lumine.models.Quote;
import com.lumine.services.QuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    // 1. Create New Quote
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteResponseDTO createQuote(@RequestBody QuoteRequestDTO request) {
        return quoteService.createQuote(request);
    }

    // 2. Accept Quote
    @PostMapping("/{quoteId}/accept")
    public QuoteResponseDTO acceptQuote(@PathVariable Long quoteId) {
        return quoteService.acceptQuote(quoteId);
    }

    // 3. Get Quote Details
    @GetMapping("/{quoteId}")
    public QuoteResponseDTO getQuote(@PathVariable Long quoteId) {
        return quoteService.convertToResponseDTO(
                quoteService.getQuoteEntity(quoteId) // You'll need to add this method to your service
        );
    }

    // Simple DTO classes (if not using your existing ones)
    public static class BasicQuoteRequest {
        private UUID fileId;
        private Long clientId;

        // Getters and setters
        public UUID getFileId() { return fileId; }
        public void setFileId(UUID fileId) { this.fileId = fileId; }
        public Long getClientId() { return clientId; }
        public void setClientId(Long clientId) { this.clientId = clientId; }
    }

    public static class BasicQuoteResponse {
        private Long id;
        private BigDecimal price;
        private int estimatedHours;
        private String status;
        private LocalDateTime expiresAt;

        // Constructor from entity
        public BasicQuoteResponse(Quote quote) {
            this.id = quote.getId();
            this.price = quote.getPrice();
            this.estimatedHours = quote.getEstimatedHours();
            this.status = quote.getStatus().name();
            this.expiresAt = quote.getExpiresAt();
        }

        // Getters
        public Long getId() { return id; }
        public BigDecimal getPrice() { return price; }
        public int getEstimatedHours() { return estimatedHours; }
        public String getStatus() { return status; }
        public LocalDateTime getExpiresAt() { return expiresAt; }
    }
}
