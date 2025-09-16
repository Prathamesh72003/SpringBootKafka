package com.kafkaproj.kafkaa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorporateActionDistributionDto {

    private Long id;
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime effectiveDate;
    private String metadata;

}
