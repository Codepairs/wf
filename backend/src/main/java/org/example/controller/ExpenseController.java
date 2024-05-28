package org.example.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseCreateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.expense.ExpenseUpdateDto;
import org.example.dto.income.IncomeCreateDto;
import org.example.service.ExpenseService;
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
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping(value = "/expenses/pagination")
    public ResponseEntity<Flux<ExpenseInfoDto>> getIncomesPagination(ServerWebExchange exchange) {
        Flux<ExpenseInfoDto> incomes = expenseService.getExpensesPagination(exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/expenses/expensesById?id={id}")
    public ResponseEntity<Mono<ExpenseInfoDto>> read(@PathVariable(name = "id") UUID id, ServerWebExchange exchange) {
        final Mono<ExpenseInfoDto> income = expenseService.getExpenseById(id, exchange);
        return income != null
                ? new ResponseEntity<>(income, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/expenses/expensesById?id=")
    public ResponseEntity<Mono<ExpenseInfoDto>> updateExpensesById(@PathVariable(name = "id") UUID id, @RequestBody ExpenseUpdateDto income, ServerWebExchange exchange) {
        final Mono<ExpenseInfoDto> updated = expenseService.updateExpensesById(income, id, exchange);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/expenses/expensesById?id={id}")
    public ResponseEntity<Mono<ExpenseInfoDto>> deleteExpensesById(@PathVariable(name = "id") UUID id) {
        final Mono<ExpenseInfoDto> deleted = expenseService.deleteExpensesById(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/expenses")
    public ResponseEntity<Mono<UUID>> createExpense(@Valid @RequestBody ExpenseCreateDto expense, ServerWebExchange exchange) {
        log.info("create expense " + expense);
        final Mono<UUID> created = expenseService.createExpense(expense, exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/expenses/expensesByMonths")
    public ResponseEntity<Mono<List<Map<String, Double>>>> getExpensesByMonths(ServerWebExchange exchange) throws InterruptedException {
        log.info("expenses by months ");
        final Mono<List<Map<String, Double>>> created = expenseService.getExpensesByMonths(exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}