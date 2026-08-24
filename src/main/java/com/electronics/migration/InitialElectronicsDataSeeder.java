package com.electronics.migration;

import com.electronics.model.Electronics;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Mongock ChangeUnit — runs exactly once on startup and seeds 5 demo
 * electronics listings into MongoDB, all located in Hyderabad / Banjara
 * Hills, matching the location used for the vehicle-service seed data.
 *
 * The IDs here intentionally match the image paths uploaded by
 * image-storage-service's SeedImageLoader.
 *
 * Mongock tracks executed change-units in the `mongockChangeLog` collection,
 * so this never runs a second time even if the application restarts.
 */
@ChangeUnit(id = "seed-initial-electronics", order = "001", author = "bechke-dev")
public class InitialElectronicsDataSeeder {

    private static final String SELLER_ID = "seed-seller-001";

    @Execution
    public void execution(ReactiveMongoTemplate template) {
        List<Electronics> items = buildSeedElectronics();

        items.forEach(e -> {
            boolean exists = Boolean.TRUE.equals(
                    template.exists(Query.query(Criteria.where("_id").is(e.getId())),
                                    Electronics.class)
                             .block());
            if (!exists) {
                template.insert(e).block();
            }
        });
    }

    @RollbackExecution
    public void rollback(ReactiveMongoTemplate template) {
        List<String> seedIds = List.of(
                "seed-electronics-001", "seed-electronics-002", "seed-electronics-003",
                "seed-electronics-004", "seed-electronics-005");
        template.remove(
                Query.query(Criteria.where("_id").in(seedIds)),
                Electronics.class).block();
    }

    // ── Seed data ─────────────────────────────────────────────────────────────

    private List<Electronics> buildSeedElectronics() {
        return List.of(
            build("seed-electronics-001", "MOBILE", "Apple", "iPhone 15 Pro", "Titanium Black",
                "Apple iPhone 15 Pro, 256GB, Titanium Black. Barely used, comes with box and charger.",
                new BigDecimal("119999"), new BigDecimal("139900"), "USED", 6,
                "256GB", "8GB",
                Instant.parse("2024-01-02T10:00:00Z")),

            build("seed-electronics-002", "LAPTOP", "Dell", "XPS 15", "Platinum Silver",
                "Dell XPS 15, Intel i7 13th Gen, 16GB RAM, 512GB SSD, 4K OLED display. Excellent condition.",
                new BigDecimal("145000"), new BigDecimal("189990"), "USED", 10,
                "512GB", "16GB",
                Instant.parse("2024-01-03T11:00:00Z")),

            build("seed-electronics-003", "TV", "Samsung", "55\" Neo QLED 4K", "Black",
                "Samsung 55-inch Neo QLED 4K Smart TV, sealed box, unused.",
                new BigDecimal("68000"), new BigDecimal("89999"), "NEW", 0,
                null, null,
                Instant.parse("2024-01-04T09:30:00Z")),

            build("seed-electronics-004", "CAMERA", "Sony", "Alpha a6400", "Black",
                "Sony Alpha a6400 mirrorless camera with 16-50mm kit lens. Low shutter count.",
                new BigDecimal("72000"), new BigDecimal("94990"), "USED", 14,
                null, null,
                Instant.parse("2024-01-05T14:00:00Z")),

            build("seed-electronics-005", "AUDIO", "Sony", "WH-1000XM5", "Midnight Blue",
                "Sony WH-1000XM5 wireless noise-cancelling headphones. Unopened, sealed retail box.",
                new BigDecimal("22000"), new BigDecimal("29990"), "NEW", 0,
                null, null,
                Instant.parse("2024-01-06T08:00:00Z"))
        );
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Electronics build(String id, String subCategory, String brand, String model, String color,
                               String description, BigDecimal price, BigDecimal originalPrice,
                               String condition, int ageMonths,
                               String storageCapacity, String ramCapacity,
                               Instant createdAt) {
        Electronics e = new Electronics();
        e.setId(id);
        e.setCategory("ELECTRONICS");
        e.setSubCategory(subCategory);
        e.setTitle(brand + " " + model);
        e.setBrand(brand);
        e.setModel(model);
        e.setColor(color);
        e.setDescription(description);
        e.setPrice(price);
        e.setCurrency("₹");
        e.setOriginalPrice(originalPrice);
        e.setCondition(condition);
        e.setAgeMonths(ageMonths);
        e.setStorageCapacity(storageCapacity);
        e.setRamCapacity(ramCapacity);
        e.setLocation("Hyderabad");
        e.setCountry("India");
        e.setState("Telangana");
        e.setCity("Hyderabad");
        e.setNeighbourhood("Banjara Hills");
        e.setFeatureImage("electronics/" + id + ".jpg");
        e.setImageUrls(List.of(
                "electronics/" + id + ".jpg",
                "electronics/" + id + "-alt.jpg"));
        e.setListingStatus("ACTIVE");
        e.setOwnerId(SELLER_ID);
        e.setNumberOfViews(0L);
        e.setCreatedAt(createdAt);
        e.setUpdatedAt(createdAt);
        return e;
    }
}
