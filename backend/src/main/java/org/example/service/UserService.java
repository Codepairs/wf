package org.example.service;

import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserUpdateDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserService {
    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/users").build();
    }

    //TODO add filters
    public Flux<UserInfoDto> getUsersFilter(){
        return this.webClient.get().uri("/getByFilter")
                .retrieve()
                .bodyToFlux(UserInfoDto.class);
    }

    public Flux<UserInfoDto> getUsersPagination(){
        return this.webClient.get().uri("/pagination")
                .retrieve()
                .bodyToFlux(UserInfoDto.class);
    }

    public Flux<IncomeInfoDto> getIncomesById(UUID id){
        return this.webClient.get().uri("/getIncomesById/{id}", id)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }

    public Flux<ExpenseInfoDto> getExpensesById(UUID id){
        return this.webClient.get().uri("/getExpensesById/{id}", id)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }

    public Mono<UserInfoDto> getUserById(UUID id){
        return this.webClient.get().uri("/usersById/{id}",id)
                .retrieve()
                .bodyToMono(UserInfoDto.class);
    }


    public Mono<UserInfoDto> updateUserById(UserUpdateDto user, UUID id) {
        return this.webClient.put().uri("/usersById/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(user), UserUpdateDto.class)
                .retrieve()
                .bodyToMono(UserInfoDto.class);
    }

    public Mono<Void> deleteUserById(UUID id){
        return this.webClient.delete().uri("/usersById/{id}")
                .retrieve()
                .bodyToMono(Void.class);
    }
}