package org.example.controller;


import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.user.UserInfoDto;
import org.example.dto.user.UserUpdateDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/incomesById/?id={id}")
    public ResponseEntity<List<IncomeInfoDto>> getIncomesById(ServerWebExchange exchange) throws ExecutionException, InterruptedException {
        List<IncomeInfoDto> incomes = userService.getIncomesById(exchange).get();
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/expensesById/{id}")
    public ResponseEntity<List<ExpenseInfoDto>> getExpensesById(ServerWebExchange exchange) throws ExecutionException, InterruptedException {
        List<ExpenseInfoDto> incomes = userService.getExpensesById(exchange).get();
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/usersById/{id}")
    public ResponseEntity<Mono<UserInfoDto>> getUserById(@PathVariable(name = "id") UUID id, ServerWebExchange exchange) {
        final Mono<UserInfoDto> user = userService.getUserById(id, exchange);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/usersById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody UserUpdateDto user) {
        final Mono<UserInfoDto> updated = userService.updateUserById(user, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/usersById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final Mono<Void> deleted = userService.deleteUserById(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/pagination")
    public ResponseEntity<Flux<UserInfoDto>> getUsersPagination(ServerWebExchange exchange) {
        final Flux<UserInfoDto> users = userService.getUsersPagination(exchange);
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
