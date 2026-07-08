package com.electronics.controller;

import com.electronics.model.Electronics;
import com.electronics.service.ElectronicsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}")
public class ElectronicsController {

    private final ElectronicsService service;

    public ElectronicsController(ElectronicsService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<Electronics>> create(
            @Valid @RequestBody Electronics item,
            @RequestHeader(value = "X-User-Id",    required = false) String userId,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {

        item.setOwnerId(userId);
        return service.createElectronics(item)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
                .onErrorResume(e -> {
                    log.error("Error creating electronics: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Electronics>>> getAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "50") int size) {
        Flux<Electronics> items = service.getAll(page, size);
        return items.hasElements()
                .flatMap(has -> has
                        ? Mono.just(ResponseEntity.ok(items))
                        : Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Electronics>> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Electronics>> update(
            @PathVariable String id,
            @RequestBody Electronics updated,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        return service.getById(id)
                .<ResponseEntity<Electronics>>flatMap(existing -> {
                    if (userId != null && !userId.equals(existing.getOwnerId())) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
                    }
                    updated.setOwnerId(existing.getOwnerId());
                    if (updated.getOrganizationId() == null) {
                        updated.setOrganizationId(existing.getOrganizationId());
                    }
                    return service.update(id, updated).map(ResponseEntity::ok);
                })
                .defaultIfEmpty(ResponseEntity.<Electronics>notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        return service.getById(id)
                .flatMap(existing -> {
                    if (userId != null && !userId.equals(existing.getOwnerId())) {
                        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                    }
                    return service.delete(id)
                            .flatMap(deleted -> deleted
                                    ? Mono.just(ResponseEntity.noContent().build())
                                    : Mono.empty());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    public Mono<ResponseEntity<Flux<Electronics>>> getByOwner(@PathVariable String ownerId) {
        Flux<Electronics> items = service.getByOwnerId(ownerId);
        return items.hasElements()
                .flatMap(has -> has
                        ? Mono.just(ResponseEntity.ok(items))
                        : Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/org/{organizationId}")
    public Mono<ResponseEntity<Flux<Electronics>>> getByOrg(@PathVariable String organizationId) {
        Flux<Electronics> items = service.getByOrganizationId(organizationId);
        return items.hasElements()
                .flatMap(has -> has
                        ? Mono.just(ResponseEntity.ok(items))
                        : Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<Flux<Electronics>>> search(
            @RequestParam(required = false) String subCategory,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        Flux<Electronics> items = service.search(subCategory, city, minPrice, maxPrice);
        return items.hasElements()
                .flatMap(has -> has
                        ? Mono.just(ResponseEntity.ok(items))
                        : Mono.just(ResponseEntity.noContent().build()));
    }
}
