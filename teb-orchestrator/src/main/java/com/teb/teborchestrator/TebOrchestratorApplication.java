package com.teb.teborchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TebOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TebOrchestratorApplication.class, args);
    }

}
