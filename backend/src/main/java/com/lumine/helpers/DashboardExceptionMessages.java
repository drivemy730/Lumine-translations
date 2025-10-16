package com.lumine.helpers;

public enum DashboardExceptionMessages
{
    DASHBOARD_STATS_FETCHED_SUCCESS("Client dashboard stats loaded."),
    DASHBOARD_HISTORY_FETCHED_SUCCESS("Translation history retrieved."),
    DASHBOARD_UNAUTHORIZED_ACCESS("Unauthorized access to client dashboard."),
    DASHBOARD_DATA_AGGREGATION_FAILED("Error aggregating dashboard data.");

    private final String message;

    DashboardExceptionMessages(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
