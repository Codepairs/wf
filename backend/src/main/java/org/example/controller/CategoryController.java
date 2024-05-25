package org.example.controller;


import jakarta.validation.Valid;
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

import java.util.List;
import java.util.UUID;

@RestController
public class CategoryController {
    /*
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryInfoDto>> readAll() {
        List<CategoryInfoDto> categories = categoryService.readAll();
        return categories != null &&  !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/categories/categoriesById/{id}")
    public ResponseEntity<CategoryInfoDto> read(@PathVariable(name = "id") UUID id) {
        final CategoryInfoDto category = categoryService.read(id);
        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


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


    @PostMapping(value = "/categories")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryCreateDto category) {
        final CategoryInfoDto created = categoryService.create(category);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/category/expensesById")
    public ResponseEntity<List<ExpenseInfoDto>> readExpenses(@RequestParam(name = "id") UUID id) {
        List<ExpenseInfoDto> expenses = categoryService.getExpenses(id);
        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/category/incomesById")
    public ResponseEntity<List<IncomeInfoDto>> readIncomes(@RequestParam(name = "id") UUID id) {
        List<IncomeInfoDto> incomes = categoryService.getIncomes(id);
        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    */
}
