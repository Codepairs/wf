package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.user.UserInfoDto;
import org.example.dto.user.UserUpdateDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }



    public Flux<UserInfoDto> getUsersPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("token " + token);

        return this.webClient.post()
                        .uri("/users/pagination")
                        .body(Mono.empty(), String.class)
                        .header("Authorization",  token)
                        .retrieve()
                        .bodyToFlux(UserInfoDto.class);
    }

    //TODO add filters
    public Flux<UserInfoDto> getUsersFilter() {
        return this.webClient.get().uri("/users/getByFilter")
                .retrieve()
                .bodyToFlux(UserInfoDto.class);
    }

    public Flux<IncomeInfoDto> getIncomesById(UUID id) {
        return this.webClient.get().uri("/users/getIncomesById/{id}", id)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }

    public Flux<ExpenseInfoDto> getExpensesById(UUID id) {
        return this.webClient.get().uri("/users/getExpensesById/{id}", id)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }

    public Mono<UserInfoDto> getUserById(UUID id) {
        return this.webClient.get().uri("/users/usersById/{id}", id)
                .retrieve()
                .bodyToMono(UserInfoDto.class);
    }


    public Mono<UserInfoDto> updateUserById(UserUpdateDto user, UUID id) {
        return this.webClient.put().uri("/users/usersById/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(user), UserUpdateDto.class)
                .retrieve()
                .bodyToMono(UserInfoDto.class);
    }

    public Mono<Void> deleteUserById(UUID id) {
        return this.webClient.delete().uri("/users/usersById/{id}")
                .retrieve()
                .bodyToMono(Void.class);
    }
}