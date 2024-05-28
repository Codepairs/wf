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
import java.util.concurrent.TimeUnit;
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
                uri("/categories/categoryById?id=" + id, CategoryInfoDto.class)
                .header("Authorization",  token)
                .retrieve()
                .bodyToMono(CategoryInfoDto.class);
    }

    public Mono<CategoryInfoDto> getCategoryByName(String name, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("category token " + token);
        return webClient.get().
                uri("/categories/categoryByName?name=" + name, CategoryInfoDto.class)
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


    public Mono<List<Map.Entry<String, Double>>> getBestCategories(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("best token" + token);
        log.info("best id " + userId);
        Flux<ExpenseInfoDto> allExpenses = userService.getExpensesById(exchange).doOnNext(data -> System.out.println("Получены данные: " + data));
        Flux<IncomeInfoDto> allIncomes = userService.getIncomesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));
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

    public Mono<List<Map.Entry<String, Double>>> getBestCategoriesIncomes(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("best token" + token);
        log.info("best id " + userId);
        Flux<IncomeInfoDto> allIncomes = userService.getIncomesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));

        Mono<List<Map.Entry<String, Double>>> groupedIncomes = allIncomes
                .groupBy(IncomeInfoDto::getCategory)
                .flatMap(group -> group.reduce(0.0, (acc, income) -> acc + income.getValue())
                        .map(sum -> Map.entry(group.key().getName(), sum))) // Use Map.entry instead of Map.of
                .collectList();

        log.info("groupedIncomes " + groupedIncomes.toString());

        // Сортируем категории по сумме в порядке убывания и ограничиваемся тремя
        Mono<List<Map.Entry<String, Double>>> top3Categories = groupedIncomes
                .map(merged -> merged.stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toList()))
                .doOnNext(top3 -> top3.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue())));

        // Subscribe to the Mono to process the top 3 categories
        top3Categories.subscribe();

        return top3Categories;
    }

    public Mono<List<Map.Entry<String, Double>>> getBestCategoriesExpenses(ServerWebExchange exchange) throws InterruptedException {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userId = exchange.getRequest().getHeaders().getFirst("UserId");
        log.info("best token" + token);
        log.info("best id " + userId);
        Flux<ExpenseInfoDto> allExpenses = userService.getExpensesById(exchange).doOnNext(data -> System.out.println("Поток данных завершен" + data));

        Mono<List<Map.Entry<String, Double>>> groupedExpenses = allExpenses
                .groupBy(ExpenseInfoDto::getCategory) // Assuming getCategory exists in ExpenseInfoDto
                .flatMap(group -> group.reduce(0.0, (acc, expense) -> acc + expense.getValue())
                        .map(sum -> Map.entry(group.key().getName(), sum)))
                .collectList();

        log.info("groupedExpenses " + groupedExpenses.toString());

        // Сортируем категории по сумме в порядке убывания и ограничиваемся тремя
        Mono<List<Map.Entry<String, Double>>> top3Categories = groupedExpenses
                .map(merged -> merged.stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toList()))
                .doOnNext(top3 -> top3.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue())));

        // Subscribe to the Mono to process the top 3 categories
        top3Categories.subscribe();

        return top3Categories;
    }


}


