package com.diego.spring.restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = { "com.diego.spring.restfull.model" })
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.diego.spring.restfull.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class RestfullApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfullApplication.class, args);
	}

}
