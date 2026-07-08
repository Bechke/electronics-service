package com.electronics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElectronicsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsServiceApplication.class, args);
    }
}
