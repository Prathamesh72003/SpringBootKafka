package com.kafkaproj.kafkaa.repository;

import com.kafkaproj.kafkaa.entity.CorporateActionDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorporateActionDistributionRepository extends JpaRepository<CorporateActionDistribution, Long> {

    List<CorporateActionDistribution> findByTransactionId(String transactionId);

}
