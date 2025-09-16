package com.kafkaproj.kafkaa.service;

import com.kafkaproj.kafkaa.dto.CorporateActionDistributionDto;
import com.kafkaproj.kafkaa.producer.DistributionKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ProducerService {

    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);
    private final DistributionKafkaProducer distributionKafkaProducer;
    private final Duration sendTimeout = Duration.ofSeconds(10);

    public ProducerService(DistributionKafkaProducer distributionKafkaProducer) {
        this.distributionKafkaProducer = distributionKafkaProducer;
    }

    public void publishAll(List<CorporateActionDistributionDto> dtos, String topic){
        if (dtos==null || dtos.isEmpty()){
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(dtos.size(), 8));

        try {
            List<CompletableFuture<Void>> futures = dtos.stream().map(dto ->
                    CompletableFuture.runAsync(() -> {
                        try {
                            distributionKafkaProducer.send(topic, dto.getTransactionId(), dto).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);
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
                    }, executorService)
            ).toList();

            // wait for all
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaException("Interrupted waiting for sends", e);
        } catch (ExecutionException | TimeoutException e) {
            throw new KafkaException("Failed sending messages", e);
        } finally {
            executorService.shutdownNow();
        }

    }
}
