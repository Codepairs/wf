package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
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
    /*


    public IncomeInfoDto update(IncomeUpdateDto income, UUID id) {
        return restTemplate.postForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), income, IncomeInfoDto.class);
    }


    public IncomeInfoDto delete(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);
    }


    public IncomeInfoDto create(IncomeCreateDto income) {
        return restTemplate.postForObject("http://localhost:8080/incomes", income, IncomeInfoDto.class);
    }


    public IncomeInfoDto read(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);
    }
    */

}
