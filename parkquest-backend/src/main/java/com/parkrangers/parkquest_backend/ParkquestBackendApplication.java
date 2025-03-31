package com.parkrangers.parkquest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = "com.parkrangers.parkquest_backend")
@EntityScan(basePackages = "com.parkrangers.parkquest_backend.model")
@EnableJpaRepositories(basePackages = "com.parkrangers.parkquest_backend.repository")
public class ParkquestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkquestBackendApplication.class, args);
	}

}
