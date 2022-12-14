package com.example.eventservice.model.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.eventservice")
@EnableTransactionManagement
public class ModelConfig {
}
