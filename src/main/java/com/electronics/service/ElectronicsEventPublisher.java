package com.electronics.service;

import com.electronics.event.ElectronicsAdEvent;
import com.electronics.model.Electronics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
public class ElectronicsEventPublisher {

    private final KafkaTemplate<String, ElectronicsAdEvent> kafkaTemplate;
    private final String topic;

    public ElectronicsEventPublisher(
            KafkaTemplate<String, ElectronicsAdEvent> kafkaTemplate,
            @Value("${electronics.kafka.topic.ad-events:electronics-ad-events}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic         = topic;
    }

    public Mono<Void> publishCreated(Electronics e) { return publish("CREATED", e); }
    public Mono<Void> publishUpdated(Electronics e) { return publish("UPDATED", e); }

    public Mono<Void> publishDeleted(String id) {
        ElectronicsAdEvent event = new ElectronicsAdEvent();
        event.setEventType("DELETED");
        event.setElectronicsId(id);
        event.setUpdatedAt(Instant.now());
        return send(id, event);
    }

    private Mono<Void> publish(String eventType, Electronics e) {
        ElectronicsAdEvent event = new ElectronicsAdEvent(
                eventType, e.getId(), e.getCategory(), e.getSubCategory(),
                e.getTitle(), e.getBrand(), e.getPrice(), e.getCurrency(),
                e.getFeatureImage(), e.getCountry(), e.getState(), e.getCity(),
                e.getNeighbourhood(), e.getListingStatus(), e.getOwnerId(),
                e.getOrganizationId(), e.getCreatedAt(), e.getUpdatedAt());
        return send(e.getId(), event);
    }

    private Mono<Void> send(String key, ElectronicsAdEvent event) {
        return Mono.fromFuture(kafkaTemplate.send(topic, key, event))
                .doOnSuccess(r  -> log.info("Published {} event for electronics {}", event.getEventType(), key))
                .doOnError(err -> log.error("Failed to publish {} event for {}: {}", event.getEventType(), key, err.getMessage()))
                .onErrorResume(err -> Mono.empty())
                .then();
    }
}
