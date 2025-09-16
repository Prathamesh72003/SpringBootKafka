package com.dbtotopickafka.kafkaproj.dto;

import jakarta.validation.constraints.NotBlank;

public class TransactionPublishRequest {
    @NotBlank(message = "transactionId is required")
    private String transactionId;

    public TransactionPublishRequest() {}
    public TransactionPublishRequest(String transactionId) { this.transactionId = transactionId; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}

