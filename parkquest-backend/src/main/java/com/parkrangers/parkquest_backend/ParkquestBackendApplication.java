package com.parkrangers.parkquest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ParkquestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkquestBackendApplication.class, args);
	}

}
