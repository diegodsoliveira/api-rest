package com.diego.spring.restfull;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = { "com.diego.spring.restfull.model" })
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.diego.spring.restfull.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class RestfullApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(RestfullApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("admin"));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) { // mapeamento global
		registry.addMapping("/usuario/**")
				.allowedMethods("*") // requisições http
				.allowedOrigins("*"); // domínios
	}
}
