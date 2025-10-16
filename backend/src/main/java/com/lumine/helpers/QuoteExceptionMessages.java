package com.lumine.helpers;

public enum QuoteExceptionMessages
{
    QUOTE_GENERATED_SUCCESS("Quote generated successfully."),
    QUOTE_ACCEPTED_SUCCESS("Quote accepted and job created."),
    QUOTE_EXPIRED_CLEANED_SUCCESS("Expired quotes cleaned up."),
    QUOTE_INVALID_FILE("Cannot generate quote from invalid file."),
    QUOTE_UNSUPPORTED_LANGUAGE_PAIR("Unsupported language pair."),
    QUOTE_EXPIRED("Quote has expired and cannot be accepted."),
    QUOTE_NOT_FOUND("Quote not found"),
    ONLY_PENDING_QUOTES_ACCEPTED("Only pending quotes can be accepted"),
    EXPIRED_QUOTES_NOT_ACCEPTED("Expired quotes cannot be accepted");


    private final String message;

    QuoteExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
