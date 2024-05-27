package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeCreateDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.income.IncomeUpdateDto;
import org.example.dto.user.UserInfoDto;
import org.example.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import java.util.*;
import java.util.List;

@Service
@Slf4j
public class IncomeService {



    private final WebClient webClient;

    public IncomeService (WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<IncomeInfoDto> getIncomesPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.post()
                .uri("/incomes/pagination")
                .body(Mono.empty(), String.class)
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



    public Mono<IncomeInfoDto> create(IncomeCreateDto income, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);

        return this.webClient.post()
                .uri("/incomes")
                .body(income, IncomeCreateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(IncomeInfoDto.class);
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


}
