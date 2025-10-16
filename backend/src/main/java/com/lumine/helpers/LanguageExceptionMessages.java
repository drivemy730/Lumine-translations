package com.lumine.helpers;

public enum LanguageExceptionMessages
{
    SOURCE_LANGUAGE_NOT_FOUND("the source language could not be found"),
    TARGET_LANGUAGE_NOT_FOUND("the target language could not be found"),
    FILE_TYPE_NOT_FOUND("the fyle type could not be found");


    private final String message;

    LanguageExceptionMessages(String message)
    {
        this.message = message;
    }

    public String message()
    {
        return message;
    }
}
