package com.kafkaproj.kafkaa.controller;

import com.kafkaproj.kafkaa.dto.PublishResponseDto;
import com.kafkaproj.kafkaa.dto.TransactionPublishRequest;
import com.kafkaproj.kafkaa.service.DistributionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cad-distributions")
public class DistributionController {

    private static final Logger log = LoggerFactory.getLogger(DistributionController.class);

    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.OK)
    public PublishResponseDto publishByTransaction(@Valid @RequestBody TransactionPublishRequest request) {
        log.info("Publish request received for transactionId={}", request.getTransactionId());
        return distributionService.publishByTransactionId(request.getTransactionId());
    }

}
