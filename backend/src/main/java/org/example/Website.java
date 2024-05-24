package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;


@SpringBootApplication
public class Website {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Website.class);
        app.run(args);
    }


}