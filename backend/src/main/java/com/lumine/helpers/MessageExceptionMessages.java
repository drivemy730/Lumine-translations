package com.lumine.helpers;

public enum MessageExceptionMessages
{
    MESSAGE_SENT_SUCCESS("Message sent successfully."),
    MESSAGE_READ_STATUS_UPDATED("Message marked as read."),
    MESSAGE_SENDER_NOT_FOUND("Sender not found."),
    MESSAGE_RECEIVER_NOT_FOUND("Receiver not found."),
    MESSAGE_NOT_FOUND("the message was not found"),
    MESSAGE_TOO_LONG("Message exceeds the maximum allowed length (500 characters).");

    private final String message;

    MessageExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
