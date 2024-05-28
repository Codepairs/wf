package org.example.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseCreateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.expense.ExpenseUpdateDto;
import org.example.dto.income.IncomeCreateDto;
import org.example.dto.income.IncomeInfoDto;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class ExpenseService {
    private final WebClient webClient;
    private final UserService userService;

    public Flux<ExpenseInfoDto> getExpensesPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);
        Map<String, String> category = new HashMap<>();
        return this.webClient.post()
                .uri("/expenses/pagination")
                .body(BodyInserters.fromValue(category))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }



    public Mono<ExpenseInfoDto> updateExpensesById(ExpenseUpdateDto expense, UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.put()
                .uri("/expenses/expensesById?id={id}", id)
                .body(expense, ExpenseUpdateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(ExpenseInfoDto.class);
    }


    public Mono<ExpenseInfoDto> deleteExpensesById(UUID id) {
        return null;
    }
    //return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);



    public Mono<UUID> createExpense(@RequestBody ExpenseCreateDto expense, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token create" + token);

        return this.webClient.post()
                .uri("/expenses")
                .body(BodyInserters.fromValue(expense))
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(UUID.class);
    }


    public Mono<ExpenseInfoDto> getExpenseById(UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.post()
                .uri("/expenses/expensesById?id={id}", id)
                .body(Mono.empty(), String.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(ExpenseInfoDto.class);
    }

    public Mono<List<Map<String, Double>>> getExpensesByMonths(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("months token" + token);
        log.info("months id " + userId);
        Flux<ExpenseInfoDto> allExpenses = userService.getExpensesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));


        Mono<List<Map<String, Double>>> groupedExpensesByMonths = allExpenses
                .groupBy(expense -> {
                    // Extract month from date string
                    return expense.getGetDate().getMonthValue();
                })
                .flatMap(group -> group.reduce(0.0, (acc, expense) -> acc + expense.getValue())
                        .map(sum -> Map.of(group.key().toString(), sum)))
                .collectList();

        // Subscribe to process the data (optional)
        groupedExpensesByMonths.subscribe();

        return groupedExpensesByMonths;
    }



}
