package com.dbtotopickafka.kafkaproj.dto;

public class PublishResponseDto {
    private int publishedCount;
    private String message;

    public PublishResponseDto() {}
    public PublishResponseDto(int publishedCount, String message) {
        this.publishedCount = publishedCount;
        this.message = message;
    }

    public int getPublishedCount() { return publishedCount; }
    public void setPublishedCount(int publishedCount) { this.publishedCount = publishedCount; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

