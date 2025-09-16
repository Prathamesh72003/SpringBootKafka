package com.dbtotopickafka.kafkaproj.service;

import com.dbtotopickafka.kafkaproj.entity.CorporateActionDistribution;
import com.dbtotopickafka.kafkaproj.exceptions.NotFoundException;
import com.dbtotopickafka.kafkaproj.repository.CorporateActionDistributionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DistributionService {
    private static final Logger log = LoggerFactory.getLogger(DistributionService.class);
    private final CorporateActionDistributionRepository repository;

    public DistributionService(CorporateActionDistributionRepository repository) {
        this.repository = repository;
    }

    public List<CorporateActionDistribution> findByTransactionId(String transactionId) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId must be provided");
        }
        List<CorporateActionDistribution> list = repository.findByTransactionId(transactionId);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException("No distributions found for transactionId: " + transactionId);
        }
        return list;
    }
}

