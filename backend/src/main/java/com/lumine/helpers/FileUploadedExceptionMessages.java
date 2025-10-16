package com.lumine.helpers;

public enum FileUploadedExceptionMessages
{
    ILE_UPLOAD_SUCCESS("File uploaded successfully."),
    FILE_PROCESSED_SUCCESS("File processed successfully."),
    FILE_VIRUS_SCAN_PASSED("File passed virus scanning."),
    FILE_INVALID_TYPE("Unsupported file type."),
    FILE_TOO_LARGE("File size exceeds the 25MB limit."),
    FILE_UPLOAD_TIMEOUT("File upload failed due to timeout."),
    FILE_ZERO_WORDS("Uploaded file contains zero words."),
    FILE_NOT_FOUND("File not found with ID");

    private final String message;

    FileUploadedExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
