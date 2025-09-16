package com.dbtotopickafka.kafkaproj.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CorporateActionDistributionDto {
    private Long id;
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime effectiveDate;
    private String metadata; // keep JSON as string

    public CorporateActionDistributionDto() {}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDateTime effectiveDate) { this.effectiveDate = effectiveDate; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}

