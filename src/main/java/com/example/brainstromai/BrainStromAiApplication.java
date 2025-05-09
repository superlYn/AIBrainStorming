package com.example.brainstromai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BrainStromAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrainStromAiApplication.class, args);
    }

}
