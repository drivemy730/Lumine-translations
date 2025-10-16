package com.lumine.helpers;

public enum JobExceptionMessages
{
    JOB_COMPLETED_SUCCESS("Job marked as completed."),
    JOB_FILE_STORED_SUCCESS("Translated file stored successfully."),
    JOB_CLIENT_NOTIFIED_SUCCESS("Client has been notified."),
    JOB_CANCELLATION_AFTER_DEADLINE("Cannot cancel job after deadline."),
    JOB_INVALID_FINAL_S3_KEY("Invalid S3 key for final file."),
    JOB_TRANSLATOR_CONFLICT("Job already assigned to a different translator."),
    JOB_IN_PROGRESS_CAN_ONLY_BE_ADDED("Document can only be added to IN_PROGRESS jobs"),
    JOB_INVALID_ID("Invalid quote ID"),
    JOB_COMPLETED_JOBS_CANNOT_BE_MODIFIED("Completed jobs cannot be modified"),
    JOB_CANCELED_JOBS_CANNOT_BE_MODIFIED("Cancelled jobs cannot be reactivated");

    private final String message;

    JobExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
