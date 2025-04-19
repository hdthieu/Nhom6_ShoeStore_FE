package com.shoestore.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjectGroup6FeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGroup6FeApplication.class, args);

    }

}
