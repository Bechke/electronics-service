package com.electronics.service;

import com.electronics.model.Electronics;
import com.electronics.repository.ElectronicsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
public class ElectronicsService {

    private final ElectronicsRepository    repository;
    private final ElectronicsEventPublisher eventPublisher;

    public ElectronicsService(ElectronicsRepository repository,
                              ElectronicsEventPublisher eventPublisher) {
        this.repository     = repository;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Electronics> createElectronics(Electronics item) {
        item.setCreatedAt(Instant.now());
        item.setUpdatedAt(Instant.now());
        if (item.getListingStatus() == null) item.setListingStatus("PENDING");
        return repository.save(item)
                .flatMap(saved -> eventPublisher.publishCreated(saved).thenReturn(saved))
                .doOnSuccess(e -> log.info("Created electronics id={}", e.getId()));
    }

    public Mono<Electronics> getById(String id) {
        return repository.findById(id);
    }

    public Flux<Electronics> getAll(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size);
    }

    public Mono<Electronics> update(String id, Electronics updated) {
        return repository.findById(id)
                .flatMap(existing -> {
                    updated.setId(id);
                    updated.setCreatedAt(existing.getCreatedAt());
                    updated.setUpdatedAt(Instant.now());
                    return repository.save(updated);
                })
                .flatMap(saved -> eventPublisher.publishUpdated(saved).thenReturn(saved));
    }

    public Mono<Boolean> delete(String id) {
        return repository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) return Mono.just(false);
                    return repository.deleteById(id)
                            .then(eventPublisher.publishDeleted(id))
                            .thenReturn(true);
                });
    }

    public Flux<Electronics> getByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    public Flux<Electronics> getByOrganizationId(String orgId) {
        return repository.findByOrganizationId(orgId);
    }

    public Flux<Electronics> search(String subCategory, String city,
                                    BigDecimal minPrice, BigDecimal maxPrice) {
        return repository.findAll()
                .filter(e -> subCategory == null || subCategory.equalsIgnoreCase(e.getSubCategory()))
                .filter(e -> city        == null || city.equalsIgnoreCase(e.getCity()))
                .filter(e -> minPrice    == null || (e.getPrice() != null && e.getPrice().compareTo(minPrice) >= 0))
                .filter(e -> maxPrice    == null || (e.getPrice() != null && e.getPrice().compareTo(maxPrice) <= 0));
    }
}
