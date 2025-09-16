package com.dbtotopickafka.kafkaproj.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "corporate_action_distribution")
public class CorporateActionDistribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transaction_id", nullable = false)
    private String transactionId;

    @Column(name="account_id", nullable = false)
    private String accountId;

    @Column(name="amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name="currency", length = 10, nullable = false)
    private String currency;

    @Column(name="effective_date")
    private LocalDateTime effectiveDate;

    @Column(name="metadata", columnDefinition = "TEXT")
    private String metadata; // JSON string

    // getters / setters
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

