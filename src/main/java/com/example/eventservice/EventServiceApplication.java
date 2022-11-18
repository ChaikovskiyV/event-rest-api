package com.example.eventservice;

import com.example.eventservice.model.configuration.ModelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.example.eventservice"})
@EntityScan(basePackages = "com.example.eventservice.model.entity")
@Import({ModelConfig.class})
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

}
