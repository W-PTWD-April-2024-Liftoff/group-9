package com.parkrangers.parkquest_backend.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/park_quest")
                .username("park_quest")
                .password("parkrangers9")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
