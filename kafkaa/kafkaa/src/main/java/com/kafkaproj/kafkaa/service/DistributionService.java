package com.kafkaproj.kafkaa.service;

import com.kafkaproj.kafkaa.dto.CorporateActionDistributionDto;
import com.kafkaproj.kafkaa.dto.PublishResponseDto;
import com.kafkaproj.kafkaa.entity.CorporateActionDistribution;
import com.kafkaproj.kafkaa.exception.NotFoundException;
import com.kafkaproj.kafkaa.repository.CorporateActionDistributionRepository;
import com.kafkaproj.kafkaa.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {
    private static final Logger log = LoggerFactory.getLogger(DistributionService.class);
    private final CorporateActionDistributionRepository corporateActionDistributionRepository;
    private final ProducerService producerService;
    private String topic;

    public DistributionService(CorporateActionDistributionRepository corporateActionDistributionRepository, ProducerService producerService, @Value("${app.kafka.default-topic}") String topic) {
        this.corporateActionDistributionRepository = corporateActionDistributionRepository;
        this.producerService = producerService;
        this.topic = topic;
    }

    public PublishResponseDto publishByTransactionId(String transactionId){
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId must be provided");
        }

        List<CorporateActionDistribution> entities = corporateActionDistributionRepository.findByTransactionId(transactionId);

        if (entities == null || entities.isEmpty()) {
            throw new NotFoundException("No distributions found for transactionId: " + transactionId);
        }

        List<CorporateActionDistributionDto> dtos = entities.stream()
                .map(MapperUtil::toDto)
                .toList();

        producerService.publishAll(dtos, topic);

        return new PublishResponseDto(
                dtos.size(),
                "Published " + dtos.size() + " messages to topic " + topic
        );
    }
}
