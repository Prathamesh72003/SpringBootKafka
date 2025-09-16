package com.dbtotopickafka.kafkaproj.dto;

import java.time.OffsetDateTime;

public class ErrorResponse {
    private String error;
    private String message;
    private OffsetDateTime timestamp;

    public ErrorResponse() {}

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = OffsetDateTime.now();
    }

    // getters/setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
