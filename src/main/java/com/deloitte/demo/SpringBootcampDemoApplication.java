package com.deloitte.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@EnableJpaRepositories("com.deloitte.demo.repositories")
@EntityScan("com.deloitte.demo.entities")
@SpringBootApplication(scanBasePackages = "com.deloitte.demo")
public class SpringBootcampDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootcampDemoApplication.class, args);
	}

}