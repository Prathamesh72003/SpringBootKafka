package com.kafkaproj.kafkaa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishResponseDto {
    private int publishedCount;
    private String message;

    public PublishResponseDto() {}
    public PublishResponseDto(int publishedCount, String message) {
        this.publishedCount = publishedCount;
        this.message = message;
    }
}
