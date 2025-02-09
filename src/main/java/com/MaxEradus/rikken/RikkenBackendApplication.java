package com.MaxEradus.rikken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.MaxEradus.rikken" })
@EnableJpaRepositories(basePackages = "com.MaxEradus.rikken.Repos") // Scan for repositories
public class RikkenBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RikkenBackendApplication.class, args);
	}

}
