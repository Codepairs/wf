package com.example.myapp.controller;

import com.example.myapp.model.*;
import com.example.myapp.service.CategoryService;
import com.example.myapp.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/categories")
    public ResponseEntity<?> create(@RequestBody Category category) {
        categoryService.create(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> read() {
        final List<Category> categories = categoryService.readAll();

        return categories != null &&  !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/categories/{id}/incomes")
    public ResponseEntity<List<Income>> read_incomes(@PathVariable(name = "id") Long id) {
        List<Income> incomes = categoryService.getIncomes(id);
        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/categories/{id}/expenses")
    public ResponseEntity<List<Expense>> read_expenses(@PathVariable(name = "id") Long id) {
        List<Expense> expenses = categoryService.getExpenses(id);
        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<Category> read(@PathVariable(name = "id") Long id) {
        final Category category = categoryService.read(id);

        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/categories/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Category category) {
        final boolean updated = categoryService.update(category, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = categoryService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
