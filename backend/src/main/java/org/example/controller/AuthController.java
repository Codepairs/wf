package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.user.UserCreateDto;
import org.example.dto.user.UserLoginDto;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping(value = "/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateDto user) {
        Boolean created = authService.registerUser(user);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDto user, ServerWebExchange exchange) {
        System.out.println("login");
        Mono<Map> headerMap = authService.login(user, exchange);

        return headerMap != null
                ? new ResponseEntity<>(headerMap, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}