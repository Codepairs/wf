package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeCreateDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.income.IncomeUpdateDto;
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
import java.util.UUID;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IncomeService {
    private final WebClient webClient;
    private final UserService userService;


    public Flux<IncomeInfoDto> getIncomesPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        Map<String, String> category = new HashMap<>();
        return this.webClient.post()
                .uri("/incomes/pagination")
                .body(BodyInserters.fromValue(category))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }



    public Mono<IncomeInfoDto> update(IncomeUpdateDto income, UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.put()
                .uri("/incomes/incomesById?id={id}", id)
                .body(income, IncomeUpdateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(IncomeInfoDto.class);
    }


    public IncomeInfoDto delete(UUID id) {
        return null;
    }
        //return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);



    public Mono<UUID> createIncome(@RequestBody IncomeCreateDto income, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.post()
                .uri("/incomes")
                .body(BodyInserters.fromValue(income))
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(UUID.class);
    }


    public Mono<IncomeInfoDto> read(UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.post()
                .uri("/incomes/incomesById?id={id}", id)
                .body(Mono.empty(), String.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(IncomeInfoDto.class);
    }

    public Mono<List<Map<String, Double>>> getIncomesByMonths(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("months token" + token);
        log.info("months id " + userId);
        Flux<IncomeInfoDto> allIncomes = userService.getIncomesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));

        Mono<List<Map<String, Double>>> groupedIncomesByMonths = allIncomes
                .groupBy(income -> {
                    // Extract month from date string
                    return income.getGetDate().getMonthValue();
                })
                .flatMap(group -> group.reduce(0.0, (acc, income) -> acc + income.getValue())
                        .map(sum -> Map.of(group.key().toString(), sum)))
                .collectList();

        // Subscribe to process the data (optional)
        groupedIncomesByMonths.subscribe();

        return groupedIncomesByMonths;
    }
}
