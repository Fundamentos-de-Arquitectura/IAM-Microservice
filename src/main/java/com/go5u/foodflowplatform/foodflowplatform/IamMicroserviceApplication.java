package com.go5u.foodflowplatform.foodflowplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IamMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamMicroserviceApplication.class, args);
    }

}
