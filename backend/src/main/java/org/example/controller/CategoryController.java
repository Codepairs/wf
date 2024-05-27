package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.category.CategoryCreateDto;
import org.example.dto.category.CategoryInfoDto;
import org.example.dto.category.CategoryUpdateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;



    @PostMapping(value = "/categories/pagination")
    public ResponseEntity<Flux<CategoryInfoDto>> getCategoriesPagination(ServerWebExchange exchange) {
        Flux<CategoryInfoDto> categories = categoryService.getCategoriesPagination(exchange);
        return categories != null
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/categories/categoriesById/{id}")
    public ResponseEntity<Mono<CategoryInfoDto>> read(@PathVariable(name = "id") UUID id, ServerWebExchange exchange) {
        final Mono<CategoryInfoDto> category = categoryService.getCategoryById(id, exchange);
        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

/*
    @PutMapping(value = "/categories/categoriesById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody CategoryUpdateDto category) {
        final CategoryInfoDto updated = categoryService.update(category, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/categories/categoriesById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final CategoryInfoDto deleted = categoryService.delete(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
*/

    @PostMapping(value = "/categories")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryCreateDto category, ServerWebExchange exchange) {
        final Mono<CategoryInfoDto> created = categoryService.create(category, exchange);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/category/expensesById?id=")
    public ResponseEntity<Flux<ExpenseInfoDto>> readExpenses(@RequestParam(name = "id") UUID id, ServerWebExchange exchange) {
        Flux<ExpenseInfoDto> expenses = categoryService.getExpenses(id, exchange);
        return expenses != null
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/category/incomesById?id=")
    public ResponseEntity<Flux<IncomeInfoDto>> readIncomes(@RequestParam(name = "id") UUID id, ServerWebExchange exchange) {
        Flux<IncomeInfoDto> incomes = categoryService.getIncomes(id, exchange);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/categories/getBestCategories")
    public ResponseEntity<Map<String, Double>> getBestCategories(ServerWebExchange exchange) throws ExecutionException, InterruptedException {
        Map<String, Double> bestCategories = categoryService.getBestCategories(exchange);
        return bestCategories != null
                ? new ResponseEntity<>(bestCategories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
