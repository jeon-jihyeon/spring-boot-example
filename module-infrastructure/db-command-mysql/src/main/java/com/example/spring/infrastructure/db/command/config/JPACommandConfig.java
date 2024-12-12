package com.example.spring.infrastructure.db.command.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.spring.infrastructure.db.command")
@EnableJpaRepositories(basePackages = "com.example.spring.infrastructure.db.command")
class JPACommandConfig {
}
