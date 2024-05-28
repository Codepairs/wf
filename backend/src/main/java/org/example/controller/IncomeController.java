package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.income.IncomeCreateDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.income.IncomeUpdateDto;
import org.example.service.IncomeService;
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
public class IncomeController {

    private final IncomeService incomeService;


    @PostMapping(value = "/incomes/pagination")
    public ResponseEntity<Flux<IncomeInfoDto>> getIncomesPagination(ServerWebExchange exchange) {
        Flux<IncomeInfoDto> incomes = incomeService.getIncomesPagination(exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<Mono<IncomeInfoDto>> read(@PathVariable(name = "id") UUID id, ServerWebExchange exchange) {
        final Mono<IncomeInfoDto> income = incomeService.read(id, exchange);
        return income != null
                ? new ResponseEntity<>(income, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody IncomeUpdateDto income, ServerWebExchange exchange) {
        final Mono<IncomeInfoDto> updated = incomeService.update(income, id, exchange);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final IncomeInfoDto deleted = incomeService.delete(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/incomes")
    public ResponseEntity<Mono<UUID>> createIncome(@RequestBody IncomeCreateDto income, ServerWebExchange exchange) {
        log.info("create income " + income);
        final Mono<UUID> created = incomeService.createIncome(income, exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @GetMapping(value = "/incomes/incomesByMonths")
    public ResponseEntity<Mono<List<Map<String, Double>>>> getIncomesByMonths(ServerWebExchange exchange) throws InterruptedException {
        log.info("incomes by months ");
        final Mono<List<Map<String, Double>>> created = incomeService.getIncomesByMonths(exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
