package com.github.codeine200.soltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.codeine200.soltracker.remote")
public class SolTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolTrackerApplication.class, args);
    }

}
