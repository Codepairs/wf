package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.category.CategoryCreateDto;
import org.example.dto.category.CategoryInfoDto;
import org.example.dto.category.CategoryUpdateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.user.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final WebClient webClient;
    private final UserService userService;

    public Flux<CategoryInfoDto> getCategoriesPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        Map<String, String> category = new HashMap<>();
        category.put("userId", userId);
        category.put("token", token);
        return this.webClient.post()
                .uri("/categories/pagination")
                .body(BodyInserters.fromValue(category))
                .header("Content-Type", "application/json")
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(CategoryInfoDto.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(e-> Flux.empty());
    }

    public Mono<CategoryInfoDto> getCategoryById(UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        return webClient.get().
                uri("/categories/categoryById/" + id, CategoryInfoDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(CategoryInfoDto.class);
    }

    public Mono<CategoryInfoDto> create(CategoryCreateDto category, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        return webClient.post().
                uri("/categories", CategoryCreateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(CategoryInfoDto.class);
    }

/*
    public CategoryInfoDto delete(UUID id) {
        return webClient.getForObject("http://localhost:8080/categories/categoryById/" + id.toString(), CategoryInfoDto.class);
    }


    public CategoryInfoDto update(CategoryUpdateDto category, UUID id) {
        return webClient.postForObject("http://localhost:8080/categories/categoryById/" + id.toString(), category, CategoryInfoDto.class);
    }
*/

    public Flux<ExpenseInfoDto> getExpenses(UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        return webClient.get().
                uri("/category/expensesById/?id=" + id.toString(), CategoryCreateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }



    public Flux<IncomeInfoDto> getIncomes(UUID id,  ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        return webClient.get().
                uri("/category/incomesById/?id=" + id.toString(), CategoryCreateDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }


    public Map<String, Double> getBestCategories(ServerWebExchange exchange) throws ExecutionException, InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("best token" + token);
        log.info("best id " + userId);


        List<ExpenseInfoDto> allExpenses = userService.getExpensesById(exchange).get();

        List<IncomeInfoDto> allIncomes = userService.getIncomesById(exchange).get();
        log.info("all incomes " + allIncomes);
        log.info("all expenses " + allExpenses);

        /*
        Mono<Map<String, Double>> totalExpensesByCategory = allExpenses
                .collectList()
                .map(expenses -> expenses.stream()
                        .collect(Collectors.groupingBy(e -> e.getCategory().getName(),
                                Collectors.summingDouble(ExpenseInfoDto::getValue))));

// Calculate total incomes per category
        Mono<Map<String, Double>> totalIncomesByCategory = allIncomes
                .collectList()
                .map(incomes -> incomes.stream()
                        .collect(Collectors.groupingBy(i -> i.getCategory().getName(),
                                Collectors.summingDouble(IncomeInfoDto::getValue))));

        Map<String, Double> combinedCategoryAmounts = new HashMap<>();

        log.info("total incomes: " + totalIncomesByCategory.flatMap(res -> totalIncomesByCategory).block());
        log.info("total expenses: " + totalExpensesByCategory.flatMap(res -> totalExpensesByCategory).block());
        */

        return null;
    }

}
