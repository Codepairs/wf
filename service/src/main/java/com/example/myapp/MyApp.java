package com.example.myapp;

import com.example.myapp.service.IncomeService;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyApp {


    private final UserService userService;
    private final IncomeService incomeService;


    @Autowired
    public MyApp(UserService userService, IncomeService incomeService) {
        this.userService = userService;
        this.incomeService = incomeService;
    }


    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }



}