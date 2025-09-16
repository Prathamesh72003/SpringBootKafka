package com.kafkaproj.kafkaa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionPublishRequest {
    @NotBlank(message = "transactionId is required")
    private String transactionId;
}


