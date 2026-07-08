package com.electronics.repository;

import com.electronics.model.Electronics;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ElectronicsRepository extends ReactiveMongoRepository<Electronics, String> {
    Flux<Electronics> findByOwnerId(String ownerId);
    Flux<Electronics> findByOrganizationId(String organizationId);
    Flux<Electronics> findBySubCategory(String subCategory);
    Flux<Electronics> findByCity(String city);
    Flux<Electronics> findByListingStatus(String listingStatus);
}
