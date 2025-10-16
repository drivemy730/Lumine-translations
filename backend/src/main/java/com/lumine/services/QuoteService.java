package com.lumine.services;

import com.lumine.dtos.QuoteRequestDTO;
import com.lumine.dtos.QuoteResponseDTO;
import com.lumine.helpers.QuoteExceptionMessages;
import com.lumine.models.ClientUploadedFile;
import com.lumine.models.Quote;
import com.lumine.helpers.QuoteStatus;
import com.lumine.models.User;
import com.lumine.repositories.QuoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientUploadedFileService fileService;
    private final UserService userService;
    private float estimatedTime = 350;

    private static final int DEFAULT_QUOTE_EXPIRY_DAYS = 7;
    private static final BigDecimal PRICE_PER_WORD = new BigDecimal("200"); // Flat rate: 200 COP/word

    public QuoteService(QuoteRepository quoteRepository,
                        ClientUploadedFileService fileService,
                        UserService userService) {
        this.quoteRepository = quoteRepository;
        this.fileService = fileService;
        this.userService = userService;
    }


    public Quote getQuoteEntity(Long quoteId) {
        return quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(QuoteExceptionMessages.QUOTE_NOT_FOUND.message()));
    }



    // CREAR COTIZACION
    @Transactional
    public QuoteResponseDTO createQuote(QuoteRequestDTO requestDTO)
    {
        // Validate referenced entities
        ClientUploadedFile file = fileService.getFileEntity(requestDTO.fileId());
        User client = userService.getUserEntity(requestDTO.clientId());

        // Calculate flat-rate price
        BigDecimal totalPrice = calculatePrice(file.getWordCount());

        // Build and persist quote
        Quote quote = new Quote();
        quote.setFile(file);
        quote.setClient(client);
        quote.setPrice(totalPrice);
        quote.setEstimatedHours(calculateEstimatedHours(file));
        quote.setStatus(QuoteStatus.PENDING);
        quote.setExpiresAt(LocalDateTime.now().plusDays(DEFAULT_QUOTE_EXPIRY_DAYS));

        quoteRepository.save(quote);

        return convertToResponseDTO(quote);
    }

    @Transactional
    public QuoteResponseDTO acceptQuote(Long quoteId)
    {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(QuoteExceptionMessages.QUOTE_NOT_FOUND.message()));

        validateQuoteAcceptance(quote);

        quote.setStatus(QuoteStatus.ACCEPTED);
        quoteRepository.save(quote);

        initiateTranslationProcess(quote);

        return convertToResponseDTO(quote);
    }



    //-------------------INTERNAL HELPERS-------------------//


    private BigDecimal calculatePrice(int wordCount) {
        return PRICE_PER_WORD.multiply(BigDecimal.valueOf(wordCount));
    }


    // 500 PALABRAS POR MINUTO?
    private int calculateEstimatedHours(ClientUploadedFile file) {
        return (int) Math.ceil(file.getWordCount() / estimatedTime); // 500 words/hour baseline
    }



    private void validateQuoteAcceptance(Quote quote)
    {
        if (quote.getStatus() != QuoteStatus.PENDING) {
            throw new EntityNotFoundException(QuoteExceptionMessages.ONLY_PENDING_QUOTES_ACCEPTED.message());
        }

        if (LocalDateTime.now().isAfter(quote.getExpiresAt())) {
            throw new EntityNotFoundException(QuoteExceptionMessages.EXPIRED_QUOTES_NOT_ACCEPTED.message());
        }
    }


    public QuoteResponseDTO convertToResponseDTO(Quote quote) {
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getFile().getId(),
                quote.getClient().getId(),
                quote.getPrice(),
                quote.getEstimatedHours(),
                quote.getStatus(),
                quote.getExpiresAt()
        );
    }


    private void initiateTranslationProcess(Quote quote) {
        // Implementation would:
        // 1. Create translation job
        // 2. Notify translators
        // 3. Update order status
        System.out.println("Translation process started for quote ID: " + quote.getId());
    }


}