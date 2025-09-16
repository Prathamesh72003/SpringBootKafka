package com.dbtotopickafka.kafkaproj.controller;

import com.dbtotopickafka.kafkaproj.dto.CorporateActionDistributionDto;
import com.dbtotopickafka.kafkaproj.dto.PublishResponseDto;
import com.dbtotopickafka.kafkaproj.dto.TransactionPublishRequest;
import com.dbtotopickafka.kafkaproj.entity.CorporateActionDistribution;
import com.dbtotopickafka.kafkaproj.service.DistributionService;
import com.dbtotopickafka.kafkaproj.service.ProducerService;
import com.dbtotopickafka.kafkaproj.util.MapperUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/distributions")
public class DistributionController {

    private final DistributionService distributionService;
    private final ProducerService producerService;
    private final String topic;
    private static final Logger log = LoggerFactory.getLogger(DistributionController.class);

    public DistributionController(DistributionService distributionService, ProducerService producerService,
                                  @Value("${app.kafka.default-topic}") String topic) {
        this.distributionService = distributionService;
        this.producerService = producerService;
        this.topic = topic;
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.OK)
    public PublishResponseDto publishByTransaction(@Valid @RequestBody TransactionPublishRequest request) {
        String txId = request.getTransactionId();
        log.info("Publish request received for transactionId={}", txId);

        List<CorporateActionDistribution> entities = distributionService.findByTransactionId(txId);

        List<CorporateActionDistributionDto> dtos = entities.stream()
                .map(MapperUtil::toDto)
                .collect(Collectors.toList());

        // publish and wait for confirmations (in ProducerService)
        producerService.publishAll(dtos, topic);

        return new PublishResponseDto(dtos.size(), "Published " + dtos.size() + " messages to topic " + topic);
    }
}

