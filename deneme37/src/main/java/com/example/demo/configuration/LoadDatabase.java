package com.example.demo.configuration;

import com.example.demo.enums.Status;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Order("iphone")));
            log.info("Preloading " + repository.save(new Order("mac")));
        };
    }
}