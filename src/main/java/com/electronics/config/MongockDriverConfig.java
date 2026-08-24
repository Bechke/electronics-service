package com.electronics.config;

import com.mongodb.reactivestreams.client.MongoClient;
import io.mongock.driver.api.driver.ConnectionDriver;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * Provides the Mongock ConnectionDriver bean explicitly.
 *
 * Mongock 5.x auto-configuration uses the old spring.factories mechanism
 * which Spring Boot 3.4 no longer scans for auto-configuration.
 * Creating the bean here bypasses that and wires Mongock correctly.
 */
@Configuration
public class MongockDriverConfig {

    @Bean
    public ConnectionDriver mongockConnectionDriver(
            MongoClient mongoClient,
            ReactiveMongoTemplate reactiveMongoTemplate) {
        String dbName = reactiveMongoTemplate.getMongoDatabase()
                .map(db -> db.getName())
                .block();
        return MongoReactiveDriver.withDefaultLock(mongoClient, dbName);
    }
}
