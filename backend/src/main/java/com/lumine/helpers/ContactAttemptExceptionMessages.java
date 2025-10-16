package com.lumine.helpers;

public enum ContactAttemptExceptionMessages
{
    CONTACT_ATTEMPT_LOGGED_SUCCESS("Contact attempt logged."),
    CONTACT_ATTEMPT_SIGNUP_RECORDED("Signup attempt recorded."),
    CONTACT_INVALID_METHOD("Invalid contact method."),
    CONTACT_DUPLICATE_ATTEMPT("Duplicate contact attempt.");

    private final String message;

    ContactAttemptExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
