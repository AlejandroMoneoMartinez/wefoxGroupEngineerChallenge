package com.wefox.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.wefox.domain.entity")
@EnableJpaRepositories(basePackages = {"com.wefox.repository.jpaRepository"})
public class PaymentProcessorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessorApplication.class, args);
	}
}