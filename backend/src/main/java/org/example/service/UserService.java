package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.user.UserInfoDto;
import org.example.dto.user.UserUpdateDto;
import org.example.service.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;

    public Flux<UserInfoDto> getUsersPagination(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("token " + token);
        Map<String, String> category = new HashMap<>();

        return this.webClient.post()
                        .uri("/users/pagination")
                        .body(BodyInserters.fromValue(category))
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

    public Flux<IncomeInfoDto> getIncomesById(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");

        return this.webClient.get().uri("/users/incomesById?id={id}", userId)
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }

    public Flux<ExpenseInfoDto> getExpensesById(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");

        return this.webClient.get().uri("/users/expensesById?id={id}", userId)
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }

    public Flux<ExpenseInfoDto> getExpensesInLastMonth(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        Filter firstFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation("<=")
                .build();
        Filter secondFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation(">=")
                .build();
        List<Filter> totalFilter = List.of(firstFilter, secondFilter);
        log.info(totalFilter.toString());
        return this.webClient.post().uri("/users/expensesByIdAndFilter?id={id}", userId)
                .body(BodyInserters.fromValue(totalFilter))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }

    public Flux<ExpenseInfoDto> getExpensesInLastThreeDays(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        Filter firstFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation("<=")
                .build();
        Filter secondFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation(">=")
                .build();
        List<Filter> totalFilter = List.of(firstFilter, secondFilter);
        log.info(totalFilter.toString());
        return this.webClient.post().uri("/users/expensesByIdAndFilter?id={id}", userId)
                .body(BodyInserters.fromValue(totalFilter))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(ExpenseInfoDto.class);
    }


    public Flux<IncomeInfoDto> getIncomesInLastMonth(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        Filter firstFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation("<=")
                .build();
        Filter secondFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation(">=")
                .build();
        List<Filter> totalFilter = List.of(firstFilter, secondFilter);
        log.info(totalFilter.toString());
        return this.webClient.post().uri("/users/incomesByIdAndFilter?id={id}", userId)
                .body(BodyInserters.fromValue(totalFilter))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }

    public Flux<IncomeInfoDto> getIncomesInLastThreeDays(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        Filter firstFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation("<=")
                .build();
        Filter secondFilter = Filter.builder()
                .field("getDate")
                .value(LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .Operation(">=")
                .build();
        List<Filter> totalFilter = List.of(firstFilter, secondFilter);
        log.info(totalFilter.toString());
        return this.webClient.post().uri("/users/incomesByIdAndFilter?id={id}", userId)
                .body(BodyInserters.fromValue(totalFilter))
                .header("Authorization",  token)
                .retrieve()
                .bodyToFlux(IncomeInfoDto.class);
    }


    public Mono<UserInfoDto> getUserById(UUID id, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        return this.webClient.get().uri("/users/usersById?={id}", id)
                .header("Authorization",  token)
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

    public Mono<List<Map.Entry<String, Double>>> getBestCategories(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("best token" + token);
        log.info("best id " + userId);
        Flux<ExpenseInfoDto> allExpenses = this.getExpensesById(exchange).doOnNext(data -> System.out.println("Получены данные: " + data));
        Flux<IncomeInfoDto> allIncomes = this.getIncomesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));

        Mono<List<Map<String, Double>>> groupedExpenses = allExpenses
                .groupBy(ExpenseInfoDto::getCategory)
                .flatMap(group -> group.reduce(0.0, (acc, expense) -> acc + expense.getValue())
                        .map(sum -> Map.of(group.key().getName(), sum)))
                .collectList();

        Mono<List<Map<String, Double>>> groupedIncomes = allIncomes
                .groupBy(IncomeInfoDto::getCategory)
                .flatMap(group -> group.reduce(0.0, (acc, income) -> acc + income.getValue())
                        .map(sum -> Map.of(group.key().getName(), sum)))
                .collectList();


        // Ожидать завершения группировки расходов и доходов
        Mono<Map<String, Double>> allCategories = Mono.when(groupedExpenses, groupedIncomes)
                .then(Mono.zip(groupedExpenses, groupedIncomes, (expenses, incomes) -> {
                    Map<String, Double> merged = new HashMap<>();
                    expenses.stream().flatMap(map -> map.entrySet().stream()).forEach(entry -> merged.merge(entry.getKey(), entry.getValue(), Double::sum));
                    incomes.stream().flatMap(map -> map.entrySet().stream()).forEach(entry -> merged.merge(entry.getKey(), entry.getValue(), Double::sum));

                    return merged;
                }));

        log.info("allCategories " + allCategories.toString());

        Mono<List<Map.Entry<String, Double>>> top3Categories = allCategories
                .map(merged -> merged.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toList()))
                .doOnNext(top3 -> top3.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue())));

// Subscribe to the Mono to process the top 3 categories
        top3Categories.subscribe();

        // Отсортировать категории по сумме в порядке убывания
        return top3Categories;
    }

    public Mono<List<Map<String, Double>>> getTransactionsByMonths(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("months token" + token);
        log.info("months id " + userId);

        Flux<IncomeInfoDto> allIncomes = this.getIncomesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));

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


        Flux<ExpenseInfoDto> allExpenses = this.getExpensesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));


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


        return Mono.zip(groupedIncomesByMonths, groupedExpensesByMonths)
                .map(tuple -> {
                    List<Map<String, Double>> incomes = tuple.getT1();
                    List<Map<String, Double>> expenses = tuple.getT2();

                    // Create a combined list
                    List<Map<String, Double>> combined = new ArrayList<>();

                    // Iterate through incomes
                    for (Map<String, Double> incomeMap : incomes) {
                        // Get the month number from the income map
                        String monthNumber = incomeMap.keySet().iterator().next();

                        // Find the corresponding expense map
                        Map<String, Double> expenseMap = expenses.stream()
                                .filter(map -> map.keySet().iterator().next().equals(monthNumber))
                                .findFirst()
                                .orElse(Map.of(monthNumber, 0.0));

                        // Create a combined map with incomes and expenses
                        Map<String, Double> combinedMap = new HashMap<>();
                        combinedMap.putAll(incomeMap);
                        combinedMap.putAll(expenseMap);
                        log.info("combinedMap " + combinedMap);

                        combined.add(combinedMap);
                    }

                    return combined;
                });
    }
}