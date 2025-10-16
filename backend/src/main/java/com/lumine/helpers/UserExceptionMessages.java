package com.lumine.helpers;

public enum UserExceptionMessages
{
    USER_REGISTERED_SUCCESS("User registered successfully."),
    USER_LOGIN_SUCCESS("Login successful."),
    USER_ROLE_UPGRADED_SUCCESS("User role upgraded successfully."),
    USER_NOT_FOUND("User not found with id ."),
    CLIENT_NOT_FOUND("The client was not found."),
    USER_EMAIL_DUPLICATE("Email already exists."),
    USER_INVALID_ROLE_ASSIGNMENT("Invalid role assignment.");

    private final String message;

    UserExceptionMessages(String message)
    {
        this.message = message;
    }

    public String message()
    {
        return message;
    }
}
