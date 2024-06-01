package org.example.controller;


import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/incomesById?id={id}")
    public ResponseEntity<Flux<IncomeInfoDto>> getIncomesById(ServerWebExchange exchange) {
        Flux<IncomeInfoDto> incomes = userService.getIncomesById(exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/expensesById?id={id}")
    public ResponseEntity<Flux<ExpenseInfoDto>> getExpensesById(ServerWebExchange exchange) {
        Flux<ExpenseInfoDto> incomes = userService.getExpensesById(exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/expensesInLastMonth")
    public ResponseEntity<Flux<ExpenseInfoDto>> getExpensesInLastMonth(ServerWebExchange exchange) {
        Flux<ExpenseInfoDto> expenses = userService.getExpensesInLastMonth(exchange);
        return expenses != null
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/incomesInLastMonth")
    public ResponseEntity<Flux<IncomeInfoDto>> getIncomesInLastMonth(ServerWebExchange exchange) {
        Flux<IncomeInfoDto> expenses = userService.getIncomesInLastMonth(exchange);
        return expenses != null
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/incomesInLastThreeDays")
    public ResponseEntity<Flux<IncomeInfoDto>> getIncomesInLastThreeDays(ServerWebExchange exchange) {
        log.info("incomes 3 days ");
        Flux<IncomeInfoDto> incomes = userService.getIncomesInLastThreeDays(exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/expensesInLastThreeDays")
    public ResponseEntity<Flux<ExpenseInfoDto>> getExpensesInLastThreeDays(ServerWebExchange exchange) {
        Flux<ExpenseInfoDto> expenses = userService.getExpensesInLastThreeDays(exchange);
        return expenses != null
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/usersById?id={id}")
    public ResponseEntity<Mono<UserInfoDto>> getUserById(@PathVariable(name = "id") UUID id, ServerWebExchange exchange) {
        final Mono<UserInfoDto> user = userService.getUserById(id, exchange);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/usersById?id={id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody UserUpdateDto user) {
        final Mono<UserInfoDto> updated = userService.updateUserById(user, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/usersById?id={id}")
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


    @GetMapping(value = "/transactionsByMonths")
    public ResponseEntity<Mono<List<Map<String, Double>>>> getTransactionsByMonths(ServerWebExchange exchange) throws InterruptedException {
        log.info("transactions by months ");
        final Mono<List<Map<String, Double>>> created = userService.getTransactionsByMonths(exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
