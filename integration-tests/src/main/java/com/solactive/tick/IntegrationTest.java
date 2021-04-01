package com.solactive.tick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IntegrationTest {
    public static void main(String[] args) {
        SpringApplication.run(IntegrationTest.class, args);
    }
}