package com.example.myapp.controller;

import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequestMapping
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UUID> create(@Valid @RequestBody UserCreateDto user) throws SQLUniqueException {
        UUID id = userService.create(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

}
