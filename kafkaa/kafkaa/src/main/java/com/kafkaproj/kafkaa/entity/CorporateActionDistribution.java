package com.kafkaproj.kafkaa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private String metadata;

}
