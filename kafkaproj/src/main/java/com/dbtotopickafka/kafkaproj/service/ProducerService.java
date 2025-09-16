package com.dbtotopickafka.kafkaproj.service;

import com.dbtotopickafka.kafkaproj.dto.CorporateActionDistributionDto;
import com.dbtotopickafka.kafkaproj.producer.DistributionKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.kafka.KafkaException;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ProducerService {
    private final DistributionKafkaProducer producer;
    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);
    // how long to wait for all sends to confirm
    private final Duration sendTimeout = Duration.ofSeconds(10);

    public ProducerService(DistributionKafkaProducer producer) {
        this.producer = producer;
    }

    /**
     * Publish list of DTOs to Kafka. Waits for confirmations up to a timeout.
     * If any send fails, throws KafkaException.
     */
    public void publishAll(List<CorporateActionDistributionDto> dtos, String topic) {
        if (dtos == null || dtos.isEmpty()) return;

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(dtos.size(), 8));
        try {
            List<CompletableFuture<Void>> futures = dtos.stream().map(dto ->
                    CompletableFuture.runAsync(() -> {
                        try {
                            producer.send(topic, dto.getTransactionId(), dto).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);
                        } catch (ExecutionException ee) {
                            // underlying send failed
                            log.error("Send failed for id={} txId={}", dto.getId(), dto.getTransactionId(), ee.getCause());
                            throw new CompletionException(new KafkaException("publish failed", ee.getCause()));
                        } catch (TimeoutException te) {
                            log.error("Send timed out for id={} txId={}", dto.getId(), dto.getTransactionId(), te);
                            throw new CompletionException(new KafkaException("publish timed out", te));
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new CompletionException(new KafkaException("publish interrupted", ie));
                        }
                    }, executor)
            ).toList();

            // wait for all
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaException("Interrupted waiting for sends", e);
        } catch (ExecutionException | TimeoutException e) {
            throw new KafkaException("Failed sending messages", e);
        } finally {
            executor.shutdownNow();
        }
    }
}

