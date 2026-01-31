package com.example.demo;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.example.demo")
@RestController
public class Demo1Application {

    /**
     * Entry point of the Spring Boot application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Start the Spring Boot application.
        SpringApplication.run(Demo1Application.class, args);

    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}
